package ng.tagithub.authmobilesdk.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.databinding.DataBindingUtil
import ng.tagithub.authmobilesdk.R
import ng.tagithub.authmobilesdk.data.SourceidAuthSdk
import ng.tagithub.authmobilesdk.data.local.PrefManager
import ng.tagithub.authmobilesdk.model.AuthConfig
//import ng.tagithub.authmobilesdk.databinding.ActivityAuthenticationBinding
import ng.tagithub.authmobilesdk.model.AuthModel
import org.json.JSONObject

class AuthenticationActivity : AppCompatActivity() {

    //  private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var config: AuthConfig

    private lateinit var btnSubmit: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var username: EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
        setContentView(R.layout.activity_authentication)

        config = SourceidAuthSdk.getConfig()!!

        btnSubmit = findViewById(R.id.btnSubmit)
        email = findViewById(R.id.edtEmailAddress)
        password = findViewById(R.id.edtPassword)
        username = findViewById(R.id.edtUsername)
        firstName = findViewById(R.id.edtFirstName)
        lastName = findViewById(R.id.edtLastName)

        setupUI()

        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!USERNAME_REGEX.matches(s.toString())) {
                    username.error = "No spaces or special characters allowed."
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnSubmit.setOnClickListener {
            submitInput()
        }
    }

    private fun submitInput() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            email.error = "Invalid email format"
            return
        }
        if (password.text.length < 8) {
            password.error = "Password must be at least 8 characters"
            return
        }
        if (!USERNAME_REGEX.matches(username.text)) {
            username.error = "Invalid username. No spaces or special characters."
            return
        }

        // Create AuthData object
        val authData = AuthModel(
            email = email.text.toString(),
            password = password.text.toString(),
            username = username.text.toString(),
            firstName = firstName.text.toString(),
            lastName = lastName.text.toString()
        )

        // store credentials
        PrefManager.saveCredentials(this, authData)

        // Create a JSON Object with user input
        val userInput = JSONObject().apply {
            put("email", authData.email)
            put("password", authData.password)
            put("username", authData.username)
            put("firstName", authData.firstName)
            put("lastName", authData.lastName)
        }.toString()

        SourceidAuthSdk.sendResult(userInput)
        finish()

    }

    private fun setupUI() {
        val rootView = findViewById<ConstraintLayout>(R.id.rootLayout)
        val titleText = findViewById<TextView>(R.id.txtTitle)
        val usernameText = findViewById<TextView>(R.id.textView4)
        val firstNameText = findViewById<TextView>(R.id.textView2)
        val lastNameText = findViewById<TextView>(R.id.textView3)

        // Apply UI customization
        rootView.setBackgroundColor(Color.parseColor(config!!.backgroundColor))
        titleText.text = config!!.title
        titleText.setTextColor(Color.parseColor(config!!.textColor))

        btnSubmit.text = config!!.buttonText
        btnSubmit.setBackgroundColor(Color.parseColor(config!!.buttonColor))
        btnSubmit.setTextColor(Color.parseColor(config!!.buttonTextColor))

        // Show/Hide Optional Fields
        if (config!!.showUsername) {
            username.visibility = View.VISIBLE
            usernameText.visibility = View.VISIBLE
        } else {
            username.visibility = View.GONE
            usernameText.visibility = View.GONE
        }

        if (config!!.showFirstName) {
            firstName.visibility = View.VISIBLE
            firstNameText.visibility = View.VISIBLE
        } else {
            firstName.visibility = View.GONE
            firstNameText.visibility = View.GONE
        }

        if (config!!.showLastName) {
            lastName.visibility = View.VISIBLE
            lastNameText.visibility = View.VISIBLE
        } else {
            lastName.visibility = View.GONE
            lastNameText.visibility = View.GONE
        }
    }

    companion object {
        private val USERNAME_REGEX = Regex("^[a-zA-Z0-9]*$")
    }
}