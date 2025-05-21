package ng.tagithub.authmobilesdk.data

import android.content.Context
import android.content.Intent
import ng.tagithub.authmobilesdk.model.AuthConfig
import ng.tagithub.authmobilesdk.model.ResultCallback
import ng.tagithub.authmobilesdk.views.AuthenticationActivity

object SourceidAuthSdk {

    private var config: AuthConfig? = null
    private var resultCallback: ResultCallback? = null

    fun initialize(config: AuthConfig) {
        SourceidAuthSdk.config = config
    }

    fun getConfig(): AuthConfig? {
        return config
    }

    fun launchAuthScreen(context: Context, callback: ResultCallback) {
        resultCallback = callback
        val intent = Intent(context, AuthenticationActivity::class.java).apply {
            putExtra("config", config)
        }
        context.startActivity(intent)
    }

    internal fun sendResult(result: String) {
        resultCallback?.onSuccess(result)
    }

    internal fun sendError(error: String) {
        resultCallback?.onError(error)
    }
}