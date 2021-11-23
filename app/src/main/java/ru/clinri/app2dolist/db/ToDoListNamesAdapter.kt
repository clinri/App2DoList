package ru.clinri.app2dolist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ListNameItemBinding
import ru.clinri.app2dolist.entities.ToDoListName

class ToDoListNamesAdapter(private val listener : Listener) : ListAdapter<ToDoListName, ToDoListNamesAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)
        fun setData(toDoListNameItem: ToDoListName, listener: Listener) = with(binding) {
            tvListName.text = toDoListNameItem.name
            tvTime.text = toDoListNameItem.time
            itemView.setOnClickListener{
            }
            imDelete.setOnClickListener{
                listener.deleteItem(toDoListNameItem.id!!)
            }
            imEdit.setOnClickListener{
                listener.editItem(toDoListNameItem)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<ToDoListName>() {
        override fun areItemsTheSame(oldItem: ToDoListName, newItem: ToDoListName): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoListName, newItem: ToDoListName): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id: Int)
        fun editItem(toDoListName: ToDoListName)
        fun onClickItem(toDoListName: ToDoListName)
    }


}