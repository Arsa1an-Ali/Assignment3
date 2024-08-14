package com.example.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter(
    private val quotes: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.bind(quote, onItemClick)
    }

    override fun getItemCount() = quotes.size

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quoteTextView: TextView = itemView.findViewById(R.id.quoteTextView)

        fun bind(quote: String, onItemClick: (String) -> Unit) {
            quoteTextView.text = quote
            itemView.setOnClickListener { onItemClick(quote) }
        }
    }
}
