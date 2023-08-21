package hello.itemservice.web.filter

import hello.itemservice.web.session.SessionConstants
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.util.PatternMatchUtils

class LoginCheckFilter: Filter {

    companion object {
        private val whitelist = arrayOf("/", "/members/add", "/login", "/logout", "/css/*")
    }

    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val requestURI = httpRequest.requestURI

        val httpResponse = response as HttpServletResponse

        try {
            log.info("인증 체크 필터 시작 {}", requestURI)

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI)
                val session = httpRequest.getSession(false)

                if (session?.getAttribute(SessionConstants.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI)
                    httpResponse.sendRedirect("/login?redirectURL=$requestURI")
                    return
                }
            }

            chain.doFilter(request, response)
        } catch (e: Exception) {
            throw e
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI)
        }
    }

    private fun isLoginCheckPath(requestURI: String): Boolean =
        !PatternMatchUtils.simpleMatch(whitelist, requestURI)
}
