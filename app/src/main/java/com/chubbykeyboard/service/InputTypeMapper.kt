package com.chubbykeyboard.service

import android.text.InputType

//TODO: Implement InputTypeMapper
//TODO: probably need to add ime action handling
class InputTypeMapper {
    /**
     * Process input type for all TEXT_TYPE_VARIATION mentioned by
     * https://developer.android.com/reference/android/text/InputType
     */
    fun process(inputType: Int) {
        when (inputType and InputType.TYPE_MASK_CLASS) {
            InputType.TYPE_CLASS_TEXT -> {
                handleTextVariatioins(inputType)
            }

            InputType.TYPE_CLASS_NUMBER -> {
                handleNumberVariations(inputType)
            }

            InputType.TYPE_CLASS_PHONE -> {

            }

            InputType.TYPE_CLASS_DATETIME -> {
                // Handle date time class
                handleDateVariation(inputType)
            }
        }
    }

    private fun handleDateVariation(inputType: Int) {
        when (inputType and InputType.TYPE_MASK_VARIATION) {
            InputType.TYPE_DATETIME_VARIATION_DATE -> {
                // Handle date time variation
            }

            InputType.TYPE_DATETIME_VARIATION_TIME -> {
                // Handle time variation
            }

            InputType.TYPE_DATETIME_VARIATION_NORMAL -> {
                // Handle normal date time variation
            }
        }
    }

    private fun handleNumberVariations(inputType: Int) {
        when (inputType and InputType.TYPE_MASK_VARIATION) {
            InputType.TYPE_NUMBER_VARIATION_NORMAL -> {
                // Handle normal number variation
            }

            InputType.TYPE_NUMBER_VARIATION_PASSWORD -> {
                // Handle password number variation
            }
        }
    }

    private fun handleTextVariatioins(inputType: Int) {
        when (inputType and InputType.TYPE_MASK_VARIATION) {
            InputType.TYPE_TEXT_VARIATION_NORMAL -> {
                // Handle normal text variation
            }

            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
                // Handle email address text variation
            }

            InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                // Handle password text variation
            }

            InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS -> {
                // Handle web email address text variation
            }

            InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD -> {
                // Handle web password text variation
            }

            InputType.TYPE_TEXT_VARIATION_PERSON_NAME -> {
                // Handle person name text variation
            }

            InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS -> {
                // Handle postal address text variation
            }

            InputType.TYPE_TEXT_VARIATION_URI -> {
                // Handle URI text variation
            }

            InputType.TYPE_TEXT_VARIATION_FILTER -> {
                // Handle filter text variation
            }

            InputType.TYPE_TEXT_VARIATION_PHONETIC -> {
                // Handle phonetic text variation
            }

            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD -> {
                // Handle visible password text variation
            }

            InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT -> {
                // Handle web edit text variation
            }

            else -> {
                // Handle unknown text variation
            }
        }
    }
}