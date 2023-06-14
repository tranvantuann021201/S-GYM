package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.s_gym.MainActivity
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentSettingBinding
import com.example.s_gym.login.LoginActivity
import com.example.s_gym.ui.viewmodel.SettingViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModelFactory: SettingViewModel.SettingViewModelFactory
    private lateinit var viewModel: SettingViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = SettingViewModel.SettingViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (user != null) {
            viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
                binding.txtUserName.text = user.name
                Glide.with(requireContext()).load(user.photoUrl)
                    .into(binding.imgUserPhoto)
            }
            viewModel.getUserById(user!!.uid)
        }

        binding.txtMyProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_myProfileFragment)
        }

        binding.txtExerSetting.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_fitnessSettingFragment)
        }

        binding.txtRemind.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_remindFragment)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.txtSignOut.setOnClickListener {
            if(user != null) {
                Firebase.auth.signOut()
                googleSignInClient.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                MainActivity().finish()
            }
            else
                Toast.makeText(requireContext(), "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show()
        }
    }
}