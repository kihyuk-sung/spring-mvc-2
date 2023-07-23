package hello.itemservice.web.form.dto

import hello.itemservice.domain.item.ItemType

data class ItemDto (
    val itemName: String,
    val price: Int,
    val quantity: Int,
    val open: Boolean,
    val regions: List<String> = listOf(),
    val itemType: ItemType = ItemType.BOOK,
    val deliveryCode: String,
)
