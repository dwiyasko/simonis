package com.syanko.simonis.ui.form.section

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MainViewObject
import com.syanko.simonis.ui.main.MainViewObject.SectionViewObject.Companion.DIFF_CALLBACK
import com.syanko.simonis.ui.main.MenuItemClickListener
import kotlinx.android.synthetic.main.item_section.view.*

class FormSectionAdapter(private val listener: MenuItemClickListener) :
    ListAdapter<MainViewObject.SectionViewObject, FormSectionAdapter.FormSectionViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return FormSectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormSectionViewHolder, position: Int) {
        holder.bindWith(getItem(position))
    }

    inner class FormSectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindWith(item: MainViewObject.SectionViewObject) {
            itemView.txtSectionTitle.text = item.title

            itemView.setOnClickListener { listener.onMenuItemClicked(item) }
        }
    }

}
