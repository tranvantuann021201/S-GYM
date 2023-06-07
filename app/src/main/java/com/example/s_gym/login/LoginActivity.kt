package com.example.s_gym.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.s_gym.MainActivity
import com.example.s_gym.R
import com.example.s_gym.database.entity.User
import com.example.s_gym.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GithubAuthProvider
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebase: FirebaseDatabase
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var progressDialog: ProgressDialog
    private val RC_SIGN_IN: Int = 40
    private lateinit var viewModelFactory: LoginViewModel.LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root);
        viewModelFactory = LoginViewModel.LoginViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        init()

        binding.btnLogin.setOnClickListener {
            signIn()
        }
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()

        progressDialog = ProgressDialog(baseContext)
        progressDialog.setTitle("Tạo tài khoản")
        progressDialog.setMessage("Đang tạo tài khoản")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(baseContext, gso)
    }


    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun firebaseAuth(idToken: String) {
        val credential: AuthCredential = GithubAuthProvider.getCredential(idToken)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val users = User(
                            user.uid,
                            true,
                            "02 Th12 2001",
                            60.0,
                            170.0,
                            60.0,
                            170.0,
                            user.displayName,
                            user.photoUrl.toString()
                        )
                        firebase.reference.child("User").child(user.uid).setValue(users)
                        viewModel.insertUser(users)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account: GoogleSignInAccount = task.result
                firebaseAuth(account.idToken!!)
            } catch (e: ApiException) {
                throw java.lang.RuntimeException(e)
            }

        }
    }
}