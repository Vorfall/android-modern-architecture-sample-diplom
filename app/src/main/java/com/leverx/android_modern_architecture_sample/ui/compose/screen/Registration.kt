package com.leverx.android_modern_architecture_sample.ui.compose.screen

import android.content.Context
import android.widget.Toast
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
import androidx.navigation.NavController
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.compose.viewModel.RegistrationViewModel

@Composable
fun Registration(navController: NavController, context: Context) {
    val email = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordCopyValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),


        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.long_logo),
            contentDescription = "print",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(2 / 9f)
                .padding(25.dp)

        )

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(text = "Email") },
            singleLine = true,
            modifier = Modifier.padding(15.dp)
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
        TextField(
            value = passwordCopyValue.value,
            label = { Text("Password") },
            onValueChange = { passwordCopyValue.value = it },
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
                .padding(15.dp)
                .fillMaxWidth(1 / 3f),
            onClick = {
                val signing = RegistrationViewModel()
                if (passwordCopyValue.value == passwordValue.value) {
                    signing.signIn(email.value, passwordValue.value) { result ->
                        if (result) {
                            navController.navigate("login_page")
                        } else {
                            Toast.makeText(context, "Failed registration", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            },
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
