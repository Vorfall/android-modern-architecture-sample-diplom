package com.leverx.android_modern_architecture_sample.compose.viewModel

import com.leverx.android_modern_architecture_sample.util.fireBase.FirebaseUtils

class RegistrationViewModel{

    fun signIn(email: String, password: String, onSignInCompleteCallback1: (result: Boolean) -> Unit) {
        val userEmail = email.trim()
        val userPassword = password.trim()
        FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSignInCompleteCallback1.invoke(true)
                } else {
                    onSignInCompleteCallback1.invoke(false)
                }
            }
    }

}
