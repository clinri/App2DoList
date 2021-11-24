package ru.clinri.app2dolist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ListNameItemBinding
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

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)
        fun setItemData(toDoListItem: ToDoListItem, listener: Listener) = with(binding) {

        }

        fun setLibraryData(toDoListItem: ToDoListItem, listener: Listener) = with(binding) {

        }

        companion object {
            fun createToDoItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
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