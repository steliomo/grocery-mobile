package mz.co.commandline.grocery.validator

import com.google.android.material.textfield.TextInputLayout
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import java.math.BigDecimal

class UnexpectedValuesValidator(private val textInputLayout: TextInputLayout, private val expectedValue: BigDecimal, val message: String) : Validator {

    private var validator: Validator? = null

    init {
        validator = DefaultValidator(textInputLayout);
    }

    override fun isValid(): Boolean {

        if (validator?.isValid == false) {
            return false
        }

        val value = BigDecimal(TextInputLayoutUtil.getInpuText(textInputLayout))

        if (value.compareTo(expectedValue) == BigDecimal.ONE.toInt()) {
            textInputLayout.error = message
            return false
        }

        return true;
    }
}