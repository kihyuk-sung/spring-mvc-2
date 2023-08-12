package hello.itemservice.domain.member

data class Member(
    val id: Long,
    val loginId: String,
    val name: String,
    val password: String,
)
