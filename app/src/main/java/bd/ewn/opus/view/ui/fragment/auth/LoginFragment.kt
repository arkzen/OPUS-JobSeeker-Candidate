package bd.ewn.opus.view.ui.fragment.auth

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import bd.ewn.opus.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        val password = binding.edPassword

        binding.btnPassHideEye.setOnClickListener {
            binding.btnPassHideEye.visibility = View.GONE
            binding.btnPassHideEye1.visibility = View.VISIBLE


            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


//            binding.edPassword.setRawInputType()
        }

        binding.btnPassHideEye1.setOnClickListener {
            binding.btnPassHideEye.visibility = View.VISIBLE
            binding.btnPassHideEye1.visibility = View.GONE

            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }




        binding.cvAppLang.setOnClickListener {
            changeLanguage()
        }
        val view = binding.root
        return view




    }

    private fun changeLanguage() {
            binding.cvAppLang.visibility = View.GONE
            binding.cvLangSelect.visibility = View.VISIBLE

            binding.btnEnglish.setOnClickListener {
                Toast.makeText(context, " English Selected!", Toast.LENGTH_SHORT).show()
                binding.btnImgCheckEn.visibility = View.VISIBLE
                binding.btnImgCheckAr.visibility = View.INVISIBLE
                binding.btnEnglish.setTextColor(Color.GREEN)
                binding.btnArabic.setTextColor(Color.GRAY)
            }

            binding.btnArabic.setOnClickListener {
                Toast.makeText(context, " Arabic Selected!", Toast.LENGTH_SHORT).show()
                binding.btnImgCheckEn.visibility = View.INVISIBLE
                binding.btnImgCheckAr.visibility = View.VISIBLE
                binding.btnEnglish.setTextColor(Color.GRAY)
                binding.btnArabic.setTextColor(Color.GREEN)
            }

            binding.btnArrowSpinner.setOnClickListener {
                binding.cvLangSelect.visibility = View.GONE
                binding.cvAppLang.visibility = View.VISIBLE
            }


    }


    companion object {

        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}