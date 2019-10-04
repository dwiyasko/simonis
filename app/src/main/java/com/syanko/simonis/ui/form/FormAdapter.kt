package com.syanko.simonis.ui.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.FormItemSelectedListerner
import com.syanko.simonis.ui.main.MainViewObject
import com.syanko.simonis.ui.main.MainViewObject.FormViewObject.Companion.DIFF_CALLBACK
import kotlinx.android.synthetic.main.item_form.view.*

class FormAdapter(private val listener: FormItemSelectedListerner) :
    ListAdapter<MainViewObject.FormViewObject, FormAdapter.FormViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_form, parent, false)
        return FormViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        holder.bindWith(getItem(position))
    }

    inner class FormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindWith(item: MainViewObject.FormViewObject) {
            itemView.form_title.text = item.title

            itemView.form_title.setOnClickListener { listener.onFormSelected(item) }
        }
    }
}
