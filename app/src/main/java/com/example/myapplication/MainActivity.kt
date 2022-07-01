package com.example.myapplication

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    lateinit var rvAdapter: RVAdapter
    lateinit var rvMain: RecyclerView
    private val items: ArrayList<ItemModel> = ArrayList()
    lateinit var fabAdd: FloatingActionButton
    var inputItem: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain = findViewById(R.id.rv_main)
        fabAdd = findViewById(R.id.fab_add_item)
        setRecyclerView()

        fabAdd.setOnClickListener {
            showDialog()
        }
    }

    fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Item")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, which ->
            val item = ItemModel(itemTitle = input.text.toString())
            rvAdapter.addItem(item)
            Toast.makeText(this@MainActivity, getString(R.string.item_added), Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
            Toast.makeText(this@MainActivity, getString(R.string.item_not_added), Toast.LENGTH_LONG).show()
        }

        builder.show()
    }

    fun getItems(): ArrayList<ItemModel> {
        items.add(ItemModel(itemTitle = "item 1"))
        items.add(ItemModel(itemTitle = "item 2"))
        return items
    }

    fun setRecyclerView() {
        rvAdapter = RVAdapter(getItems())
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}

data class ItemModel(var itemTitle: String)