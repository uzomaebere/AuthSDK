package ng.tagithub.authmobilesdk.data.local

import android.content.Context
import ng.tagithub.authmobilesdk.model.AuthModel


object PrefManager {

    fun saveCredentials(context: Context, data: AuthModel) {
        val sharedPreferences = context.getSharedPreferences("AuthSdkPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("email", data.email)
            putString("password", data.password)
            putString("username", data.username)
            putString("firstName", data.firstName)
            putString("lastName", data.lastName)
            apply()
        }
    }

    fun getCredentials(context: Context): AuthModel? {
        val sharedPreferences = context.getSharedPreferences("AuthSdkPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        val username = sharedPreferences.getString("username", null)
        val firstName = sharedPreferences.getString("firstName", null)
        val lastName = sharedPreferences.getString("password", null)

        return if (email != null && password != null) {
            AuthModel(email, password, username.toString(), firstName.toString(),
                lastName.toString()
            )
        } else {
            null
        }
    }
}
