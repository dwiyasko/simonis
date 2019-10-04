package com.syanko.simonis.ui.main

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
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MenuFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel
    private var listener: MenuItemClickListener? = null

    private lateinit var adapter: MenuViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        context?.applicationContext
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        (view as RecyclerView).layoutManager = LinearLayoutManager(context)
        adapter = MenuViewAdapter(listener)
        view.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isTopLevelMenu()) {
            viewModel.menu.observe(this, Observer {
                adapter.submitList(it)
            })
            viewModel.getMenuTree()
        } else {
            val items: ArrayList<MainViewObject.MenuViewObject> =
                arguments?.getParcelableArrayList<MainViewObject.MenuViewObject>(ITEMS_EXTRA) as ArrayList<MainViewObject.MenuViewObject>
            adapter.submitList(items)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MenuItemClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun isTopLevelMenu(): Boolean {
        return arguments?.getBoolean(IS_TOP_LEVEL_EXTRA, false) ?: false
    }

    companion object {
        private const val ITEMS_EXTRA = ".items_extra"
        private const val IS_TOP_LEVEL_EXTRA = ".is_top_level_extra"
        private const val PARENT_NAME_EXTRA = ".parent_name_extra"

        fun createFragment(): MenuFragment {
            return MenuFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_TOP_LEVEL_EXTRA, true)
                }
            }
        }

        fun createFragmentWithItem(
            items: List<MainViewObject.MenuViewObject>,
            parent: String
        ): MenuFragment {
            return MenuFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ITEMS_EXTRA, ArrayList(items))
                    putString(PARENT_NAME_EXTRA, parent)
                }
            }
        }
    }
}
