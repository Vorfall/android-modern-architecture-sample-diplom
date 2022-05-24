package com.example.academyhomework.domain.features.simpleWordList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.translateapp.domain.features.repository.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordList : BottomSheetDialogFragment() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.word_recycler)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val adapter = WordsAdapter()
        adapter.setAutoScrollBack(object : AutoScrollable {
            override fun scrollBack(search: EditText) {
                recyclerView.scrollBy(0, -500)
            }
        })

        adapter.setOnClickWordListListener(object : WordDescribable {
            override fun onClick(word: Dword) {
                Toast.makeText(context, "${word.word} is meaning...bla bla bla", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        recyclerView.apply {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.also {
            val bottomSheet = dialog!!.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from<View>(bottomSheet)
            behavior.peekHeight =
                (resources.getDimension(R.dimen.words_header_height)).toInt() // replace to whatever you want
            view?.requestLayout()
        }

        updateList()
    }

    private fun updateList() {
        scope.launch {
            (recyclerView.adapter as WordsAdapter).bindWords(DataSource().loadWords(context?.applicationContext!!))
            (recyclerView.adapter as WordsAdapter).submitList(DataSource().loadWords(context?.applicationContext!!))
        }
    }
}

class WordCallback : DiffUtil.ItemCallback<Dword>() {
    override fun areItemsTheSame(oldItem: Dword, newItem: Dword): Boolean =
        oldItem.word == newItem.word

    override fun areContentsTheSame(oldItem: Dword, newItem: Dword): Boolean =
        oldItem == newItem
}
