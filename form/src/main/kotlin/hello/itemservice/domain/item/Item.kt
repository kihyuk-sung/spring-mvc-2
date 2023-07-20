package hello.itemservice.domain.item

data class Item(
    val id: Long,
    val itemName: String,
    val price: Int,
    val quantity: Int,
    val open: Boolean,
) {
    val regions: List<String> = listOf()
    val itemType: ItemType = ItemType.BOOK
    val deliveryCode: DeliveryCode = DeliveryCode(code = "FAST", displayName = "빠른 배송")
}
