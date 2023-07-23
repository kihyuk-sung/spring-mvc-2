package hello.itemservice.domain.item

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class ItemRepositoryTest: FunSpec({
    val itemRepository = ItemRepository()

    afterEach {
        itemRepository.clear()
    }

    test("save") {
        val item = Item(
            id = 1L,
            itemName = "itemA",
            price = 10_000,
            quantity = 10,
        )

        itemRepository.save(item)

        val savedItem = itemRepository.findById(1L)

        require(savedItem != null)

        with(savedItem) {
            id shouldBe 1L
            itemName shouldBe "itemA"
            price shouldBe 10_000
            quantity shouldBe 10
        }
    }

    test("findAll") {
        val item1 = Item(
            id = 1L,
            itemName = "item1",
            price = 10_000,
            quantity = 10,
        )

        val item2 = Item(
            id = 2L,
            itemName = "item2",
            price = 20_000,
            quantity = 20,
        )

        itemRepository.save(item1)
        itemRepository.save(item2)

        val result = itemRepository.findAll()

        result shouldHaveSize 2
        result shouldContainExactly listOf(item1, item2)
    }
})
