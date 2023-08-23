package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bd.ewn.opus.R
import bd.ewn.opus.databinding.FragmentRPgetOTPBinding
import bd.ewn.opus.databinding.FragmentResetPassBinding
import bd.ewn.opus.viewmodel.PassResReqViewmodel
import bd.ewn.opus.viewmodel.ResetPasswordViewmodel


class ResetPassFragment : Fragment(),OnClickListener {

    private lateinit var binding:FragmentResetPassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()
    }

    private fun setOnclicklistener() {
        binding.btnSaveChanges.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSaveChanges -> {
                val newPass = binding.edNewPass.text.toString()
                val conPass = binding.edNewPassConfirm.text.toString()



                if (newPass.isEmpty()) {
                    Toast.makeText(context, "Enter New Password", Toast.LENGTH_LONG).show()
                    return
                }
                if (conPass.isEmpty()) {
                    Toast.makeText(context, "Confirm Your Password", Toast.LENGTH_LONG).show()
                    return
                }

                if(newPass==conPass){
                    resetPass(newPass)
                }else Toast.makeText(context, "Password didn't matched", Toast.LENGTH_LONG).show()



            }
        }


    }

    private fun resetPass(pass: String) {

        val resetPassViewmodel = ViewModelProvider(this)[ResetPasswordViewmodel::class.java]

        resetPassViewmodel.resetPassResponse(requireContext(),pass)
            .observe(viewLifecycleOwner, Observer { resetPass ->

                val details = resetPass.detail
                if (details != null) {
                    if (details.isNotEmpty()) {

                        Toast.makeText(requireContext(), details, Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_resetPassFragment_to_loginFragment)
                    } else Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            })

    }
}