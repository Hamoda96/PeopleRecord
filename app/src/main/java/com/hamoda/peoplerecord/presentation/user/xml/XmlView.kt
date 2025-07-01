package com.hamoda.peoplerecord.presentation.user.xml

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hamoda.peoplerecord.R
import com.hamoda.peoplerecord.databinding.UserFragmentBinding
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.presentation.user.UserViewModel
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener

fun xmlView(
    binding: UserFragmentBinding,
    viewModel: UserViewModel,
    fragment: Fragment
): ScrollView {

    binding.btnSave.setOnClickListener {
        val name = binding.etName.text.toString().trim()
        val age = binding.etAge.text.toString().trim()
        val job = binding.etJob.text.toString().trim()
        val gender = binding.genderSpinner.selectedItem.toString()

        viewModel.validateUserError(
            User(
                name = name,
                age = age.toIntOrNull() ?: 0,
                jobTitle = job,
                gender = gender
            )
        )
        val err = viewModel.userValidationError


        if (err.nameError != null) {
            binding.etName.setBackgroundResource(R.drawable.edittext_error_background)
            binding.tvNameError.text = err.nameError
            binding.tvNameError.visibility = View.VISIBLE
        } else {
            binding.etName.setBackgroundResource(R.drawable.edittext_normal_background)
            binding.tvNameError.text = ""
            binding.tvNameError.visibility = View.GONE
        }

        if (err.ageError != null) {
            binding.etAge.setBackgroundResource(R.drawable.edittext_error_background)
            binding.tvAgeError.text = err.ageError
            binding.tvAgeError.visibility = View.VISIBLE
        } else {
            binding.etAge.setBackgroundResource(R.drawable.edittext_normal_background)
            binding.tvAgeError.text = ""
            binding.tvAgeError.visibility = View.GONE
        }
        // Job
        if (err.jobTitleError != null) {
            binding.etJob.setBackgroundResource(R.drawable.edittext_error_background)
            binding.tvJobError.text = err.jobTitleError
            binding.tvJobError.visibility = View.VISIBLE
        } else {
            binding.etJob.setBackgroundResource(R.drawable.edittext_normal_background)
            binding.tvJobError.text = ""
            binding.tvJobError.visibility = View.GONE
        }
        // Gender
        if (err.genderError != null) {
            binding.tvGenderError.text = err.genderError
            binding.tvGenderError.visibility = View.VISIBLE
        } else {
            binding.tvGenderError.text = ""
            binding.tvGenderError.visibility = View.GONE
        }

        if (err.isValid) {
            viewModel.addUser(
                User(
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    jobTitle = job,
                    gender = gender
                )
            )
        }
    }

    binding.btnSkip.setOnClickListener {
        viewModel.skipToUsers()
    }

    binding.etName.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            binding.etName.setBackgroundResource(R.drawable.edittext_focused_background)
        } else {
            if (viewModel.userValidationError.nameError == null) {
                binding.etName.setBackgroundResource(R.drawable.edittext_normal_background)
            }
        }
    }
    binding.etName.addTextChangedListener {
        if (viewModel.userValidationError.nameError != null) {
            val name = binding.etName.text.toString().trim()
            val err = viewModel.validateUserField("name", name)
            if (err == null) {
                binding.etName.setBackgroundResource(R.drawable.edittext_normal_background)
                binding.tvNameError.visibility = View.GONE
                viewModel.clearNameError()
            } else {
                binding.tvNameError.text = err
                binding.tvNameError.visibility = View.VISIBLE
            }
        }
    }
    binding.etAge.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            binding.etAge.setBackgroundResource(R.drawable.edittext_focused_background)
        } else {
            if (viewModel.userValidationError.ageError == null) {
                binding.etAge.setBackgroundResource(R.drawable.edittext_normal_background)
            }
        }
    }
    binding.etAge.addTextChangedListener {
        if (viewModel.userValidationError.ageError != null) {
            val age = binding.etAge.text.toString().trim()
            val err = viewModel.validateUserField("age", age)
            if (err == null) {
                binding.etAge.setBackgroundResource(R.drawable.edittext_normal_background)
                binding.tvAgeError.visibility = View.GONE
                viewModel.clearAgeError()
            } else {
                binding.tvAgeError.text = err
                binding.tvAgeError.visibility = View.VISIBLE
            }
        }
    }
    binding.etJob.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            binding.etJob.setBackgroundResource(R.drawable.edittext_focused_background)
        } else {
            if (viewModel.userValidationError.jobTitleError == null) {
                binding.etJob.setBackgroundResource(R.drawable.edittext_normal_background)
            }
        }
    }
    binding.etJob.addTextChangedListener {
        if (viewModel.userValidationError.jobTitleError != null) {
            val job = binding.etJob.text.toString().trim()
            val err = viewModel.validateUserField("jobTitle", job)
            if (err == null) {
                binding.etJob.setBackgroundResource(R.drawable.edittext_normal_background)
                binding.tvJobError.visibility = View.GONE
                viewModel.clearJobTitleError()
            } else {
                binding.tvJobError.text = err
                binding.tvJobError.visibility = View.VISIBLE
            }
        }
    }

    setupGenderSpinner(binding = binding, fragment = fragment,viewModel = viewModel)
    return binding.root
}

private fun setupGenderSpinner(binding: UserFragmentBinding, fragment: Fragment,viewModel: UserViewModel) {
    val genderList = mutableListOf("Select Gender", "Male", "Female")
    val genderAdapter = ArrayAdapter(fragment.requireContext(), android.R.layout.simple_spinner_item, genderList)
    genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    binding.genderSpinner.adapter = genderAdapter
    binding.genderSpinner.setSelection(0)
    binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            if (position != 0 && viewModel.userValidationError.genderError != null) {
                binding.tvGenderError.visibility = View.GONE
                viewModel.clearGenderError()
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
}