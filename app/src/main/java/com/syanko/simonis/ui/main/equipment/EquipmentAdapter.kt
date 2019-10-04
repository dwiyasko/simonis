package com.syanko.simonis.ui.main.equipment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MainViewObject
import com.syanko.simonis.ui.main.MainViewObject.EquipmentViewObject.Companion.DIFF_CALLBACK
import com.syanko.simonis.ui.main.MenuItemClickListener
import kotlinx.android.synthetic.main.item_equipment.view.*

class EquipmentAdapter(
    private val mListener: MenuItemClickListener?
) : ListAdapter<MainViewObject.EquipmentViewObject, EquipmentAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindWith(getItem(position))
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bindWith(item: MainViewObject.EquipmentViewObject) {
            mView.equipment_label.text = item.name
            mView.equipment_label.setOnClickListener {
                mListener?.onMenuItemClicked(item)
            }
        }
    }
}
