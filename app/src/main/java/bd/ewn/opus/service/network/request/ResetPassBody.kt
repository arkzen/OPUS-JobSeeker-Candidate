package bd.ewn.opus.service.network.request

data class ResetPassBody(
    val username: String,
    val new_password: String,
    val otp: Int
)