package com.example.domain.use_case

import com.example.allinone.auth.domain.model.ValidationResult

class ValidateRepeatedPassword {

    fun execute(
        validatePassword: ValidatePassword,
        password: String
    ): ValidationResult {
        // Use the validatePassword.execute method to validate the password first
        val initialValidation = validatePassword.execute(password)

        if (!initialValidation.successful) {
            return initialValidation
        }

        // Check if the password length is at least 8 characters
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }

        // Check if the password contains at least one letter and one digit
        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and one digit"
            )
        }
        return ValidationResult(successful = true)
    }
}