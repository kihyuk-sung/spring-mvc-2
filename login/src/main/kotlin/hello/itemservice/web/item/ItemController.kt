package hello.itemservice.web.item

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import hello.itemservice.web.item.form.ItemSaveForm
import hello.itemservice.web.item.form.ItemUpdateForm
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/items")
class ItemController(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun items(model: Model): String = "items/items"
        .also { model.addAttribute("items", itemRepository.findAll()) }

    @GetMapping("/{itemId}")
    fun item(
        @PathVariable itemId: Long,
        model: Model,
    ): String = "items/item"
        .also {
            model.addAttribute("item", itemRepository.findById(itemId))
        }

    @GetMapping("/add")
    fun addForm(model: Model): String = "items/addForm"
        .also { model.addAttribute("item", ItemSaveForm()) }

    @PostMapping("/add")
    fun addItem(
        @Validated @ModelAttribute("item") form: ItemSaveForm,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (form.price != null && form.quantity != null) {
            val resultPrice = form.price * form.quantity
            if (resultPrice < 10_000) {
                bindingResult.reject("totalPriceMin", arrayOf(10_000, resultPrice), null)
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult)
            return "items/addForm"
        }

        val item = with(form) {
            Item(
                id = idGenerator.next(),
                itemName = itemName,
                price = price,
                quantity = quantity,
            )
        }

        with(itemRepository.save(item)) {
            redirectAttributes
                .addAttribute("itemId", id)
                .addAttribute("statue", true)
        }

        return "redirect:/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(
        @PathVariable itemId: Long,
        model: Model,
    ): String = "items/editForm"
        .also { model.addAttribute("item", itemRepository.findById(itemId)) }

    @PostMapping("/{itemId}/edit")
    fun editItem(
        @PathVariable itemId: Long,
        @Validated @ModelAttribute("item") form: ItemUpdateForm,
        bindingResult: BindingResult,
    ): String {
        if (form.price != null && form.quantity != null) {
            val resultPrice = form.price * form.quantity
            if (resultPrice < 10_000) {
                bindingResult.reject("totalPriceMin", arrayOf(10_000, resultPrice), null)
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult)
            return "items/editForm"
        }

        val item = with(form) {
            Item(
                id = id,
                itemName = itemName,
                price = price,
                quantity = quantity,
            )
        }

        itemRepository.save(item)
        return "redirect:/items/{itemId}"
    }
}
