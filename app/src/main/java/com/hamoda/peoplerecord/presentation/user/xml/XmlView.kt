package com.hamoda.peoplerecord.presentation.user.xml

import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hamoda.peoplerecord.databinding.UserFragmentBinding
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.presentation.user.UserViewModel

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

        if (!err.isValid) {
            Toast.makeText(fragment.requireContext(), "Validation error", Toast.LENGTH_SHORT).show()
        } else {
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

    setupGenderSpinner(binding = binding, fragment = fragment)
    return binding.root
}

private fun setupGenderSpinner(binding: UserFragmentBinding, fragment: Fragment) {
    val options = listOf("Male", "Female")
    val adapter =
        ArrayAdapter(fragment.requireContext(), android.R.layout.simple_spinner_item, options)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    binding.genderSpinner.adapter = adapter
}