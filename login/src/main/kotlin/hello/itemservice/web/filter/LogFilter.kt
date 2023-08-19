package hello.itemservice.web.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.UUID

class LogFilter: Filter {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun init(filterConfig: FilterConfig?): Unit =
        log.info("log filter init")



    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        log.info("log filter doFilter")
        val httpRequest = request as HttpServletRequest
        val requestURI = httpRequest.requestURI
        val uuid = UUID.randomUUID().toString()

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI)
            chain.doFilter(request, response)
        } catch (e: Exception) {
            throw e
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI)
        }
    }


    override fun destroy(): Unit =
        log.info("log filter destroy")

}
