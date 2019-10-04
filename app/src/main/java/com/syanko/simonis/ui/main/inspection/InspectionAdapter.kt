package com.syanko.simonis.ui.main.inspection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MainViewObject
import com.syanko.simonis.ui.main.MainViewObject.InspectionViewObject.Companion.DIFF_CALLBACK
import com.syanko.simonis.ui.main.MenuItemClickListener
import kotlinx.android.synthetic.main.item_inspection.view.*

class InspectionAdapter(
    private val mListener: MenuItemClickListener?
) : ListAdapter<MainViewObject.InspectionViewObject, InspectionAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inspection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindWith(getItem(position))
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        fun bindWith(item: MainViewObject.InspectionViewObject) {
            mView.inspection_label.text = item.title
            mView.inspection_label.setOnClickListener {
                mListener?.onMenuItemClicked(item)
            }
        }
    }
}
