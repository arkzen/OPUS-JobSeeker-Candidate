package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bd.ewn.opus.R
import bd.ewn.opus.databinding.FragmentRPgetOTPBinding
import bd.ewn.opus.databinding.FragmentRPsubmitOTPBinding
import bd.ewn.opus.service.util.SharedPrefDataProvider
import bd.ewn.opus.viewmodel.PassResReqViewmodel

class RPsubmitOTPFragment : Fragment(),OnClickListener {

    private lateinit var binding: FragmentRPsubmitOTPBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRPsubmitOTPBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()
    }

    private fun setOnclicklistener() {
        binding.BtnSubmitOTP.setOnClickListener(this)
        binding.btnEnterOtp.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.BtnSubmitOTP -> {

                val digitOne = binding.etOtp.text.toString()
                val digitTwo = binding.etOtp1.text.toString()
                val digitThree =  binding.etOtp2.text.toString()
                val digitFour = binding.etOtp3.text.toString()

                val otpString = digitOne + digitTwo + digitThree + digitFour

                if (digitOne.isEmpty() || digitTwo.isEmpty() || digitThree.isEmpty() || digitFour.isEmpty()) {
                    Toast.makeText(context, "Please fill up all the field properly", Toast.LENGTH_SHORT)
                        .show()
                    return
                }

                context?.let { SharedPrefDataProvider.initialize(it) }
                SharedPrefDataProvider.setResetPassOtp(otpString.toInt())
                Toast.makeText(context, "Ready to create new password", Toast.LENGTH_SHORT)
                    .show()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_RPsubmitOTPFragment_to_resetPassFragment)


            }

            R.id.btnEnterOtp->{
                Navigation.findNavController(requireView())
                    .navigate(R.id.RPgetOTPFragment)
            }
        }


    }


}





