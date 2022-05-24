package com.example.academyhomework

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.leverx.android_modern_architecture_sample.databinding.FragmentSplashScreenfragmentBinding

class SplashScreenfragment : Fragment() {

    private var _binding: FragmentSplashScreenfragmentBinding? = null
    private val binding get() = _binding!!

    private var listener: Router? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Router) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashScreenfragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {

        binding.textViewAll.setOnClickListener {
            onAllListClick()
        }

        binding.tvER.apply {
            setOnClickListener {
                listener?.onEnglishTranslateClicked(this@SplashScreenfragment)
            }
        }
    }

    private fun onAllListClick() {
        listener?.onWordListClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStart() {
        super.onStart()
        TimerLiveData.liveData.observe(this) {
            timerStart(it)
        }
    }

    private fun timerStart(count: Int) {

        binding.tvTimer.apply {
            text = count.toString()
        }
    }
}
