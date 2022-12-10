package com.usfaa.cvbuilder.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.usfaa.cvbuilder.ui.base.BaseFragment
import com.usfaa.cvbuilder.MainActivity
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.databinding.LoginFragmentBinding

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAuthenticate.setOnClickListener {
            viewModel.validateUserInput(binding.email.editText?.text.toString(),
                binding.password.editText?.text.toString())
        }
        binding.signup.setOnClickListener {
            showToastMessage("User login are (Username: john@gmail.com, Password: 123456789)")
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.run {
            getIsLoadingLiveData().observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    binding.buttonAuthenticate.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.buttonAuthenticate.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
            getErrorLiveData().observe(viewLifecycleOwner) { error ->
                if (error.isNotEmpty()) {
                    showToastMessage(error)
                }
            }
            getUserLiveData().observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    goToHomePage()
                }
            }
        }
    }

    private fun goToHomePage() {
        activity.takeIf { it is MainActivity }?.let {
            (it as MainActivity).setInitialScreen(R.id.navigation_home)
        }
    }

    private fun showToastMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun shouldShowBottomNavigation() = false

}