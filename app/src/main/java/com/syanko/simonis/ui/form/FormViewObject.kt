package com.syanko.simonis.ui.form

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.syanko.simonis.domain.entity.FormType
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FieldSectionViewObject(
    val id: Int,
    val formFieldId: Int,
    val label: String,
    val type: FormType,
    val options: List<FieldOptionViewObject>
) : Parcelable {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FieldSectionViewObject>() {
            override fun areItemsTheSame(
                oldItem: FieldSectionViewObject,
                newItem: FieldSectionViewObject
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FieldSectionViewObject,
                newItem: FieldSectionViewObject
            ): Boolean {
                return oldItem.label == newItem.label
            }

        }
    }
}

@Parcelize
data class FieldOptionViewObject(
    val id: Int,
    val value: String,
    val formFieldSectionId: Int
) : Parcelable

