package hello.itemservice.web.form.dto

class AddItemDto(
    val itemName: String? = null,
    val price: Int? = null,
    val quantity: Int? = null,
    val open: Boolean = false,
    val regions: List<String> = listOf()
)
