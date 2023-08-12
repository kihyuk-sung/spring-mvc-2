package hello.itemservice.domain

import org.springframework.stereotype.Component

@Component
class IdGenerator {
    private var sequence: Long = 0L

    fun next(): Long = ++sequence
}
