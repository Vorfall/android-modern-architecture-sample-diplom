package com.leverx.android_modern_architecture_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leverx.android_modern_architecture_sample.databinding.ActivityMasterBinding
import com.leverx.android_modern_architecture_sample.testPlusGame.MainActivityQuiz
import com.leverx.android_modern_architecture_sample.ui.compose.MainComposeActivity
import com.leverx.android_modern_architecture_sample.ui.main.view.MainActivity

class MasterActivity : AppCompatActivity() {
    lateinit var binding: ActivityMasterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.pers.setOnClickListener {
//            val intent = Intent(this, MainComposeActivity::class.java)
//            startActivity(intent)
        }
        binding.word.setOnClickListener {
//            val intent = Intent(this, MainComposeActivity::class.java)
//            startActivity(intent)
        }
        binding.auth.setOnClickListener {
            val intent = Intent(this, MainComposeActivity::class.java)
            startActivity(intent)
        }
        binding.mainview.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.game.setOnClickListener {
            val intent = Intent(this, MainActivityQuiz::class.java)
            startActivity(intent)
        }
        binding.card.setOnClickListener {
//            val intent = Intent(this, MainComposeActivity::class.java)
//            startActivity(intent)
        }
        binding.test.setOnClickListener {
//            val intent = Intent(this, MainComposeActivity::class.java)
//            startActivity(intent)
        }
        binding.bigtest.setOnClickListener {
//            val intent = Intent(this, MainComposeActivity::class.java)
//            startActivity(intent)
        }

    }
}
