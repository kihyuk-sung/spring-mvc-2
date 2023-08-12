package hello.itemservice.domain.member

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class MemberRepository {
    private val log = LoggerFactory.getLogger(javaClass)
    private val store: MutableMap<Long, Member> = HashMap()

    fun save(member: Member): Member = member
        .also { store[it.id] = it }
        .also { log.info("save: member={}", it) }

    fun findById(id: Long): Member? = store[id]

    fun findByLoginId(loginId: String): Member? = store.values.find { it.loginId == loginId }

    fun findAll(): List<Member> = store.values.toList()

    fun clear(): Unit = store.clear()
}
