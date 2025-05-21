package ng.tagithub.authmobilesdk.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AuthConfig(
    var title: String = "Auth SDK",
    var buttonText: String? = "Login",
    var showUsername: Boolean = true,
    var showFirstName: Boolean = true,
    var showLastName: Boolean = true,
    var backgroundColor: String = "#FFFFFF",
    var textColor: String = "#000000",
    var buttonColor: String = "#6200EE",
    var buttonTextColor: String = "#FFFFFF",
    var fontFamily: String? = null
): Parcelable
