package com.syanko.simonis.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuViewAdapter(
    private val mListener: MenuItemClickListener?
) : ListAdapter<MainViewObject.MenuViewObject, MenuViewAdapter.ViewHolder>(MainViewObject.MenuViewObject.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindWith(getItem(position))
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bindWith(menu: MainViewObject.MenuViewObject) {
            mView.menu_label.text = menu.label
            mView.menu_label.setOnClickListener {
                mListener?.onMenuItemClicked(menu)
            }
        }
    }
}
