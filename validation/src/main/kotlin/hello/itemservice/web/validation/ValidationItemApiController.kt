package hello.itemservice.web.validation

import hello.itemservice.web.validation.form.ItemSaveForm
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/validation/api/items")
class ValidationItemApiController {

    val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/add")
    fun addItem(
        @RequestBody @Validated form: ItemSaveForm,
        bindingResult: BindingResult,
    ): Any {
        log.info("API 컨트롤러 호출")

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult)
            return bindingResult.allErrors
        }

        log.info("성공 로직 실행")
        return form
    }
}
