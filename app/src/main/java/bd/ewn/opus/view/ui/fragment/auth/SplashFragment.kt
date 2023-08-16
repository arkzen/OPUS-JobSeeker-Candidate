package bd.ewn.opus.view.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import bd.ewn.opus.R
import bd.ewn.opus.view.ui.activity.Homepage
class SplashFragment : Fragment() {
    var state:Boolean?=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Delayed navigation logic
        Handler(Looper.getMainLooper()).postDelayed({
            /*val intent = if (state == true) {
                Intent(requireContext(), Homepage::class.java)
            } else {
                Navigation.findNavController(view).navigate(R.id.loginFragment)
                null // No intent needed when navigating with NavController
            }

            intent?.let {
                startActivity(intent)
                requireActivity().finish()
            }*/
            Navigation.findNavController(view).navigate(R.id.loginFragment)

        }, 3000) // 3000 milliseconds = 3 seconds delay
    }


    }
