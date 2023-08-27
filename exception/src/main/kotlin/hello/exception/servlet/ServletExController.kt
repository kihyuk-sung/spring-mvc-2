package hello.exception.servlet

import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ServletExController {

    @GetMapping("/error-ex")
    fun errorEx(): Unit =
        throw RuntimeException("예외 발생!")

    @GetMapping("/error-404")
    fun error404(response: HttpServletResponse): Unit =
        response.sendError(404, "404 오류!")

    @GetMapping("/error-500")
    fun error500(response: HttpServletResponse): Unit =
        response.sendError(500, "500 오류!")

}
