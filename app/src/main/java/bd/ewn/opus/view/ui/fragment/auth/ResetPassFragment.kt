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
import bd.ewn.opus.databinding.FragmentResetPassBinding
import bd.ewn.opus.viewmodel.ResetPasswordViewmodel


class ResetPassFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentResetPassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPassBinding.inflate(inflater, container, false)
        binding.cl1.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()
    }

    private fun setOnclicklistener() {
        binding.btnSaveChanges.setOnClickListener(this)
        binding.btnArrwoback.setOnClickListener(this)
        binding.btnPassHideEye.setOnClickListener(this)
        binding.btnPassHideCP1.setOnClickListener(this)
        binding.btnPassHideCP.setOnClickListener(this)
        binding.btnPassHideEye1.setOnClickListener(this)
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

                if (newPass == conPass) {
                    resetPass(newPass)
                } else Toast.makeText(context, "Password didn't matched", Toast.LENGTH_LONG).show()

            }

            R.id.btnPassHideEye -> {
                showpass1()
            }

            R.id.btnPassHideEye1 -> {
                hisepass1()
            }

            R.id.btnPassHideCP -> {
                showpass()
            }

            R.id.btnPassHideCP1 -> {
                hisepass()
            }

            R.id.btnArrwoback -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.RPsubmitOTPFragment)
            }
        }


    }

    private fun hisepass() {
        binding.btnPassHideCP.visibility = View.VISIBLE
        binding.btnPassHideCP1.visibility = View.GONE

        binding.edNewPassConfirm.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.edNewPassConfirm.text?.let { binding.edNewPassConfirm.setSelection(it.length) }
    }

    private fun showpass() {
        binding.btnPassHideCP.visibility = View.GONE
        binding.btnPassHideCP1.visibility = View.VISIBLE

        binding.edNewPassConfirm.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.edNewPassConfirm.text?.let { binding.edNewPassConfirm.setSelection(it.length) }
    }

    private fun hisepass1() {
        binding.btnPassHideEye.visibility = View.VISIBLE
        binding.btnPassHideEye1.visibility = View.GONE

        binding.edNewPass.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.edNewPass.text?.let { binding.edNewPass.setSelection(it.length) }
    }

    private fun showpass1() {
        binding.btnPassHideEye.visibility = View.GONE
        binding.btnPassHideEye1.visibility = View.VISIBLE

        binding.edNewPass.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.edNewPass.text?.let { binding.edNewPass.setSelection(it.length) }
    }

    private fun resetPass(pass: String) {

        val resetPassViewmodel = ViewModelProvider(this)[ResetPasswordViewmodel::class.java]

        resetPassViewmodel.resetPassResponse(requireContext(), pass)
            .observe(viewLifecycleOwner, Observer { resetPass ->

                val details = resetPass.detail
                if (details != null) {
                    if (details.isNotEmpty()) {

                        Toast.makeText(requireContext(), details, Toast.LENGTH_SHORT).show()

                        try {
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_resetPassFragment_to_loginFragment)
                        }catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, "Reset Password Failed", Toast.LENGTH_SHORT).show()
                        }

                    } else Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            })

    }
}