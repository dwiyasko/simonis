package com.syanko.simonis.ui.form.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.annotation.LayoutRes
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import com.syanko.simonis.domain.entity.FormType
import com.syanko.simonis.ui.form.FieldOptionViewObject
import com.syanko.simonis.ui.form.FieldSectionViewObject
import com.syanko.simonis.ui.main.MainViewObject
import com.syanko.simonis.ui.main.MainViewObject.FormFieldViewObject.Companion.DIFF_CALLBACK
import kotlinx.android.synthetic.main.item_content_form.view.*
import kotlinx.android.synthetic.main.item_field_check.view.*
import kotlinx.android.synthetic.main.item_field_date.view.*
import kotlinx.android.synthetic.main.item_field_number.view.*
import kotlinx.android.synthetic.main.item_field_radio.view.*
import kotlinx.android.synthetic.main.item_field_text.view.*
import kotlinx.android.synthetic.main.item_field_text.view.txtTitleFieldText

class FormContentAdapter(val formSessionId: Int) :
    ListAdapter<MainViewObject.FormFieldViewObject, FormContentAdapter.ViewHolder>(DIFF_CALLBACK) {

    var removedPosition: MutableList<Int> = mutableListOf()
    var values: MutableMap<Int, FormValueView> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content_form, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindWith(
            item = getItem(position),
            atPosition = position
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onViewRecycled(holder: ViewHolder) {
        val position = holder.adapterPosition
        removedPosition.add(position)
        super.onViewRecycled(holder)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bindWith(item: MainViewObject.FormFieldViewObject, atPosition: Int) {
            mView.txtFormSectionTitle.text = item.label
            if (!checkIsItemRemoved(atPosition)) {
                populateForm(item.fieldSections)
            }
        }

        private fun checkIsItemRemoved(atPosition: Int) = removedPosition.contains(atPosition)

        private fun populateForm(fieldSections: List<FieldSectionViewObject>) {
            fieldSections.forEach {
                val view = when (it.type) {
                    FormType.TEXT -> {
                        val view = inflateView(R.layout.item_field_text)
                        view.txtTitleFieldText.text = it.label
                        view.etFieldText.addTextChangedListener { editable ->
                            val value = editable?.toString() ?: ""
                            values[it.id] = FormValueView(formSessionId, it.id, it.type.code, value)
                        }
                        view
                    }
                    FormType.NUMBER -> {
                        val view = inflateView(R.layout.item_field_number)
                        view.txtTitleFieldText.text = it.label
                        view.etFieldNumber.addTextChangedListener { editable ->
                            val value = editable?.toString() ?: ""
                            values[it.id] = FormValueView(formSessionId, it.id, it.type.code, value)
                        }
                        view
                    }
                    FormType.DATE -> {
                        val view = inflateView(R.layout.item_field_date)
                        view.txtTitleFieldText.text = it.label
                        view.etFieldDate.addTextChangedListener { editable ->
                            val value = editable?.toString() ?: ""
                            values[it.id] = FormValueView(formSessionId, it.id, it.type.code, value)
                        }
                        view
                    }
                    FormType.RADIO -> {
                        val view = inflateView(R.layout.item_field_radio)
                        view.txtTitleFieldText.text = it.label
                        addRadioOptionValueTo(it.options, view)
                        view
                    }
                    FormType.CHECKBOX -> {
                        val view = inflateView(R.layout.item_field_check)
                        view.txtTitleFieldText.text = it.label
                        addCheckOptionValueTo(it.options, view)
                        view
                    }
                    FormType.UNKNOWN -> View(mView.context)
                }
                mView.frameFieldContent.setHasTransientState(true)
                mView.frameFieldContent.addView(view)
            }
            mView.frameFieldContent.setHasTransientState(false)
        }

        private fun inflateView(@LayoutRes layoutId: Int): View {
            return LayoutInflater.from(mView.frameFieldContent.context).inflate(
                layoutId,
                null
            )
        }

        private fun addRadioOptionValueTo(options: List<FieldOptionViewObject>, view: View) {
            options.forEach { option ->
                val optionView: RadioButton =
                    LayoutInflater.from(view.rgFieldRadio.context)
                        .inflate(R.layout.item_field_radio_button, null) as RadioButton

                optionView.id = option.id
                optionView.text = option.value
                view.rgFieldRadio.addView(optionView)
                view.rgFieldRadio.setOnCheckedChangeListener { _, checkedId ->
                    values[option.formFieldSectionId] = FormValueView(
                        formSessionId,
                        option.formFieldSectionId,
                        FormType.RADIO.code,
                        checkedId.toString()
                    )
                }
            }
        }

        private fun addCheckOptionValueTo(options: List<FieldOptionViewObject>, view: View) {
            options.forEach { option ->
                val selectedValue = mutableListOf<String>()
                val optionView: CheckBox =
                    LayoutInflater.from(view.frameContentCheckBox.context)
                        .inflate(R.layout.item_field_check_box, null) as CheckBox

                optionView.id = option.id
                optionView.text = option.value
                view.frameContentCheckBox.addView(optionView)

                optionView.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (buttonView.isSelected) {
                        selectedValue.add(buttonView.id.toString())
                    } else {
                        selectedValue.remove(buttonView.id.toString())
                    }

                    updateSelectedValue(option.formFieldSectionId, selectedValue)
                }
            }
        }

        private fun updateSelectedValue(
            formFieldSectionId: Int,
            selectedValue: MutableList<String>
        ) {
            var value = ""
            selectedValue.mapIndexed { index, str ->
                value = if (index == 0) {
                    str
                } else {
                    "${value}, $str"
                }
            }
            values[formFieldSectionId] =
                FormValueView(formSessionId, formFieldSectionId, FormType.CHECKBOX.code, value)
        }
    }
}
