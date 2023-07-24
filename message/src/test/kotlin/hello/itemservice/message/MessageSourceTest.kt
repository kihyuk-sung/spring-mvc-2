package hello.itemservice.message

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import java.util.*

@SpringBootTest
final class MessageSourceTest(
    private val ms: MessageSource,
): FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    init {
        test("hello message") {
            ms.getMessage("hello", null, Locale.ROOT) shouldBe "안녕"
        }

        test("not found message code") {
            shouldThrow<NoSuchMessageException> {
                ms.getMessage("no_code", null, Locale.ROOT)
            }
        }

        test("not found message code with default") {
            ms.getMessage("no_code", null, "기본 메시지", Locale.ROOT) shouldBe "기본 메시지"
        }

        test("argument message") {
            ms.getMessage("hello.name", arrayOf("Spring"), Locale.ROOT) shouldBe "안녕 Spring"
        }

        test("defaultLang") {
            ms.getMessage("hello", null, Locale.KOREA) shouldBe "안녕"
        }

        test("en lang") {
            ms.getMessage("hello", null, Locale.ENGLISH) shouldBe "hello"
            ms.getMessage("hello.name", arrayOf("Spring"), Locale.ENGLISH) shouldBe "hello Spring"
        }
    }
}
