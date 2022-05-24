package com.example.academyhomework.domain.features.englishToRussianCase

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.academyhomework.TimerLiveData
import com.example.academyhomework.domain.features.extensions.toEnglishTranslateList
import com.leverx.android_modern_architecture_sample.translateapp.domain.features.repository.DataSource
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.translateapp.domain.features.englishToRussianCase.EnglishTranslate
import kotlinx.coroutines.*

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var recyclerView: RecyclerView
    private lateinit var timerView: TextView
    private lateinit var list: MutableList<EnglishTranslate>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timerView = view.findViewById<TextView>(R.id.tv_timer)
        timerView.text = "0"
        recyclerView = view.findViewById(R.id.rv_quiz)
        recyclerView.setHasFixedSize(true)
        scope.launch { withContext(Dispatchers.IO) { setRecyclerList() } }
    }

    private suspend fun setRecyclerList() {
        val listTemp = DataSource().loadWords(requireContext())
        list = (listTemp.toEnglishTranslateList()).toMutableList()
        val adapter = EnglishTranslateAdapter()

        adapter.setOnClickAnswerHandler(getHandler(adapter))

        adapter.bindList(list.shuffled())
        withContext(Dispatchers.Main) { recyclerView.adapter = adapter }
    }

    private fun getHandler(adapter: EnglishTranslateAdapter): AnswerHandler = object :
        AnswerHandler {
        override val action: (EnglishTranslate.TranslateVariants, Int) -> Unit
            get() = { ans, pos ->
                when (ans) {
                    EnglishTranslate.TranslateVariants.GOOD -> {
                        adapter.notifyItemRemoved(pos)
                        list.removeAt(pos)
                        adapter.bindList(list)
                    }

                    EnglishTranslate.TranslateVariants.WRONG -> {
                        Toast.makeText(
                            context,
                            "WRONG!",
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.notifyItemChanged(pos, "payload")
                    }
                }
            }

        override fun handle(ans: EnglishTranslate.TranslateVariants, adapterPosition: Int) {
            action(ans, adapterPosition)
        }
    }

    override fun onStart() {
        super.onStart()

        TimerLiveData.start()
        TimerLiveData.liveData.observe(this@QuizFragment) {
            timerStart(it)
        }
    }

    private fun timerStart(count: Int) {

        timerView.apply {
            text = count.toString()
        }
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }
}
