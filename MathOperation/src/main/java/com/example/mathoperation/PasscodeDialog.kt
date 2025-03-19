package com.example.mathoperation.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.mathoperation.R

object PasscodeDialog {
    private lateinit var dialog: AlertDialog

    fun showPasscodeDialog(
        context: Context,
        merchantName: String,
        transactionId: String,
        amount: String,
        tax: String,
        walletFee: String,
        onSubmit: (String) -> Unit
    ) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 40)
            setBackgroundColor(Color.WHITE)
        }

        val headerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, 0, 0, 20)
        }

        val logo = ImageView(context).apply {
            setImageResource(R.drawable.evzone)
            layoutParams = LinearLayout.LayoutParams(80, 80)
        }

        val title = TextView(context).apply {
            text = "EVzone Pay"
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val closeButton = ImageView(context).apply {
            setImageResource(R.drawable.cancel)
            layoutParams = LinearLayout.LayoutParams(50, 50)
            setOnClickListener { dialog.dismiss() }
        }

        headerLayout.addView(logo)
        headerLayout.addView(title)
        headerLayout.addView(closeButton)

        val merchantText = TextView(context).apply {
            // Create a SpannableString to apply different styles to different parts of the text
            val spannable = SpannableString("Merchant Info:\n$merchantName \n$transactionId \t\t\t\t\t\t\t\t\t\t\t$amount")

            // Apply style to "Merchant Info:"
            val startMerchantInfo = 0
            val endMerchantInfo = "Merchant Info:".length
            val softGreen = Color.parseColor("#4CAF50") // Soft green color

            // Set the "Merchant Info:" part to be bold, green, and larger font size
            spannable.setSpan(ForegroundColorSpan(softGreen), startMerchantInfo, endMerchantInfo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), startMerchantInfo, endMerchantInfo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(AbsoluteSizeSpan(24, true), startMerchantInfo, endMerchantInfo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Apply style to the amount to make it bold
            val startAmount = spannable.length - amount.length
            val endAmount = spannable.length
            spannable.setSpan(StyleSpan(Typeface.BOLD), startAmount, endAmount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Set the styled text as the TextView text
            text = spannable

            // The rest of the text (merchantName, transactionId, amount) remains unaffected
            textSize = 18f // Default text size for other parts
            setTextColor(Color.BLACK) // Default text color for other parts
            gravity = Gravity.START // Align the text to the left by default

            // Optionally, use padding to adjust the layout and push the amount toward the right
            setPadding(0, 0, 50, 0) // Padding to give space on the right
        }



        // Add a label before the passcode input field
        val passcodeLabel = TextView(context).apply {
            text = "Enter Passcode : "
            textSize = 16f
            setTextColor(Color.BLACK)
            gravity = Gravity.START
            setPadding(20, 20, 20, 10) // Padding to separate from the passcode input field
        }

        val passcodeLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(10, 10, 10, 10)
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 30) // Bottom margin to separate from info box
            }
            background = context.getDrawable(R.drawable.passcode_input_border) // Apply border to the whole container
            // Optional: Apply elevation if you want a shadow effect on the whole container
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                elevation = 5f // Elevation to give a shadow effect
            }
        }

        val passcodeInput = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD // Start with password input
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            setPadding(20, 20, 20, 20)
            maxLines = 1
            minHeight = 100
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            gravity = Gravity.CENTER // Center the text inside the input field
            // Remove any internal underline (default behavior of EditText)
            background = null

            // Set max length to 6 digits
            filters = arrayOf(InputFilter.LengthFilter(6))
        }

        val toggleVisibility = ImageView(context).apply {
            setImageResource(R.drawable.ic_eye_off) // Initially set to the "eye off" icon (hidden)
            layoutParams = LinearLayout.LayoutParams(80, 80) // Size of the eye icon
            setPadding(10, 10, 10, 10)
            setOnClickListener {
                val selection = passcodeInput.selectionStart
                if (passcodeInput.inputType == (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                    passcodeInput.inputType = InputType.TYPE_CLASS_NUMBER // Show passcode (clear text)
                    setImageResource(R.drawable.ic_eye_on) // Change the icon to "eye on"
                } else {
                    passcodeInput.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD // Hide passcode
                    setImageResource(R.drawable.ic_eye_off) // Change the icon back to "eye off"
                }
                passcodeInput.setSelection(selection) // Retain cursor position
            }
        }

        // Add the EditText and ImageView to the layout
        passcodeLayout.addView(passcodeInput)
        passcodeLayout.addView(toggleVisibility)

        val infoBox = TextView(context).apply {
            text = "You are making a payment to $merchantName.\nAmount UGX $amount will be deducted from your wallet, including $tax tax and $walletFee wallet fee."
            textSize = 14f
            setTextColor(Color.BLACK)
            setPadding(20, 20, 20, 20)
            gravity = Gravity.CENTER

            // Set the background with rounded corners
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f // Set the radius of the corners (adjust as needed)
                setColor(Color.parseColor("#D9EFFF")) // Set the background color
            }
        }

        val buttonsLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 20, 0, 0)
        }

        val confirmButton = Button(context).apply {
            text = "Confirm"
            textSize = 16f
            setTextColor(Color.WHITE)
            setPadding(20, 20, 20, 20)

            // Set the background with rounded corners
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f // Adjust the radius to control the rounding
                setColor(ContextCompat.getColor(context, R.color.blue)) // Set background color
            }
            isAllCaps = false

            setOnClickListener {
                val passcode = passcodeInput.text.toString()
                if (passcode.length != 6) {
                    Toast.makeText(context, "Passcode must be 6 digits.", Toast.LENGTH_SHORT).show()
                } else {
                    onSubmit(passcode)
                    dialog.dismiss()
                }
            }
        }

        val backButton = Button(context).apply {
            text = "Back"
            textSize = 16f
            setTextColor(Color.RED)
            setBackgroundColor(Color.TRANSPARENT)
            setPadding(20, 20, 20, 20)
            setOnClickListener { dialog.dismiss() }
            isAllCaps = false
        }

        buttonsLayout.addView(confirmButton)
        buttonsLayout.addView(backButton)

        layout.addView(headerLayout)
        layout.addView(merchantText)
        layout.addView(passcodeLabel) // Add the passcode label before the input field
        layout.addView(passcodeLayout)
        layout.addView(infoBox)
        layout.addView(buttonsLayout)

        dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(false)
            .create()

        dialog.show()
    }
}
