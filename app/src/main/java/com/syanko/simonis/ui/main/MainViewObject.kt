package com.syanko.simonis.ui.main

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.syanko.simonis.ui.form.FieldSectionViewObject
import kotlinx.android.parcel.Parcelize

data class Property(
    val id: Int,
    val value: String
)

sealed class MainViewObject {
    data class InspectionViewObject(
        val id: Int,
        val title: String,
        val description: String,
        val period: String,
        val time: String,
        val equipmentName: String
    ) : MainViewObject() {
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InspectionViewObject>() {
                override fun areItemsTheSame(
                    oldItem: InspectionViewObject,
                    newItem: InspectionViewObject
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: InspectionViewObject,
                    newItem: InspectionViewObject
                ): Boolean {
                    return oldItem.title == newItem.title
                }

            }
        }
    }

    @Parcelize
    data class MenuViewObject(
        val id: Int,
        val label: String,
        val children: List<MenuViewObject>
    ) : Parcelable, MainViewObject() {
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MenuViewObject>() {
                override fun areItemsTheSame(
                    oldItem: MenuViewObject,
                    newItem: MenuViewObject
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MenuViewObject,
                    newItem: MenuViewObject
                ): Boolean {
                    return oldItem.label == newItem.label
                }
            }
        }
    }

    data class EquipmentViewObject(
        val id: Int,
        val groupId: Int,
        val name: String,
        val breadCrumbs: String
    ) : MainViewObject() {
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EquipmentViewObject>() {
                override fun areContentsTheSame(
                    oldItem: EquipmentViewObject,
                    newItem: EquipmentViewObject
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areItemsTheSame(
                    oldItem: EquipmentViewObject,
                    newItem: EquipmentViewObject
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
        }
    }

    @Parcelize
    data class FormViewObject(
        val id: Int,
        val inspectionId: Int,
        val title: String,
        val description: String,
        val periodId: Int,
        val periodName: String,
        val substationId: Int,
        val substationName: String,
        val voltageId: Int,
        val voltageName: String,
        val bayId: Int,
        val bayName: String,
        val sections: List<SectionViewObject>
    ) : MainViewObject(), Parcelable {
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FormViewObject>() {
                override fun areItemsTheSame(
                    oldItem: FormViewObject,
                    newItem: FormViewObject
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: FormViewObject,
                    newItem: FormViewObject
                ): Boolean {
                    return oldItem.title == newItem.title
                }

            }
        }
    }

    @Parcelize
    data class SectionViewObject(
        val id: Int,
        val formId: Int,
        val parent: Int,
        val formTitle: String,
        val title: String,
        val fields: List<FormFieldViewObject>,
        val children: List<SectionViewObject>
    ) : MainViewObject(), Parcelable {
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SectionViewObject>() {
                override fun areItemsTheSame(
                    oldItem: SectionViewObject,
                    newItem: SectionViewObject
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: SectionViewObject,
                    newItem: SectionViewObject
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
        }
    }

    @Parcelize
    data class FormFieldViewObject(
        val id: Int,
        val label: String,
        val type: Int,
        val order: Int,
        val description: String,
        val formTitle: String,
        val sectionTitle: String,
        val fieldSections: List<FieldSectionViewObject>
    ) : MainViewObject(), Parcelable {
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FormFieldViewObject>() {
                override fun areItemsTheSame(
                    oldItem: FormFieldViewObject,
                    newItem: FormFieldViewObject
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: FormFieldViewObject,
                    newItem: FormFieldViewObject
                ): Boolean {
                    return oldItem.label == oldItem.label
                }
            }
        }
    }
}