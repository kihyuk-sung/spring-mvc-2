package hello.thymeleaf.basic

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/basic")
class BasicController {

    @GetMapping("text-basic")
    fun textBasic(model: Model): String = "basic/text-basic"
        .apply { model.addAttribute("data", "Hello Spring!") }

    @GetMapping("text-unescaped")
    fun textUnescaped(model: Model): String = "basic/text-unescaped"
        .apply { model.addAttribute("data", "Hello <b>Spring!</b>") }

    @GetMapping("/variable")
    fun variable(model: Model): String = "basic/variable"
        .apply {
            val userA = User("userA", 10)
            val userB = User("userB", 20)
            model.addAttribute("user", userA)
                .addAttribute("users", listOf(userA, userB))
                .addAttribute("userMap", mapOf("userA" to userA, "userB" to userB))
        }

    @GetMapping("/basic-objects")
    fun basicObjects(session: HttpSession) = "basic/basic-objects"
        .apply { session.setAttribute("sessionData", "Hello Session") }

    @Component("helloBean")
    class HelloBean {
        fun hello(data: String): String = "Hello $data"
    }

    data class User (
        val username: String,
        val age: Int,
    )
}
