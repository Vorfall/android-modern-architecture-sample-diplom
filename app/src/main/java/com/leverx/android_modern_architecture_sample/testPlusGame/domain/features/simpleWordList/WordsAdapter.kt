package com.example.academyhomework.domain.features.simpleWordList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.leverx.android_modern_architecture_sample.R
import java.lang.Exception

class WordsAdapter() :
    androidx.recyclerview.widget.ListAdapter<Dword, WordsAdapter.WordsItemViewHolder>(
        WordCallback()
    ) {

    private var textChangeListener: EditTextChangeListener? = null
    private var disListener: WordDescribable? = null
    private var scrollback: AutoScrollable? = null
    fun setOnClickWordListListener(l: WordDescribable) {
        disListener = l
    }

    fun setOnTextChangeListener(listener: EditTextChangeListener) {
        textChangeListener = listener
    }

    fun setAutoScrollBack(scroll: AutoScrollable) {
        scrollback = scroll
    }

    private var words = mutableListOf<Dword>()
    private var original = listOf<Dword>()

    abstract class WordsItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class EmptyViewHolder(view: View) : WordsItemViewHolder(view)
    class HeaderViewHolder(view: View) : WordsItemViewHolder(view) {
        val search = view.findViewById<EditText>(R.id.et_wordSearch)
        val searchLayout =
            view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.et_wordSearch_layout)
        val tvHeader = view.findViewById<TextView>(R.id.tv_header)
    }

    class WordViewHolder(view: View) : WordsItemViewHolder(view) {
        val textViewWord = view.findViewById<TextView>(R.id.textViewWord)
        val textViewTranslate: TextView = view.findViewById(R.id.textViewTranslate)

        fun bindWordFields(wordItem: Dword) {
            textViewWord.text = wordItem.word
            textViewTranslate.text = wordItem.translate
        }
    }

    fun bindWords(list: List<Dword>) {
        words = list.toMutableList()
        original = list
        notifyDataSetChanged()
    }

    enum class ViewTypes(type: Int) {
        HEADER(0),
        WORD(1),
        EMPTY(2)
    }

    override fun getItemViewType(position: Int): Int {
        return try {

            when {

                words[position] == null -> ViewTypes.EMPTY.ordinal
                words[position].word == "nope" -> ViewTypes.HEADER.ordinal
                else -> ViewTypes.WORD.ordinal
            }
        } catch (e: Exception) {
            ViewTypes.EMPTY.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsItemViewHolder {
        return when (viewType) {

            ViewTypes.WORD.ordinal -> WordViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.words_item, parent, false)
            )

            ViewTypes.HEADER.ordinal -> HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.words_header, parent, false)
            )

            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.empty_word_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: WordsItemViewHolder, position: Int) {

        when (holder) {
            is WordViewHolder -> {
                holder.bindWordFields(words[position])
                holder.itemView.setOnClickListener {
                    disListener?.onClick(words[position])
                }
            }
            is HeaderViewHolder -> holder.itemView.setOnClickListener {
                when (holder.searchLayout.visibility) {

                    View.GONE -> {

                        holder.searchLayout.visibility = View.VISIBLE
                        holder.search.showSoftInputOnFocus = true
                        scrollback?.scrollBack(holder.search)
                    }
                    View.VISIBLE -> {

                        holder.searchLayout.visibility = View.GONE
                    }
                }
                val defaultSearch: (CharSequence?, Int, Int, Int) -> Unit = { field, _, _, _ ->

                    words =
                        original.filter { it.word.startsWith(field ?: "", 0, true) }.toMutableList()
                    words.add(0, Dword())
                    submitList(words)
                }
                holder.search.addTextChangedListener(
                    onTextChanged = textChangeListener?.action ?: defaultSearch
                )
            }
        }
    }
}
