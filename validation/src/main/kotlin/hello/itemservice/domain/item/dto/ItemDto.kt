package hello.itemservice.domain.item.dto

import hello.itemservice.domain.item.Item

class ItemDto {
    var id: Long? = null
    var itemName: String? = null
    var price: Int? = null
    var quantity: Int? = null
    fun toItem(id: Long): Item =
        Item(
            id = id,
            itemName = itemName?: throw IllegalStateException("must not be null"),
            price = price ?: throw IllegalStateException("must not be null"),
            quantity = quantity ?: throw IllegalStateException("must not be null"),
        )
}
