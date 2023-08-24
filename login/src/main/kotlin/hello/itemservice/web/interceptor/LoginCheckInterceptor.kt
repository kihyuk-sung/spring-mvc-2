package hello.itemservice.web.interceptor

import hello.itemservice.web.session.SessionConstants
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor

class LoginCheckInterceptor: HandlerInterceptor {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI =request.requestURI

        log.info("인증 체크 인터셉처 실행 {}", requestURI)
        val session = request.getSession(false)

        if (session?.getAttribute(SessionConstants.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청")

            response.sendRedirect("/login?redirectURL=$requestURI")
            return false
        }

        return true
    }
}
