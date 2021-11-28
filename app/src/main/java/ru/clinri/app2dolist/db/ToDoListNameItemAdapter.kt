package ru.clinri.app2dolist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ListNameItemBinding
import ru.clinri.app2dolist.databinding.ToDoLibraryListItemBinding
import ru.clinri.app2dolist.databinding.ToDoListItemBinding
import ru.clinri.app2dolist.entities.ToDoListItem
import ru.clinri.app2dolist.entities.ToDoListName

class ToDoListNameItemAdapter(private val listener: Listener) :
    ListAdapter<ToDoListItem, ToDoListNameItemAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0)
            ItemHolder.createToDoItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setItemData(toDoListItem: ToDoListItem, listener: Listener) {
            val binding = ToDoListItemBinding.bind(view)
            binding.apply {
                tvName.text = toDoListItem.name
                tvInfo.text = toDoListItem.itemInfo
                tvInfo.visibility = infoVisibility(toDoListItem)
            }
        }

        fun setLibraryData(toDoListItem: ToDoListItem, listener: Listener) {
            //val binding = ToDoLibraryListItemBinding.bind(view)
        }

        fun infoVisibility(toDoListItem: ToDoListItem): Int{
            return if (toDoListItem.itemInfo.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        companion object {
            fun createToDoItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.to_do_list_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.to_do_library_list_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ToDoListItem>() {
        override fun areItemsTheSame(oldItem: ToDoListItem, newItem: ToDoListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoListItem, newItem: ToDoListItem): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(toDoListName: ToDoListName)
        fun onClickItem(toDoListName: ToDoListName)
    }


}