package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var quoteList: MutableList<String>
    private lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize quotes with numbering
        quoteList = savedInstanceState?.getStringArrayList("QUOTE_LIST")?.toMutableList()
            ?: mutableListOf(
                "1. The best way to predict the future is to create it.",
                "2. Don't watch the clock; do what it does. Keep going.",
                "3. Success is not the key to happiness. Happiness is the key to success. If you love what you are doing, you will be successful.",
                "4. The only limit to our realization of tomorrow is our doubts of today."
            )

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        quoteAdapter = QuoteAdapter(quoteList) { quote ->
            val intent = Intent(this, QuoteDetailActivity::class.java)
            intent.putExtra("QUOTE_TEXT", quote)
            startActivity(intent)
        }
        recyclerView.adapter = quoteAdapter

        // Set up EditText and Button for adding new quotes
        val quoteInput = findViewById<EditText>(R.id.quoteInput)
        val addQuoteButton = findViewById<Button>(R.id.addQuoteButton)

        addQuoteButton.setOnClickListener {
            val newQuote = quoteInput.text.toString()
            if (newQuote.isNotEmpty()) {
                val newNumber = quoteList.size + 1
                val numberedQuote = "$newNumber. $newQuote"
                quoteList.add(numberedQuote)
                quoteAdapter.notifyItemInserted(quoteList.size - 1)
                quoteInput.text.clear()
            } else {
                Toast.makeText(this, "Please enter a quote", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("QUOTE_LIST", ArrayList(quoteList))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                showDialog("Settings", "Settings options can be configured here.")
                return true
            }
            R.id.action_share -> {
                showDialog("Share", "Share your favorite quotes with your friends!")
                return true
            }
            R.id.action_about -> {
                showDialog("About", "This app helps you manage and share your favorite quotes.")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
