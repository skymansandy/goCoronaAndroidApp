package dev.skymansandy.base.ui.custom._control

interface WizardInputViewControl {
    fun validate(allowEmpty: Boolean = true): Boolean

    fun validateIfMandatory(allowEmpty: Boolean = true): Boolean

    fun isMandatory(): Boolean
}