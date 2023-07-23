package hello.itemservice.domain.item.dto

import hello.itemservice.domain.item.Item

data class ItemDto(
    val itemName: String? = null,
    val price: Int? = null,
    val quantity: Int? = null,
) {
    fun toItem(id: Long): Item = Item(
        id = id,
        itemName = itemName ?: "",
        price = price ?: 0,
        quantity = quantity ?: 0,
    )
}
