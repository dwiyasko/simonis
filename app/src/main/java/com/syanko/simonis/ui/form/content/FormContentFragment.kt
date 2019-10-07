package com.syanko.simonis.ui.form.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MainActivity.Companion.ACTIVE_USER_ID_EXTRA
import com.syanko.simonis.ui.main.MainViewObject
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_form_content.*
import kotlinx.android.synthetic.main.fragment_form_field.view.*
import javax.inject.Inject

class FormContentFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    lateinit var viewModel: FormContentViewModel

    private lateinit var contentAdapter: FormContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelProvider)[FormContentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val formTitle = arguments?.getString(FORM_TITLE_EXTRA) ?: "-"
        val sectionTitle = arguments?.getString(FORM_SECTION_EXTRA) ?: "-"
        val activeUserId = arguments?.getInt(ACTIVE_USER_ID_EXTRA) ?: -1
        val formSessionId = arguments?.getLong(FORM_SESSION_ID_EXTRA) ?: -1L

        view.txtFormTitle.text = formTitle
        view.txtSectionLabel.text = sectionTitle

        prepareAdapter(activeUserId, view)
        setDataAdapter()

        btnSave.setOnClickListener {
            val formIsNotEmpty = checkIsFormNotEmpty()
//            if (formIsNotEmpty) {
////                viewModel.saveFormContent(contentAdapter.values)
//            }
        }
    }

    private fun checkIsFormNotEmpty(): Boolean {
        return contentAdapter.values.isNotEmpty()
    }

    private fun prepareAdapter(activeUserId: Int, view: View) {
        contentAdapter = FormContentAdapter(activeUserId)
        contentAdapter.setHasStableIds(true)
        view.list_form.layoutManager = LinearLayoutManager(context)
        view.list_form.adapter = contentAdapter
    }

    private fun setDataAdapter() {
        val data =
            arguments?.getParcelableArrayList<MainViewObject.FormFieldViewObject>(FIELD_ITEM_EXTRA)
        data?.let {
            contentAdapter.submitList(it)
        }
    }

    companion object {
        private const val FORM_TITLE_EXTRA = ".form_title_extra"
        private const val FORM_SECTION_EXTRA = ".form_section_extra"
        private const val FIELD_ITEM_EXTRA = ".form_item_extra"
        private const val FORM_SESSION_ID_EXTRA = ".form_session_id_extra"

        @JvmStatic
        fun createFragment(
            activeUserId: Int,
            formSessionId: Long,
            formTitle: String,
            sectionTitle: String,
            items: List<MainViewObject.FormFieldViewObject>
        ) =
            FormContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ACTIVE_USER_ID_EXTRA, activeUserId)
                    putLong(FORM_SESSION_ID_EXTRA, formSessionId)
                    putString(FORM_TITLE_EXTRA, formTitle)
                    putString(FORM_SECTION_EXTRA, sectionTitle)
                    putParcelableArrayList(FIELD_ITEM_EXTRA, ArrayList(items))
                }
            }
    }
}
