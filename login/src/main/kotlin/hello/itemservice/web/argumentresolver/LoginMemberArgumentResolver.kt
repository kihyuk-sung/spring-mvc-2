package hello.itemservice.web.argumentresolver

import hello.itemservice.domain.member.Member
import hello.itemservice.web.session.SessionConstants
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginMemberArgumentResolver : HandlerMethodArgumentResolver {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        log.info("supportsParameter 실행")
            .let {
                parameter.hasParameterAnnotation(Login::class.java) &&
                        Member::class.java.isAssignableFrom(parameter.parameterType)
            }


    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? =
        (webRequest.nativeRequest as HttpServletRequest)
            .getSession(false)
            ?.getAttribute(SessionConstants.LOGIN_MEMBER)
            .apply { log.info("resoleArgument 실행") }
}
