package com.leverx.android_modern_architecture_sample.compose.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.compose.viewModel.AuthorizationViewModel
import com.leverx.android_modern_architecture_sample.ui.main.view.MainActivity

@Composable
fun Authorization(navController: NavController, context: Context) {
    val email = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(

            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "print",
                modifier = Modifier.fillMaxSize(1 / 3f)
            )

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = "Login") },
                singleLine = true,
            )

            TextField(
                value = passwordValue.value,
                label = { Text("Password") },
                onValueChange = { passwordValue.value = it },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            tint = if (passwordVisibility.value) Color(0xFFFFCB2B) else Color.Gray,
                            contentDescription = "print",
                            modifier = Modifier.fillMaxWidth(1 / 7f)

                        )
                    }
                },
                singleLine = true,
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier.padding(15.dp)

            )
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(1 / 3f),
                onClick = {
                    val signing = AuthorizationViewModel()

                    signing.signInUser(email.value, passwordValue.value,) {
                        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
                        prefs.edit()
                            .putString("auth", email.value)
                            .apply()

                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("login", email.value)
                        context.startActivity(intent)
                    }
                },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFFCB2B),
                    contentColor = Color(0xFFFFF5EE),
                    disabledBackgroundColor = Color(0xFF2067D3),
                    disabledContentColor = Color(0xFFE6991B)
                ),

            ) {
                Text(text = "Sign in")
            }
            Button(
                onClick = { navController.navigate("register_page") },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(1 / 3f),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF67C23F),
                    contentColor = Color(0xFFFFF5EE),
                    disabledBackgroundColor = Color(0xFF2067D3),
                    disabledContentColor = Color(0xFFE6991B)
                ),

            ) {
                Text(text = "Register")
            }
        }
    }
}
