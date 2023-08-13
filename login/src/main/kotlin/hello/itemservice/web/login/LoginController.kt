package hello.itemservice.web.login

import hello.itemservice.domain.login.LoginService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class LoginController(
    private val loginService: LoginService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/login")
    fun loginForm(@ModelAttribute("loginForm") form: LoginForm): String = "login/loginForm"


    @PostMapping("/login")
    fun login(@Validated @ModelAttribute("loginForm") form: LoginForm, bindingResult: BindingResult): String {
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
        return "redirect:/"
    }

}