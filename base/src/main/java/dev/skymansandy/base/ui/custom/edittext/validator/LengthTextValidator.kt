package dev.skymansandy.base.ui.custom.edittext.validator

import android.content.Context

class LengthTextValidator(
    private val context: Context,
    private val fieldName: String,
    private val minLength: Int = 0,
    private val maxLength: Int
) : TextValidator() {

    override fun isValid(content: String): TextValidationResponse {
        return when {
            content.length > maxLength ->
                TextValidationResponse(false, "MaxLength must be $maxLength")
            content.length < minLength ->
                TextValidationResponse(false, "MinLength must be $minLength")
            else ->
                TextValidationResponse(true, "")
        }
    }
}