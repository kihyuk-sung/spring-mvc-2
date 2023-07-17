package hello.itemservice.domain.item

data class Item(
    val id: Long,
    val itemName: String,
    val price: Int,
    val quantity: Int,
)
