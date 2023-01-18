package com.mlk.day12testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {

    private val db = DataBase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val textView = findViewById<TextView>(R.id.textViewGame)
        val editTextTitle = findViewById<EditText>(R.id.editTextGame)
        val editTextNewTitle = findViewById<EditText>(R.id.editTextNewTitle)
        val showButton = findViewById<Button>(R.id.showButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val updateButton = findViewById<Button>(R.id.updateButton)

        textView.text = intent.getStringExtra("item")

        showButton.setOnClickListener {
            if (editTextTitle.text.toString().isBlank()){
                val games = db.getAll()
                if(games.isEmpty()){
                    textView.text = "Empty!!"
                    editTextTitle.setText("")
                }

                else{
                    Toast.makeText(this,"Getting all games!",Toast.LENGTH_SHORT)
                    textView.text = games.joinToString("\n")
                    editTextTitle.setText("")
                }

            }else{
                val game = db.getGameByTitle(editTextTitle.text.toString())
                if (game.id == -1){
                    editTextTitle.setText("")
                    Toast.makeText(this, "No games with this title!!",Toast.LENGTH_SHORT).show()
                }
                else{
                    textView.text =  """
                   Game ID: ${game.id}
                   Game Title: ${game.title}
                   Game Genre: ${game.genre}
               """.trimIndent()
                    editTextTitle.setText("")
                }
            }
        }

        updateButton.setOnClickListener {
            if (editTextTitle.text.toString().isBlank() || editTextNewTitle.text.toString().isBlank()){
                Toast.makeText(this,"Enter game old and new title!",Toast.LENGTH_SHORT).show()
                editTextTitle.setText("")
                editTextNewTitle.setText("")
            }else{
                if (db.editGameTitle(editTextTitle.text.toString(),editTextNewTitle.text.toString())){
                    textView.text = """
                        ${editTextTitle.text} Updated to: ${editTextNewTitle.text}
                    """.trimIndent()
                    editTextTitle.setText("")
                    editTextNewTitle.setText("")
                }else{
                    textView.text = "${editTextTitle.text} Not Found!!"
                    editTextTitle.setText("")
                    editTextNewTitle.setText("")
                }
            }
        }

        deleteButton.setOnClickListener {
            if (editTextTitle.text.toString().isBlank()){
                Toast.makeText(this,"Enter game title!",Toast.LENGTH_SHORT).show()
                editTextTitle.setText("")
            }else{
                if(db.deleteGameByTitle(editTextTitle.text.toString())) {
                    textView.text = "${editTextTitle.text} Deleted!!"
                    editTextTitle.setText("")
                }else{
                    textView.text = "${editTextTitle.text} Not Found!!"
                    editTextTitle.setText("")
                }
            }
        }
    }
}