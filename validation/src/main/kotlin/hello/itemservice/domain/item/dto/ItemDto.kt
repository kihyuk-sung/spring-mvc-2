package hello.itemservice.domain.item.dto

import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.SaveCheck
import hello.itemservice.domain.item.UpdateCheck
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

class ItemDto {

    @NotNull(groups = [UpdateCheck::class])
    var id: Long? = null

    @NotBlank(groups = [SaveCheck::class, UpdateCheck::class])
    var itemName: String? = null

    @NotNull(groups = [SaveCheck::class, UpdateCheck::class])
    @Range(min = 1_000, max = 1_000_000, groups = [SaveCheck::class, UpdateCheck::class])
    var price: Int? = null

    @NotNull(groups = [SaveCheck::class, UpdateCheck::class])
    @Max(value = 9_999, groups = [SaveCheck::class])
    var quantity: Int? = null
    fun toItem(id: Long): Item =
        Item(
            id = id,
            itemName = itemName?: throw IllegalStateException("must not be null"),
            price = price ?: throw IllegalStateException("must not be null"),
            quantity = quantity ?: throw IllegalStateException("must not be null"),
        )
}
