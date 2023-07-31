package hello.itemservice.validation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import org.springframework.validation.DefaultMessageCodesResolver

class MessageCodesResolverTest : FunSpec({
    val codeResolver = DefaultMessageCodesResolver()
    test("messageCodesResolverObject") {
        val messageCodes = codeResolver.resolveMessageCodes("required", "item")

        messageCodes shouldContainExactly arrayOf("required.item", "required")
    }

    test("messageCodesResolverField") {
        val messageCodes = codeResolver.resolveMessageCodes("required", "item", "itemName", String::class.java)

        messageCodes shouldContainExactly arrayOf(
            "required.item.itemName",
            "required.itemName",
            "required.java.lang.String",
            "required"
        )
    }
})
