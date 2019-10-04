package com.syanko.simonis.ui.form.section

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MainViewObject
import com.syanko.simonis.ui.main.MenuItemClickListener
import kotlinx.android.synthetic.main.fragment_form.view.list_form
import kotlinx.android.synthetic.main.fragment_form_section.*
import kotlinx.android.synthetic.main.fragment_form_section.view.*

class FormSectionFragment : Fragment() {

    private lateinit var adapter: FormSectionAdapter
    private lateinit var listener: MenuItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form_section, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data =
            arguments?.getParcelableArrayList<MainViewObject.SectionViewObject>(SECTION_ITEM_EXTRA)
        val parentSection = arguments?.getString(SECTION_PARENT_EXTRA) ?: "Unknown"
        val formTitle = arguments?.getString(SECTION_FORM_TITLE_EXTRA) ?: "-"

        showChildTreeIfAny(parentSection)

        view.txtFormTitle.text = formTitle

        adapter = FormSectionAdapter(listener)
        view.list_form.layoutManager = LinearLayoutManager(context)
        view.list_form.adapter = adapter

        adapter.submitList(data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MenuItemClickListener
    }

    private fun showChildTreeIfAny(section: String) {
        if (section.isNotEmpty()) {
            txtChildLabel.text = section
        } else {
            txtChildLabel.visibility = GONE
            imgChildTree.visibility = GONE
        }
    }

    companion object {
        const val SECTION_ITEM_EXTRA = ".section_item_extra"
        const val SECTION_FORM_TITLE_EXTRA = ".section_form_title_extra"
        const val SECTION_PARENT_EXTRA = ".section_parent_extra"

        fun createFragment(
            formTitle: String,
            parent: String,
            items: List<MainViewObject.SectionViewObject>
        ): FormSectionFragment {
            return FormSectionFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(SECTION_FORM_TITLE_EXTRA, formTitle)
                        putString(SECTION_PARENT_EXTRA, parent)
                        putParcelableArrayList(SECTION_ITEM_EXTRA, ArrayList(items))
                    }
                }
        }
    }
}