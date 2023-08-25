package bd.ewn.opus.service.network.request

data class SignupBody(
    val profile: String,
    val account: Account,
    val candidate: Candidate
)
{
    data class Account(
        val email: String,
        val first_name: String,
        val last_name: String,
        val password: String
    )
    data class Candidate(
        val summary: String,
        val gender: String,
        val designation: String,
        val phone_number: String,
        val lat: Float,
        val lon: Float,
        val city: String,
        val country: String,
        val id_number: String,
        val avatar: String,
        val resume: String,
        val candidate_skill: List<CandidateSkill>,

    )
    data class CandidateSkill(
        val skill_id: Int,
        val skill_level: Int
    )
}