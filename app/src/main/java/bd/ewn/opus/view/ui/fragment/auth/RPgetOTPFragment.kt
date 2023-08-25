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
import bd.ewn.opus.databinding.FragmentRPgetOTPBinding
import bd.ewn.opus.viewmodel.PassResReqViewmodel


class RPgetOTPFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentRPgetOTPBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRPgetOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()
    }

    private fun setOnclicklistener() {
        binding.btnGetOTP.setOnClickListener(this)
        binding.btnResetPassword.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnGetOTP -> {
                val email = binding.etEmailRP.text.toString()

                if (email.isEmpty()) {
                    Toast.makeText(context, "Enter Your Email", Toast.LENGTH_LONG).show()
                    return
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(context, "Please enter a valid  Email", Toast.LENGTH_LONG).show()
                    return
                }

                sendOtpToEmail(email)

            }

            R.id.btnResetPassword ->{
                Navigation.findNavController(requireView())
                    .navigate(R.id.loginFragment)
            }
        }


    }

    private fun sendOtpToEmail(email: String) {

        val sendOtpViewmodel = ViewModelProvider(this)[PassResReqViewmodel::class.java]

        sendOtpViewmodel.OtpSendResponse(requireContext(), email)
            .observe(viewLifecycleOwner, Observer { sendOtp ->

                val details = sendOtp.detail
                if (details != null) {
                    if (details.isNotEmpty()) {

                        Toast.makeText(requireContext(), details, Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_RPgetOTPFragment_to_RPsubmitOTPFragment)
                    } else Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            })

    }
}



