package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bd.ewn.opus.R
import bd.ewn.opus.databinding.FragmentLoginBinding
import bd.ewn.opus.viewmodel.LoginViewModel

class LoginFragment : Fragment(), OnClickListener {


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()
    }

    private fun setOnclicklistener() {
        binding.btnLogin.setOnClickListener(this)
        binding.tvBtnForgetPass.setOnClickListener(this)
        binding.btnCreateAccount.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btnLogin -> {
                val email = binding.edEmail.text.toString()
                val pass = binding.edPassword.text.toString()

                if (isValid()) {
                    loginCheck(email, pass)
                }
            }

            R.id.tvBtnForgetPass -> {
                Navigation.findNavController(requireView()).navigate(R.id.RPgetOTPFragment)
            }

            R.id.btnCreateAccount ->{
                Navigation.findNavController(requireView()).navigate(R.id.registerSkillFragment)
            }

        }
    }

    private fun loginCheck(email: String, pass: String) {
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel.loginResponse(requireContext(), email, pass)
            .observe(viewLifecycleOwner, Observer { loginPojo ->

                val accessToken = loginPojo.access_token
                if (accessToken.isNotEmpty()) {
                    Navigation.findNavController(requireView()).navigate(R.id.homepage)
                } else Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            })
    }

    private fun isValid(): Boolean {

        binding.edEmail.error = null
        binding.edPassword.error = null

        var isValid = true
        val email = binding.edEmail.toString()
        val pass = binding.edPassword.toString()

        if (email.isEmpty()) {
            isValid = false
            binding.edEmail.error = "Enter Your Email"
        }

        if (pass.isEmpty()) {
            isValid = false
            binding.edPassword.error = "Enter Your Password"
        }

        return isValid
    }
}