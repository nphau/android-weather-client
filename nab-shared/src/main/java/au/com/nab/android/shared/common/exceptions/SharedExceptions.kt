package au.com.nab.android.shared.common.exceptions

sealed class SharedExceptions(message: String? = "") : RuntimeException(message) {
    object NoNetworkConnection : SharedExceptions()
}
