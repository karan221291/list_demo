package com.example.myapplication

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(private var list : ArrayList<ItemModel>) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setItemData(list[position], this::deleteItem, this::showDialog)
    }

    override fun getItemCount(): Int {
        //return items size
        return list.size
    }

    fun addItem (item: ItemModel) {
        list.add(item)
        notifyItemInserted(list.size)
    }

    fun deleteItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setItemData(itemData : ItemModel, deleteItem: (pos: Int) -> Unit, showDialog: (context:Context, deleteItem:()->Unit) -> Unit  )  {
            val tvTitle : TextView = itemView.findViewById(R.id.tv_title)
            tvTitle.text = itemData.itemTitle

            val img_delete : ImageView = itemView.findViewById(R.id.btn_delete)
            img_delete.setOnClickListener {
                showDialog(itemView.context) {
                    deleteItem(adapterPosition)
                }
            }
        }
    }

    fun showDialog(context : Context, deleteItem: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete this item?")

        builder.setPositiveButton("Delete") { dialog, which ->
            deleteItem()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
         }

        builder.show()
    }
}

