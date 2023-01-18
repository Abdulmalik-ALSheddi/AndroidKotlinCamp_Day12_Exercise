package com.mlk.day12testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val db = DataBase(this)
    private lateinit var  listView :ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val genreEditText = findViewById<EditText>(R.id.editTextGenre)
        val addButton = findViewById<Button>(R.id.addButton)

        listView = findViewById(R.id.ListViewMain)
        listAdapter()

        listView.setOnItemClickListener { adapterView, _, position, _ ->
            val intent = Intent(applicationContext , GameActivity::class.java)
            intent.putExtra("item",adapterView.getItemAtPosition(position).toString())
            startActivity(intent)
        }

        addButton.setOnClickListener {
            if (titleEditText.text.toString().isBlank() || genreEditText.text.toString().isBlank())
                Toast.makeText(this,"You have to enter the Title & Genre",Toast.LENGTH_SHORT).show()
            else{
                db.addGame(titleEditText.text.toString(),genreEditText.text.toString())
                listAdapter()
                Toast.makeText(this,"Game Added!",Toast.LENGTH_SHORT).show()
                titleEditText.setText("")
                genreEditText.setText("")
            }
        }


    }

    fun listAdapter(){
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,db.getAll())
        listView.adapter = adapter
    }
}