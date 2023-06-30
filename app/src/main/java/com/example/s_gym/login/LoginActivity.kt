package com.example.s_gym.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.s_gym.MainActivity
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.database.entity.User
import com.example.s_gym.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var progressDialog: ProgressDialog
    private val RC_SIGN_IN: Int = 40
    private lateinit var viewModelFactory: LoginViewModel.LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = LoginViewModel.LoginViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        init()

        binding.btnLogin.setOnClickListener {
            signIn()
        }
    }

    private fun init() {
        firebaseDatabase = FirebaseDatabase.getInstance()
//        firebaseDatabase.setPersistenceEnabled(true)
        auth = FirebaseAuth.getInstance()

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

    @SuppressLint("SuspiciousIndentation")
    private fun firebaseAuth(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)

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
                        firebaseDatabase.reference.child("User").child(user.uid).setValue(users)

                        viewModel.insertUser(users)
                        viewModel.getSetting(user.uid).observe(this, Observer { setting ->
                            val settingSnapshot = firebaseDatabase.reference.child("Setting").child(user.uid).get()
                            if (setting == null) {
                                    settingSnapshot.addOnSuccessListener {
                                        if (!it.exists()) {
                                            firebaseDatabase.reference.child("Setting").child(user.uid)
                                                .setValue(viewModel.settingDefault.copy(userId = user.uid))
                                        }
                                        else {
                                            val setting = it.getValue(Setting::class.java)
                                            viewModel.insertSetting(setting!!)
                                        }
                                    }
                                }
                        })
                        lifecycleScope.launch {
                            viewModel.allBasic(user.uid).observe(this@LoginActivity, Observer { fitnessBasics ->
                                val fBDefaultSnapshot = firebaseDatabase.reference.child("FitnessBasic")
                                    .child("default").get()
                                val fBSnapshot =
                                    firebaseDatabase.reference.child("FitnessBasic")
                                        .child(user.uid).get()
                                fBSnapshot.addOnSuccessListener { fBSnapShot ->
                                    if (!fBSnapShot.exists() && fitnessBasics.isEmpty()) {
                                        fBDefaultSnapshot.addOnSuccessListener {
                                            for(fitnessBasicSS in it.children) {
                                                val fitnessBasic = fitnessBasicSS.getValue(FitnessBasic::class.java)
                                                if (fitnessBasic != null) {
                                                    viewModel.insertFB(fitnessBasic.copy(userId = user.uid))
                                                    firebaseDatabase.reference.child("FitnessBasic")
                                                        .child(user.uid).child("${fitnessBasic.id}").setValue(fitnessBasic.copy(userId = user.uid))
                                                }
                                            }
                                        }
                                    }
                                    else if (fBSnapShot.exists() && fitnessBasics.isEmpty()) {
                                        for(fitnessBasicSS in fBSnapShot.children) {
                                            val fitnessBasic = fitnessBasicSS.getValue(FitnessBasic::class.java)
                                            if (fitnessBasic != null) {
                                                viewModel.insertFB(fitnessBasic.copy(userId = user.uid))
                                            }
                                        }
                                    }
                                }
                            })
                        }

                        viewModel.getListDay(user.uid).observe(this, Observer {
                            val listDayCloud = firebaseDatabase.reference.child("Days").child(user.uid).get()
                            listDayCloud.addOnSuccessListener { listDaySS ->
                                if (!listDaySS.exists() && it.isEmpty()) {
                                    viewModel.insertDefaultDay(user,firebaseDatabase.reference)
                                }
                                else if (listDaySS.exists() && it.isEmpty()) {
                                    viewModel.insertListDay(user,firebaseDatabase.reference, listDaySS)
                                }
                            }
                        })

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("userId", user.uid)
                        startActivity(intent)
                        finish()
                    }
                }
                else {
                    Toast.makeText(baseContext, "Không đăng nhập đc bạn ơi", Toast.LENGTH_SHORT).show()
                    // In ra lỗi
                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                }

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.result
                firebaseAuth(account.idToken!!)
            } catch (e: ApiException) {
                throw java.lang.RuntimeException(e)
            }

        }
    }
}