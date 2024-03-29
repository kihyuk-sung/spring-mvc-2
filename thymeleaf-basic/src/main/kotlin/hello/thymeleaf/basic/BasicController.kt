package hello.thymeleaf.basic

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime

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

    @GetMapping("/date")
    fun date(model: Model): String = "basic/date"
        .apply {
            model.addAttribute("localDateTime", LocalDateTime.now())
        }

    @GetMapping("/link")
    fun link(model: Model): String = "basic/link"
        .apply {
            model.addAttribute("param1", "data1")
                .addAttribute("param2", "data2")
        }

    @GetMapping("/literal")
    fun literal(model: Model): String = "basic/literal"
        .apply {
            model.addAttribute("data", "Spring!")
        }

    @GetMapping("/operation")
    fun operation(model: Model): String = "basic/operation"
        .apply {
            model.addAttribute("nullData", null)
                .addAttribute("data", "Spring!")
        }

    @GetMapping("/attribute")
    fun attribute(): String = "basic/attribute"

    @GetMapping("/each")
    fun each(mode: Model) = "basic/each"
        .apply {
            mode.addAttribute("users", listOf(
                User("UserA", 10),
                User("UserB", 20),
                User("UserC", 30),
            ))
        }


    @GetMapping("/condition")
    fun condition(model: Model): String = "basic/condition"
        .apply {
            model.addAttribute("users", listOf(
                User("UserA", 10),
                User("UserB", 20),
                User("UserC", 30),
            ))
        }


    @GetMapping("/comments")
    fun comments(model: Model): String = "basic/comments"
        .apply {
            model.addAttribute("data", "Spring!")
        }

    @GetMapping("/block")
    fun block(model: Model): String = "basic/block"
        .apply {
            model.addAttribute("users", listOf(
                User("UserA", 10),
                User("UserB", 20),
                User("UserC", 30),
            ))
        }

    @GetMapping("/javascript")
    fun javascript(model: Model): String = "basic/javascript"
        .apply {
            model.addAttribute("user", User("UserD", 40))
            model.addAttribute("users", listOf(
                User("UserA", 10),
                User("UserB", 20),
                User("UserC", 30),
            ))
        }

    @Component("helloBean")
    class HelloBean {
        fun hello(data: String): String = "Hello $data"
    }

    data class User (
        val username: String,
        val age: Int,
    )
}
