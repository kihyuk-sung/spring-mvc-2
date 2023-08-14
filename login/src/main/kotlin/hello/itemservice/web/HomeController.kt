package hello.itemservice.web

import hello.itemservice.domain.member.MemberRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
    private val memberRepository: MemberRepository,
) {

    @GetMapping("/")
    fun homeLogin(
        @CookieValue(name = "memberId", required = false)
        memberId: Long?,
        model: Model,
    ): String = memberId
        ?.let { memberRepository.findById(it) }
        ?.let { model.addAttribute("member", it) }
        ?.let { "loginHome" }
        ?: "home"
}
