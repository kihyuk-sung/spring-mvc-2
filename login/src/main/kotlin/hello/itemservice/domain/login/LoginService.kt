package hello.itemservice.domain.login

import hello.itemservice.domain.member.Member
import hello.itemservice.domain.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val memberRepository: MemberRepository,
) {

    fun login(loginId: String, password: String): Member? = memberRepository
        .findByLoginId(loginId)
        ?.takeIf { it.password == password }

}
