package hello.itemservice.web.session

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class SessionInfoController {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/session-info")
    fun sessionInfo(request: HttpServletRequest): String =
        request.getSession(false)
            ?.also { it.attributeNames.asIterator().forEachRemaining { name -> log.info("session name={}, value={}", name, it.getAttribute(name)) } }
            ?.apply { log.info("sessionId={}", id) }
            ?.apply { log.info("getMaxInactiveInterval={}", maxInactiveInterval) }
            ?.apply { log.info("createTime={}", Date(creationTime)) }
            ?.apply { log.info("lastAccessedTime={}", Date(lastAccessedTime)) }
            ?.apply { log.info("isNew={}", isNew) }
            ?.let { "세션 출력" }
            ?: "세션이 없습니다."

}
