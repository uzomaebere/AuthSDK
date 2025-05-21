package ng.tagithub.authmobilesdk.model

interface ResultCallback {
    fun onSuccess(result: String)
    fun onError(error: String)
}