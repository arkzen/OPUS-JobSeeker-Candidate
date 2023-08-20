package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import bd.ewn.opus.R
import bd.ewn.opus.databinding.FragmentRPgetOTPBinding


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
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnGetOTP -> {
                val email = binding.etEmailRP.text.toString()

                Navigation.findNavController(requireView()).navigate(R.id.RPsubmitOTPFragment)
            }
        }


    }
}


