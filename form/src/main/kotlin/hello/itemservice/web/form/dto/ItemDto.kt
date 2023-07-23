package hello.itemservice.web.form.dto

import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemType

data class ItemDto (
    val itemName: String,
    val price: Int,
    val quantity: Int,
    val open: Boolean,
    val regions: List<String> = listOf(),
    val itemType: ItemType = ItemType.BOOK,
    val deliveryCode: String,
) {

    fun toItem(id: Long): Item = Item(
        id = id,
        itemName = itemName,
        price = price,
        quantity = quantity,
        open = open,
        regions = regions,
        itemType = itemType,
        deliveryCode = deliveryCode,
    )

}
