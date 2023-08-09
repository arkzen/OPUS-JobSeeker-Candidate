package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bd.ewn.opus.R


class ResetPassFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_pass, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ResetPassFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}