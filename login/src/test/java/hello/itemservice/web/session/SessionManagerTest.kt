package hello.itemservice.web.session

import hello.itemservice.domain.member.Member
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class SessionManagerTest: FunSpec({

    val sessionManager = SessionManager()

    test("session test") {
        val response = MockHttpServletResponse()

        val member = Member(
            id = 1,
            loginId = "hello",
            name = "good",
            password = "test!",
        )
        sessionManager.createSession(member, response)

        val request = MockHttpServletRequest()
        request.setCookies(*response.cookies)

        val result = sessionManager.getSession(request)

        result shouldBe member

        sessionManager.expire(request)

        val expired = sessionManager.getSession(request)
        expired shouldBe null
    }
})
