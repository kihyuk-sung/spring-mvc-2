package hello.itemservice.web.login

import hello.itemservice.domain.login.LoginService
import hello.itemservice.web.session.SessionConstants
import hello.itemservice.web.session.SessionManager
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginController(
    private val loginService: LoginService,
    private val sessionManager: SessionManager,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/login")
    fun loginForm(@ModelAttribute("loginForm") form: LoginForm): String = "login/loginForm"


//    @PostMapping("/login")
    fun login(
        @Validated @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        log.info("form = {}", form)

        val member = with(form) {
            loginService.login(
                loginId = loginId,
                password = password,
            )
        }

        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.")
            return "login/loginForm"
        }

        // TODO: 로그인 성공 처리
        val idCookie = Cookie("memberId", member.id.toString())
        response.addCookie(idCookie)

        return "redirect:/"
    }

//    @PostMapping("/login")
    fun loginV2(
        @Validated @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        log.info("form = {}", form)

        val member = with(form) {
            loginService.login(
                loginId = loginId,
                password = password,
            )
        }

        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.")
            return "login/loginForm"
        }

        // TODO: 로그인 성공 처리
        sessionManager.createSession(member, response)

        return "redirect:/"
    }

//    @PostMapping("/login")
    fun loginV3(
        @Validated @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        log.info("form = {}", form)

        val member = with(form) {
            loginService.login(
                loginId = loginId,
                password = password,
            )
        }

        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.")
            return "login/loginForm"
        }

        // TODO: 로그인 성공 처리
        request.session.setAttribute(SessionConstants.LOGIN_MEMBER, member)

        return "redirect:/"
    }

    @PostMapping("/login")
    fun loginV4(
        @Validated @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        @RequestParam(defaultValue = "/") redirectURL: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        log.info("form = {}", form)

        val member = with(form) {
            loginService.login(
                loginId = loginId,
                password = password,
            )
        }

        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.")
            return "login/loginForm"
        }

        // TODO: 로그인 성공 처리
        request.session.setAttribute(SessionConstants.LOGIN_MEMBER, member)

        return "redirect:$redirectURL"
    }


    //    @PostMapping("logout")
    fun logout(response: HttpServletResponse): String {
        expireCookie(response, "memberId")
        return "redirect:/"
    }

//    @PostMapping("logout")
    fun logoutV2(request: HttpServletRequest): String {
        sessionManager.expire(request)
        return "redirect:/"
    }

    @PostMapping("logout")
    fun logoutV3(request: HttpServletRequest): String {
        request.getSession(false)?.invalidate()
        return "redirect:/"
    }

    fun expireCookie(response: HttpServletResponse, cookieName: String) {
        val cookie = Cookie(cookieName, null).apply {
            maxAge = 0
        }
        response.addCookie(cookie)
    }

}
