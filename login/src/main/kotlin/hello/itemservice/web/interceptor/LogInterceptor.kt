package hello.itemservice.web.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.UUID

class LogInterceptor: HandlerInterceptor {
    companion object {
        private const val LOG_ID = "logID"
    }

    private val log = LoggerFactory.getLogger(javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI = request.requestURI
        val uuid = UUID.randomUUID().toString()

        request.setAttribute(LOG_ID, uuid)

        if (handler is HandlerMethod) {
            handler
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler)
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        log.info("postHandle [{}]", modelAndView)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val requestURI = request.requestURI
        val logId = request.getAttribute(LOG_ID) as String

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler)

        ex?.let { log.error("afterCompletion error!!", it) }
    }
}
