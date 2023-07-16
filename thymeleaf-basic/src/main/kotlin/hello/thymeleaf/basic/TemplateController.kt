package hello.thymeleaf.basic

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/template")
class TemplateController {

    @GetMapping("/fragment")
    fun template(): String = "template/fragment/fragmentMain"

    @GetMapping("/layout")
    fun layout(): String = "template/layout/layoutMain"
}
