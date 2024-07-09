package com.tobyproduct.phonenumbermaskapp.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.tobyproduct.phonenumbermaskapp.R
import com.tobyproduct.phonenumbermaskapp.utils.PhoneNumberMaskTextWatcher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /** Init **/
        phoneEditText = findViewById(R.id.input_phone)
        accountVisibilitySwitch = findViewById(R.id.button_account_visibility_toggle)
        passwordEditText = findViewById(R.id.input_password)
        passwordVisibilitySwitch = findViewById(R.id.button_password_visibility_toggle)
        loginButton = findViewById(R.id.login_button)
        mainLayout = findViewById(R.id.main)

        watcher = PhoneNumberMaskTextWatcher(phoneEditText, accountVisibilitySwitch, false)


        /** Event **/
        // 手機號碼
        accountVisibilitySwitch.setOnClickListener {
            watcher.toggleVisibility()
        }
        // 密碼
        passwordVisibilitySwitch.setOnClickListener {
            if(isPasswordVisible){
                passwordVisibilitySwitch.setBackgroundResource(R.drawable.ic_password_visibility_off_24)
                passwordEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                passwordVisibilitySwitch.setBackgroundResource(R.drawable.ic_password_visibility_24)
                passwordEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT
            }
            isPasswordVisible = !isPasswordVisible
            passwordEditText.setSelection(passwordEditText.text.length)
        }
        // 登入
        loginButton.setOnClickListener {
            onLoginClicked()
        }
        // 點到其他地方就收起來
        mainLayout.setOnClickListener {
            phoneEditText.clearFocus()
            passwordEditText.clearFocus()
            hideKeyboard(this)
        }


    }

    /** View **/
    private lateinit var phoneEditText: EditText
    private lateinit var accountVisibilitySwitch: Button
    private lateinit var passwordEditText: EditText
    private lateinit var passwordVisibilitySwitch: Button
    private var isPasswordVisible = false
    private lateinit var loginButton: Button
    private lateinit var watcher: PhoneNumberMaskTextWatcher
    private lateinit var mainLayout: androidx.constraintlayout.widget.ConstraintLayout

    private fun onLoginClicked() {

        phoneEditText.clearFocus()
        passwordEditText.clearFocus()
        hideKeyboard(this)

        val phone = watcher.originalPhone
        val password = passwordEditText.text.toString()
        if(phone.isEmpty()){
            showNoTitleNoCancelDialog("手機號碼不得為空")
            return
        }
        if(password.isEmpty()){
            showNoTitleNoCancelDialog("密碼不得為空")
            return
        }
        if(phone.length < 10){
            showNoTitleNoCancelDialog("手機號碼長度不得小於10位")
            return
        }
        if(!phone.startsWith("09")){
            showNoTitleNoCancelDialog("手機號碼格式不對")
            return
        }

        showLoginDialog(phone,password)

    }
    private fun showLoginDialog(phone: String, password: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("登入成功")
        dialogBuilder.setMessage("您的帳號 : $phone \n密碼 : $password")
        dialogBuilder.setPositiveButton("確定") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.dark_green))
    }

    private fun showNoTitleNoCancelDialog(content: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("登入失敗")
        dialogBuilder.setMessage(content)
        dialogBuilder.setPositiveButton("確定") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(mainLayout.windowToken, 0) // 直接從 mainLayout 取得視窗 token
    }


}