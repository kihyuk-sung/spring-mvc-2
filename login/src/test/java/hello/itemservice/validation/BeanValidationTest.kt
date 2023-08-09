package hello.itemservice.validation

import hello.itemservice.web.item.form.ItemSaveForm
import io.kotest.core.spec.style.FunSpec
import jakarta.validation.Validation

class BeanValidationTest: FunSpec({
    test("bean validation") {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator

        val item = ItemSaveForm().apply {
            itemName = " "
            price = 0
            quantity = 10_000
        }

        val violations = validator.validate(item)

        violations.forEach {
            println("violation = $it")
            println("violation = ${it.message}")
        }
    }
})
