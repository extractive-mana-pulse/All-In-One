package com.example.domain.use_case

import com.example.domain.model.ValidationResult

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email can't be blank"
            )
        }

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        if(!emailRegex.matches(email)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid email format"
            )
        }

        return ValidationResult(successful = true)
    }
}