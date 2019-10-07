package com.syanko.simonis.ui.main.inspection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MenuItemClickListener
import com.syanko.simonis.ui.main.PageLoaderListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InspectionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    lateinit var viewModel: InspectionViewModel

    private var listener: MenuItemClickListener? = null
    private var loaderListener: PageLoaderListener? = null
    private lateinit var adapter: InspectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProvider)[InspectionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inspection, container, false)

        if (view is RecyclerView) {
            adapter = InspectionAdapter(listener)
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = this@InspectionFragment.adapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.inspection.observe(this, Observer {
            adapter.submitList(it)
            loaderListener?.onPageLoaded()
        })

        val equipmentId = arguments?.getInt(EQUIPMENT_ID_EXTRA, -1) ?: -1
        loaderListener?.onPageLoading()
        viewModel.loadInspectionByEquipment(equipmentId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MenuItemClickListener
        loaderListener = context as PageLoaderListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        private const val EQUIPMENT_ID_EXTRA = ".equipment_id_extra"
        @JvmStatic
        fun createFragment(equipmentId: Int) =
            InspectionFragment().apply {
                arguments = Bundle().apply {
                    putInt(EQUIPMENT_ID_EXTRA, equipmentId)
                }
            }
    }
}
