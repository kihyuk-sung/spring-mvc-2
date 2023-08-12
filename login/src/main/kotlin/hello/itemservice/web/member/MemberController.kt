package hello.itemservice.web.member

import hello.itemservice.domain.IdGenerator
import hello.itemservice.domain.member.Member
import hello.itemservice.domain.member.MemberRepository
import hello.itemservice.web.member.form.MemberSaveForm
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/members")
class MemberController(
    private val memberRepository: MemberRepository,
    @Qualifier("memberIdGenerator")
    private val idGenerator: IdGenerator,
) {

    @GetMapping("/add")
    fun addForm(@ModelAttribute("member") form: MemberSaveForm): String = "members/addMemberForm"

    @PostMapping("/add")
    fun save(
        @Validated
        @ModelAttribute("member")
        form: MemberSaveForm,
        bindingResult: BindingResult,
    ): String {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm"
        }

        val member = with(form) {
           Member(
               id = idGenerator.next(),
               loginId = loginId,
               name = name,
               password = password,
           )
        }

        memberRepository.save(member)
        return "redirect:/"
    }
}
