package com.leverx.android_modern_architecture_sample.ui.compose


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager

import com.leverx.android_modern_architecture_sample.compose.screen.Authorization
import com.leverx.android_modern_architecture_sample.ui.compose.screen.Registration

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        var login = prefs.getString("auth", "unknown")
//        if(login != "unknown"){
//            val intent = Intent(context, MainActivity::class.java)
//            intent.putExtra("login", login)
//            context.startActivity(intent)
//        }
        setContent {
            LoginApplication(context)
        }
    }
}

@Composable
fun LoginApplication(context: Context) {
    val navController = rememberNavController()
     NavHost(navController = navController, startDestination = "login_page", builder = {
        composable("login_page", content = { Authorization(navController = navController, context) })
        composable("register_page", content = { Registration(navController = navController, context) })
    })
}
