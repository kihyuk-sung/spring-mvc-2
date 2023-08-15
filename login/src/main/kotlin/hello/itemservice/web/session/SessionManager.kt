package hello.itemservice.web.session

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionManager {

    companion object {
        private const val SESSION_COOKIE_NAME = "mySessionId"
    }

    private val sessionStore: MutableMap<String, Any> = ConcurrentHashMap()

    fun createSession(value: Any, response: HttpServletResponse): Unit =
        UUID.randomUUID().toString()
            .also { sessionStore[it] = value }
            .let { Cookie(SESSION_COOKIE_NAME, it) }
            .let { response.addCookie(it) }

    fun getSession(request: HttpServletRequest): Any? =
        findCookie(request, SESSION_COOKIE_NAME)
            ?.let { sessionStore[it.value] }

    fun expire(request: HttpServletRequest): Unit =
        findCookie(request, SESSION_COOKIE_NAME)
            ?.let {
                sessionStore.remove(it.value)
                Unit
            }
            ?: Unit

    fun findCookie(request: HttpServletRequest, cookieName: String): Cookie? =
        request
            .cookies
            ?.find { it.name == cookieName }

}
