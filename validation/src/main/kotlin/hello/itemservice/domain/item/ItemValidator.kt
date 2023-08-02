package hello.itemservice.domain.item

import hello.itemservice.domain.item.dto.ItemDto
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class ItemValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean =
        ItemDto::class.java.isAssignableFrom(clazz)


    override fun validate(target: Any, errors: Errors) {
        val item = target as ItemDto
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required")

        if (item.price == null || item.price!! < 1000 || item.price!! > 1_000_000) {
            errors.rejectValue("price", "range", arrayOf(1_000, 1_000_000), null)
        }
        if (item.quantity == null || item.quantity!! < 1 || item.quantity!! >= 9_999) {
            errors.rejectValue("quantity", "max", arrayOf(9_999), null)
        }

        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price!! * item.quantity!!
            if (resultPrice < 10_000) {
                errors.reject("totalPriceMin", arrayOf(10_000, resultPrice), null)
            }
        }
    }
}
