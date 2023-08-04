package hello.itemservice.domain.item.dto

import hello.itemservice.domain.item.Item
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

class ItemDto {
    var id: Long? = null

    @NotBlank
    var itemName: String? = null

    @NotNull
    @Range(min = 1_000, max = 1_000_000)
    var price: Int? = null

    @NotNull
    @Max(9_999)
    var quantity: Int? = null
    fun toItem(id: Long): Item =
        Item(
            id = id,
            itemName = itemName?: throw IllegalStateException("must not be null"),
            price = price ?: throw IllegalStateException("must not be null"),
            quantity = quantity ?: throw IllegalStateException("must not be null"),
        )
}
