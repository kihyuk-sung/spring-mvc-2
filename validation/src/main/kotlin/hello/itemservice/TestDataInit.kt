package hello.itemservice

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class TestDataInit(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {

    @PostConstruct
    fun init(): Unit = listOf(
        Item(idGenerator.next(), "itemA", 10_000, 10),
        Item(idGenerator.next(), "itemB", 20_000, 20),
    )
        .forEach(itemRepository::save)

}
