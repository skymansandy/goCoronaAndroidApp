package dev.skymansandy.base.ui.custom.edittext

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import dev.skymansandy.base.R
import dev.skymansandy.base.ui.custom._control.WizardInputViewControl
import dev.skymansandy.base.ui.custom.edittext.validator.LengthTextValidator
import dev.skymansandy.base.ui.custom.edittext.validator.PatternTextValidator
import dev.skymansandy.base.ui.custom.edittext.validator.TextValidator


class WizardEditText(context: Context, attributeSet: AttributeSet?) :
    AppCompatEditText(context, attributeSet), WizardInputViewControl {

    companion object {
        const val PATTERN_MOBILE = "^([6-9]{1})([0-9]{9})\$"
        const val PATTERN_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$"
    }

    constructor(context: Context) : this(context, null)

    /**
     * Attributes
     */
    private var isMandatory = false
    private var wizardInputType = WizardInputType.TYPE_TEXT
    private var watchers: ArrayList<TextWatcher?>? = null
    private var validators: ArrayList<TextValidator>? = null

    /**
     * Accessor vars
     */
    private val parentTil: TextInputLayout?
        get() = (parent?.parent) as? TextInputLayout

    fun getValue() = text?.toString() ?: ""

    init {
        with(context.obtainStyledAttributes(attributeSet, R.styleable.WizardEditText)) {
            setMandatory(getBoolean(R.styleable.WizardEditText_wizard_mandatory, true))
            setWizardInputType(
                getInt(R.styleable.WizardEditText_wizard_input_type, WizardInputType.TYPE_TEXT)
            )
            recycle()
        }.also {
            watchers = ArrayList()
            validators = ArrayList()
            initWizardType()
        }
    }

    private fun initWizardType() {
        clearAllTextChangedListeners()
        clearValidators()
        clearFilters()

        isAllCaps = false
        inputType = when (wizardInputType) {
            WizardInputType.TYPE_NAME -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            WizardInputType.TYPE_UNIQUE_ID -> InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            WizardInputType.TYPE_PIN_DIGITS -> InputType.TYPE_NUMBER_VARIATION_PASSWORD
            WizardInputType.TYPE_PIN_ALPHANUMERIC -> InputType.TYPE_TEXT_VARIATION_PASSWORD
            WizardInputType.TYPE_EMAIL -> {
                addValidator(PatternTextValidator(context, hint.toString(), PATTERN_EMAIL))
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            WizardInputType.TYPE_MOBILE -> {
                addValidator(PatternTextValidator(context, hint.toString(), PATTERN_MOBILE))
                addValidator(LengthTextValidator(context, hint.toString(), 10, 10))
                setInputMaxLength(10)
                InputType.TYPE_CLASS_PHONE
            }
            WizardInputType.TYPE_LONG_DESCRIPTION -> InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            else -> InputType.TYPE_CLASS_TEXT
        }
    }

    /**
     * Public API
     */
    fun setWizardInputType(wizardInputType: Int) {
        this.wizardInputType = wizardInputType
    }

    fun setMandatory(mandatory: Boolean) {
        isMandatory = mandatory
        if (isMandatory && !hint.contains("*")) hint = "$hint *"
    }

    /**
     * Validators
     */
    private fun validateValidators(): Boolean {
        validators?.let {
            for (validator in it) {
                val response = validator.isValid(getValue())
                if (!response.isValid) {
                    setTextInputError(response.error)
                    return false
                }
            }
        }
        return true
    }

    private fun addValidator(validator: TextValidator) {
        validators?.add(validator)
    }

    private fun clearValidators() {
        validators?.clear()
    }

    /**
     * Validation methods
     */
    override fun validate(allowEmpty: Boolean): Boolean {
        return when (text?.toString()?.isNotEmpty()) {
            true -> validateValidators()
            else -> {
                setTextInputError(context.getString(R.string.mandatory_field))
                false
            }
        }
    }

    override fun validateIfMandatory(allowEmpty: Boolean): Boolean {
        return when {
            isMandatory || getValue().isNotEmpty() -> validate(allowEmpty)
            else -> true
        }
    }

    override fun isMandatory(): Boolean {
        return isMandatory
    }

    /**
     * Property setters
     */
    private fun setInputMaxLength(length: Int) {
        val filterArray = arrayOf<InputFilter>(LengthFilter(length))
        filters = filterArray
    }

    private fun clearFilters() {
        filters = emptyArray()
    }

    private fun setTextInputError(errorMsg: String?) {
        parentTil?.error = errorMsg
    }

    /**
     * Text Watchers
     */
    private fun clearAllTextChangedListeners() {
        watchers?.run {
            for (watcher in this) {
                removeTextChangedListener(watcher)
            }
            this.clear()
        }
        addTextChangedListener(DefaultTextWatcher())
    }

    override fun addTextChangedListener(watcher: TextWatcher?) {
        super.addTextChangedListener(watcher)
        watchers?.add(watcher)
    }

    /**
     * Default text watcher
     */
    inner class DefaultTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s?.isEmpty() == false) {
                setTextInputError(null)
            }
        }
    }
}