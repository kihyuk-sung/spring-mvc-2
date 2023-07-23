package hello.itemservice.domain.item

data class Item(
    val id: Long,
    val itemName: String,
    val price: Int,
    val quantity: Int,
    val open: Boolean,
    val regions: List<String>,
    val itemType: ItemType,
    val deliveryCode: String,
)
