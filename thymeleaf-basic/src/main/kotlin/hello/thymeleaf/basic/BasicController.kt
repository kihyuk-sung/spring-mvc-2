package hello.thymeleaf.basic

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
}
