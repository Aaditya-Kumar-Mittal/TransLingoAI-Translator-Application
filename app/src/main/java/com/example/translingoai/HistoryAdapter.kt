package com.example.translingoai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter :
    ListAdapter<TranslationHistory, HistoryAdapter.TranslationViewHolder>(DiffCallback()) {

    class TranslationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val originalText: TextView = itemView.findViewById(R.id.originalText)
        val translatedText: TextView = itemView.findViewById(R.id.translatedText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.translation_item, parent, false)
        return TranslationViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationViewHolder, position: Int) {
        val item = getItem(position)
        holder.originalText.text = item.originalText
        holder.translatedText.text = item.translatedText
    }

    class DiffCallback : DiffUtil.ItemCallback<TranslationHistory>() {
        override fun areItemsTheSame(oldItem: TranslationHistory, newItem: TranslationHistory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TranslationHistory, newItem: TranslationHistory) =
            oldItem == newItem
    }
}
