package hello.itemservice.domain.item.dto

import hello.itemservice.domain.item.Item

data class ItemDto(
    val id: Long? = null,
    val itemName: String? = null,
    val price: Int? = null,
    val quantity: Int? = null,
) {
    fun toItem(id: Long): Item =
        Item(
            id = id,
            itemName = itemName?: throw IllegalStateException("must not be null"),
            price = price ?: throw IllegalStateException("must not be null"),
            quantity = quantity ?: throw IllegalStateException("must not be null"),
        )
}
