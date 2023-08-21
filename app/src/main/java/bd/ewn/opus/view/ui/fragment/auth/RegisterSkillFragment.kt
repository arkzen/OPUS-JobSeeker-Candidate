package bd.ewn.opus.view.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import bd.ewn.opus.R
import bd.ewn.opus.databinding.FragmentRegisterSkillBinding
import bd.ewn.opus.view.adapters.RegSkillAdapter

class RegisterSkillFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentRegisterSkillBinding
    private lateinit var regSkillAdapter: RegSkillAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterSkillBinding.inflate(inflater, container, false)

        val skillList = ArrayList<String>()
        skillList.add("Blockchain")
        skillList.add("Cloud Computing")
        skillList.add("English")
        skillList.add("Art & Culture")
        skillList.add("Blockchain")
        skillList.add("Blockchain")
        skillList.add("Blockchain")
        skillList.add("Blockchain")
        skillList.add("Blockchain")
        skillList.add("Blockchain")
        skillList.add("Blockchain")

        regSkillAdapter = RegSkillAdapter(skillList)

        binding.rvSkill.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = regSkillAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()
    }

    private fun setOnclicklistener() {
        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_registerSkillFragment_to_loginFragment)
            }
            R.id.btnNext -> {
                val selectedSkillsList = regSkillAdapter.getSelectedSkillsList()
                /*   val action = RegisterSkillFragmentDirections.actionRegisterSkillFragmentToRegisterFragment(selectedSkillsList.toTypedArray())
                   Navigation.findNavController(requireView()).navigate(action)
   */

            }
        }
    }}
