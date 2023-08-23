package bd.ewn.opus.view.ui.fragment.auth

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bd.ewn.opus.R
import bd.ewn.opus.databinding.FragmentRegisterBinding
import bd.ewn.opus.service.network.request.SignupBody
import bd.ewn.opus.viewmodel.SignUpViewmodel
import bd.ewn.opus.viewmodel.UserLocationViewModel


class RegisterFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var signUpViewModel:SignUpViewmodel
    private lateinit var userLocationViewModel:UserLocationViewModel

/*    private val viewModel: UserLocationViewModel by viewModels()
    private val sifnupViewModel: SignUpViewmodel by viewModels()*/
    private var lat = 34.34.toFloat()
    private var lon = 90.2.toFloat()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        signUpViewModel = ViewModelProvider(this)[SignUpViewmodel::class.java]
        userLocationViewModel = ViewModelProvider(this)[UserLocationViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclicklistener()

        val spinner = binding.spinerGender
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setOnclicklistener() {
        binding.btnBack.setOnClickListener(this)
        binding.btnCreateAccount.setOnClickListener(this)
        binding.tvLocation.setOnClickListener(this)
        binding.iconBtnLocation.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_registerFragment_to_registerSkillFragment)
            }

            R.id.btnCreateAccount -> {
                checkCandidateDetails()
                Toast.makeText(context, "  checkCandidateDetails() method called", Toast.LENGTH_LONG).show()
            }

            R.id.tvLocation -> {
                requestLocation()
                Toast.makeText(context, " requestLocation() method called", Toast.LENGTH_LONG).show()
            }

            R.id.iconBtnLocation -> {
                requestLocation()
                Toast.makeText(context, " requestLocation() method called", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkCandidateDetails() {
        val name = binding.etName.text.toString()
        val email = binding.edEmail.text.toString()
        val phnNo = binding.edPhone.text.toString()
        val pass = binding.edPassword.text.toString()
        val cPass = binding.etConfirmPass.text.toString()
        val genders = resources.getStringArray(R.array.gender)
        var selectedGender = "Male"


       /* if (name.isEmpty()) {
            Toast.makeText(context, "Enter Your Full Name", Toast.LENGTH_LONG).show()
            return
        }

        if (email.isEmpty()) {
            Toast.makeText(context, "Enter Your Email", Toast.LENGTH_LONG).show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid  Email", Toast.LENGTH_LONG).show()
            return
        }


        if (phnNo.isEmpty()) {
            Toast.makeText(context, "Enter Your Mobile No", Toast.LENGTH_LONG).show()
            return
        }

        binding.spinerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGender = genders[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        if (selectedGender.isEmpty()) {
            selectedGender="Male"
            *//*Toast.makeText(context, "Please Select Your Gender", Toast.LENGTH_LONG).show()*//*
            return
        }

        *//*if (lat.isNaN() && lon.isEmpty()) {
            Toast.makeText(context, "Please Select Your Location", Toast.LENGTH_LONG).show()
            return
        }*//*
        if (pass.isEmpty()) {
            Toast.makeText(context, "Enter Your Password", Toast.LENGTH_LONG).show()
            return
        }

        if (pass.length < 8) {
            Toast.makeText(context, "Password length should be at least 8", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (cPass.isEmpty()) {
            Toast.makeText(context, "Confirm Your Password", Toast.LENGTH_LONG).show()
            return
        }

        if (pass !== cPass) {
            Toast.makeText(context, "Your password didn't matched", Toast.LENGTH_LONG).show()
            return
        }*/

        Log.d("CDSIGNUP","Candidate SignUp Method Called")
        candidateSignUp(name, email, phnNo, selectedGender, lat, lon, pass)
        Toast.makeText(context, "  candidateSignUp method called", Toast.LENGTH_LONG).show()

    }

    private fun candidateSignUp(
        name: String,
        email: String,
        phnNo: String,
        gender: String,
        lat: Float,
        lon: Float,
        pass: String
    ) {

        val prof = "candidate"
        val fname = "Candidate"
        val summary = "summary"
        val designation = "designation"
        val city = "city"
        val country = "country"
        val id_number = "1234567890"
        val avatar = "/avatar.jpg"
        val resume = "/resume.pdf"
        val candidateSkList = ArrayList<SignupBody.CandidateSkill>()

        val account = SignupBody.Account(email, fname, name, pass)
        val candidateSkill = SignupBody.CandidateSkill(1, 3)
        candidateSkList.add(candidateSkill)
        val candidate = SignupBody.Candidate(
            summary,
            gender,
            designation,
            phnNo,
            lat,
            lon,
            city,
            country,
            id_number,
            avatar,
            resume,
            candidateSkList
        )

        val signupBody = SignupBody(prof, account, candidate)

        Log.d("CDSIGNUP","I'm in Candidate signup method")



        signUpViewModel.signUpResponse(requireContext(), signupBody)
            .observe(viewLifecycleOwner) { signUpPojo ->
                if (signUpPojo != null) {
                    val responseMessage = signUpPojo.detail
                    if (responseMessage.isEmpty()) {
                        Toast.makeText(context, "Registration Failed", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, responseMessage, Toast.LENGTH_LONG).show()
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                } else {
                    Toast.makeText(context, "Registration Failed", Toast.LENGTH_LONG).show()

                }


            }


    }

    private fun requestLocation() {
        val PERMISSION_REQUEST_CODE = 100
        Toast.makeText(context, "  requestLocation working", Toast.LENGTH_LONG).show()
        val permissionCheck = PackageManager.PERMISSION_GRANTED
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (fineLocationPermission != permissionCheck || coarseLocationPermission != permissionCheck) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        Log.d("CDSIGNUP","Location permission not granted")
        } else {

            userLocationViewModel.getCurrentLocation(requireContext())
                .observe(viewLifecycleOwner, Observer { locationModel ->
                    binding.tvLocation.text = locationModel.location

                    Toast.makeText(context, "  Location updated", Toast.LENGTH_LONG).show()
                    Log.d("CDSIGNUP","Location: ${locationModel.location}")
                    this.lat = locationModel.lat.toFloat()
                    this.lon = locationModel.lan.toFloat()
                })
        }
    }

}