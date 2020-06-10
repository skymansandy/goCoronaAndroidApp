package dev.skymansandy.base.ui.custom.edittext.validator

abstract class TextValidator {
    abstract fun isValid(content: String): TextValidationResponse
}

class TextValidationResponse(
    var isValid: Boolean = true,
    var error: String = ""
)