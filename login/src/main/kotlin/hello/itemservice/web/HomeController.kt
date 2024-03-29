package hello.itemservice.web

import hello.itemservice.domain.member.Member
import hello.itemservice.domain.member.MemberRepository
import hello.itemservice.web.argumentresolver.Login
import hello.itemservice.web.session.SessionConstants
import hello.itemservice.web.session.SessionManager
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.SessionAttribute

@Controller
class HomeController(
    private val memberRepository: MemberRepository,
    private val sessionManager: SessionManager,
) {

//    @GetMapping("/")
    fun homeLogin(
        @CookieValue(name = "memberId", required = false)
        memberId: Long?,
        model: Model,
    ): String = memberId
        ?.let { memberRepository.findById(it) }
        ?.let { model.addAttribute("member", it) }
        ?.let { "loginHome" }
        ?: "home"

//    @GetMapping("/")
    fun homeLoginV2(
        request: HttpServletRequest,
        model: Model,
    ): String = (sessionManager.getSession(request) as? Member)
        ?.let { memberRepository.findById(it.id) }
        ?.let { model.addAttribute("member", it) }
        ?.let { "loginHome" }
        ?: "home"

//    @GetMapping("/")
    fun homeLoginV3(
        request: HttpServletRequest,
        model: Model,
    ): String = request.getSession(false)
        ?.let { it.getAttribute(SessionConstants.LOGIN_MEMBER) as? Member }
        ?.let { memberRepository.findById(it.id) }
        ?.let { model.addAttribute("member", it) }
        ?.let { "loginHome" }
        ?: "home"

//    @GetMapping("/")
    fun homeLoginV3Spring(
        @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) member: Member?,
        model: Model,
    ): String = member
        ?.let { memberRepository.findById(it.id) }
        ?.let { model.addAttribute("member", it) }
        ?.let { "loginHome" }
        ?: "home"
    @GetMapping("/")
    fun homeLoginV3ArgumentResolver(
        @Login member: Member?,
        model: Model,
    ): String = member
        ?.let { memberRepository.findById(it.id) }
        ?.let { model.addAttribute("member", it) }
        ?.let { "loginHome" }
        ?: "home"
}
