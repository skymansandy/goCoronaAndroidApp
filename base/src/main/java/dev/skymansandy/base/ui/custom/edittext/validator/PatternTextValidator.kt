package dev.skymansandy.base.ui.custom.edittext.validator

import android.content.Context
import dev.skymansandy.base.util.general.TextUtil

class PatternTextValidator(
    private val context: Context,
    private val fieldName: String,
    private val pattern: String
) : TextValidator() {

    override fun isValid(content: String): TextValidationResponse {
        return if (!TextUtil.matches(pattern, content)) {
            TextValidationResponse(false, "Pattern not matched!")
        } else {
            TextValidationResponse(true, "")
        }
    }
}