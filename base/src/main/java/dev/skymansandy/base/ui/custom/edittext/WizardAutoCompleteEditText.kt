package dev.skymansandy.base.ui.custom.edittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent.ACTION_UP
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import dev.skymansandy.base.R
import dev.skymansandy.base.ui.custom._control.WizardInputViewControl

class WizardAutoCompleteEditText(context: Context, attributeSet: AttributeSet?) :
    AppCompatAutoCompleteTextView(context, attributeSet), WizardInputViewControl {

    constructor(context: Context) : this(context, null)

    /**
     * Attributes
     */
    private var isMandatory = false
    private var watchers: ArrayList<TextWatcher?>? = null

    /**
     * Accessor vars
     */
    private val parentTil: TextInputLayout?
        get() = (parent?.parent) as? TextInputLayout

    fun getValue() = text?.toString() ?: ""

    init {
        with(context.obtainStyledAttributes(attributeSet, R.styleable.WizardAutoCompleteEditText)) {
            setMandatory(getBoolean(R.styleable.WizardAutoCompleteEditText_wizard_mandatory, true))
            recycle()
        }.also {
            watchers = ArrayList()
        }

        setOnTouchListener { _, event ->
            if (event.action == ACTION_UP) {
                showDropDown()
                true
            } else false
        }

        clearAllTextChangedListeners()
    }

    /**
     * Public API
     */
    fun setMandatory(mandatory: Boolean) {
        isMandatory = mandatory
        if (isMandatory && !hint.contains("*")) hint = "$hint *"
    }

    /**
     * Property setters
     */
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
     * Validation methods
     */
    override fun validate(allowEmpty: Boolean): Boolean {
        return when (text?.toString()?.isNotEmpty()) {
            true -> true
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