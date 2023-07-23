package hello.itemservice

import hello.itemservice.domain.item.*
import jakarta.annotation.PostConstruct
import org.springframework.boot.web.server.PortInUseException
import org.springframework.stereotype.Component

@Component
class TestDataInit(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {

    @PostConstruct
    fun init() {
        itemRepository.save(
            Item(
                id = idGenerator.next(),
                itemName = "itemA",
                price = 10_000,
                quantity = 10,
                open = false,
                regions = listOf(),
                itemType = ItemType.BOOK,
                deliveryCode = "FAST",
            )
        )

        itemRepository.save(
            Item(
                id = idGenerator.next(),
                itemName = "itemB",
                price = 20_000,
                quantity = 20,
                open = false,
                regions = listOf(),
                itemType = ItemType.FOOD,
                deliveryCode = "NORMAL",
            )
        )
    }
}
