package hello.itemservice

import hello.itemservice.domain.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import hello.itemservice.domain.member.Member
import hello.itemservice.domain.member.MemberRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class TestDataInit(
    @Qualifier("itemIdGenerator")
    private val idGenerator: IdGenerator,
    private val itemRepository: ItemRepository,
    @Qualifier("memberIdGenerator")
    private val memberIdGenerator: IdGenerator,
    private val memberRepository: MemberRepository,
) {

    @PostConstruct
    fun init(): Unit = listOf(
        Item(idGenerator.next(), "itemA", 10_000, 10),
        Item(idGenerator.next(), "itemB", 20_000, 20),
    )
        .forEach(itemRepository::save)

    @PostConstruct
    fun memberInit(): Unit = listOf(
        Member(memberIdGenerator.next(), "test", "테스터", "test!")
    )
        .forEach(memberRepository::save)

}
