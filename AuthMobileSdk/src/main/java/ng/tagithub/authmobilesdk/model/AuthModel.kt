package ng.tagithub.authmobilesdk.model

import org.json.JSONObject

data class AuthModel(
    val email: String,
    val password: String,
    val username: String,
    val firstName: String,
    val lastName: String
)
//{
//    fun toJson(): String {
//        return JSONObject().apply {
//            put("email", email)
//            put("password", password)
//            put("username", username)
//            put("firstName", firstName)
//            put("lastName", lastName)
//        }.toString()
//    }
//}
