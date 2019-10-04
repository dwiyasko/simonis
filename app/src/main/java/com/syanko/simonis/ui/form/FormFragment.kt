package com.syanko.simonis.ui.form

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.FormItemSelectedListerner
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_form.view.*
import javax.inject.Inject

class FormFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    lateinit var viewModel: FormViewModel

    lateinit var adapter: FormAdapter
    lateinit var listener: FormItemSelectedListerner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelProvider)[FormViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inspectionId = arguments?.getInt(INSPECTION_ID_EXTRA) ?: -1

        if (inspectionId > 0) {
            viewModel.loadFormByScheduleId(inspectionId)
        }

        adapter = FormAdapter(listener)
        view.list_form.layoutManager = LinearLayoutManager(context)
        view.list_form.adapter = adapter

        viewModel.forms.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FormItemSelectedListerner
    }

    companion object {

        const val INSPECTION_ID_EXTRA = ".inspection_id_extra"

        @JvmStatic
        fun createFragment(inspectionId: Int): FormFragment {
            return FormFragment().apply {
                arguments = Bundle().apply {
                    putInt(INSPECTION_ID_EXTRA, inspectionId)
                }
            }
        }
    }
}