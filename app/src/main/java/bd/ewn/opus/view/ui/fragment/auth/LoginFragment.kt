package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import android.text.InputType
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
import bd.ewn.opus.service.util.SharedPrefDataProvider
import bd.ewn.opus.viewmodel.LoginViewModel
import java.util.Locale


class LoginFragment : Fragment(), OnClickListener {


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        if ( Locale.getDefault().language.equals("en")){
            binding.btnImgCheckEn.visibility = View.VISIBLE
            binding.btnImgCheckAr.visibility = View.INVISIBLE
            binding.btnEnglish.setTextColor(resources.getColor(R.color.green))
            binding.btnArabic.setTextColor(resources.getColor(R.color.gray))


        }
        else if (Locale.getDefault().language.equals("ar")){
            binding.btnImgCheckEn.visibility = View.INVISIBLE
            binding.btnImgCheckAr.visibility = View.VISIBLE
            binding.btnEnglish.setTextColor(resources.getColor(R.color.gray))
            binding.btnArabic.setTextColor(resources.getColor(R.color.green))
            binding.cvAppLang.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.cvLangSelect.layoutDirection =  View.LAYOUT_DIRECTION_RTL
            binding.cvLoginView1.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.edPassword.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
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
        binding.cvAppLang.setOnClickListener (this)
        binding.btnPassHideEye.setOnClickListener(this)
        binding.btnPassHideEye1.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.cvAppLang ->{
                slectLang()
            }

            R.id.tvBtnForgetPass -> {
                Navigation.findNavController(requireView()).navigate(R.id.RPgetOTPFragment)
            }

            R.id.btnCreateAccount ->{
                Navigation.findNavController(requireView()).navigate(R.id.registerSkillFragment)
            }
            R.id.btnPassHideEye ->{
                showpass()
            }
            R.id.btnPassHideEye1 ->{


                hidePass()
            }

            R.id.btnLogin -> {
                val email = binding.edEmail.text.toString()
                val pass = binding.edPassword.text.toString()

                if (isValid()) {
                    loginCheck(email, pass)
                }

            }

        }

    }

    private fun showpass() {

        binding.btnPassHideEye.visibility = View.GONE
        binding.btnPassHideEye1.visibility = View.VISIBLE

        binding.edPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.edPassword.text?.let { binding.edPassword.setSelection(it.length) }




    }


    private fun hidePass() {

        binding.btnPassHideEye.visibility = View.VISIBLE
        binding.btnPassHideEye1.visibility = View.GONE

        binding.edPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.edPassword.text?.let { binding.edPassword.setSelection(it.length) }
    }

    private fun slectLang() {
        binding.cvAppLang.visibility = View.GONE
        binding.cvLangSelect.visibility = View.VISIBLE
        SharedPrefDataProvider.initialize(requireContext())

        binding.btnEnglish.setOnClickListener {

          if ( Locale.getDefault().language.equals("en")){

              binding.btnImgCheckEn.visibility = View.VISIBLE
              binding.btnImgCheckAr.visibility = View.INVISIBLE
              binding.btnEnglish.setTextColor(resources.getColor(R.color.green))
              binding.btnArabic.setTextColor(resources.getColor(R.color.gray))
          }
            SharedPrefDataProvider.setAppLanguage(requireContext(), "en")
            requireActivity().recreate()


        }

        binding.btnArabic.setOnClickListener {
            SharedPrefDataProvider.setAppLanguage(requireContext(), "ar")
            requireActivity().recreate()

            binding.btnImgCheckEn.visibility = View.INVISIBLE
            binding.btnImgCheckAr.visibility = View.VISIBLE
            binding.btnEnglish.setTextColor(resources.getColor(R.color.gray))
            binding.btnArabic.setTextColor(resources.getColor(R.color.green))
        }

        binding.btnArrowSpinner.setOnClickListener {
            binding.cvLangSelect.visibility = View.GONE
            binding.cvAppLang.visibility = View.VISIBLE
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