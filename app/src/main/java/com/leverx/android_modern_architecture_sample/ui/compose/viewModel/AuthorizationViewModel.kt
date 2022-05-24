package com.leverx.android_modern_architecture_sample.compose.viewModel


import com.leverx.android_modern_architecture_sample.util.fireBase.FirebaseUtils.firebaseAuth

class AuthorizationViewModel {

    fun signInUser(email: String, password: String, onSignInCompleteCallback: () -> Unit) {
        val signInEmail = email.trim()
        val signInPassword = password.trim()

        if (signInEmail.isNotEmpty() && signInPassword.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        onSignInCompleteCallback.invoke()

                    }
                }
        }
    }
}
