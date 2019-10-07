package com.syanko.simonis.ui.main.equipment

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

class EquipmentFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    lateinit var viewModel: EquipmentViewModel

    lateinit var adapter: EquipmentAdapter

    private var listener: MenuItemClickListener? = null
    private var loaderListener: PageLoaderListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProvider)[EquipmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_equipment, container, false)

        // Set the adapterForm
        if (view is RecyclerView) {
            adapter = EquipmentAdapter(listener)

            view.layoutManager = LinearLayoutManager(context)
            view.adapter = adapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.equipments.observe(this, Observer {
            adapter.submitList(it)
            loaderListener?.onPageLoaded()
        })

        val groupId = arguments?.getInt(GROUP_ID_EXTRA, -1) ?: -1
        viewModel.loadEquipmentByGroup(groupId)
        loaderListener?.onPageLoading()
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
        private const val GROUP_ID_EXTRA = ".group_id_extra"

        fun createFragment(groupId: Int): EquipmentFragment {
            return EquipmentFragment().apply {
                arguments = Bundle().apply {
                    putInt(GROUP_ID_EXTRA, groupId)
                }
            }
        }
    }
}
