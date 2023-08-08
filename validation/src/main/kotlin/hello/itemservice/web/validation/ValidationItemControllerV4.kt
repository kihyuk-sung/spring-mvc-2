package hello.itemservice.web.validation

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import hello.itemservice.domain.item.dto.ItemDto
import hello.itemservice.web.validation.form.ItemSaveForm
import hello.itemservice.web.validation.form.ItemUpdateForm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v4/items")
class ValidationItemControllerV4(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "validation/v4/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v4/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", ItemDto())
        return "validation/v4/addForm"
    }

//    @PostMapping("/add")
    fun addItem(
        @Validated @ModelAttribute("item") item: ItemDto,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {
        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price!! * item.quantity!!
            if (resultPrice < 10_000) {
                bindingResult.addError(
                    ObjectError(
                        "item",
                        arrayOf("totalPriceMin"),
                        arrayOf(10_000, resultPrice),
                        null
                    )
                )
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult)
            return "validation/v4/addForm"
        }

        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v4/items/{itemId}"
    }

    @PostMapping("/add")
    fun addItemV2(
        @Validated @ModelAttribute("item") form: ItemSaveForm,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {
        if (form.price != null && form.quantity != null) {
            val resultPrice = form.price * form.quantity
            if (resultPrice < 10_000) {
                bindingResult.addError(
                    ObjectError(
                        "item",
                        arrayOf("totalPriceMin"),
                        arrayOf(10_000, resultPrice),
                        null
                    )
                )
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult)
            return "validation/v4/addForm"
        }

        val item = with(form) {
            Item(
                id = idGenerator.next(),
                itemName = itemName,
                price = price,
                quantity = quantity,
            )
        }

        val (id) = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v4/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v4/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(
        @PathVariable itemId: Long,
        @Validated @ModelAttribute("item") form: ItemUpdateForm,
        bindingResult: BindingResult,
    ): String {
        if (form.price != null && form.quantity != null) {
            val resultPrice = form.price * form.quantity
            if (resultPrice < 10_000) {
                bindingResult.addError(
                    ObjectError(
                        "item",
                        arrayOf("totalPriceMin"),
                        arrayOf(10_000, resultPrice),
                        null
                    )
                )
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult)
            return "validation/v4/editForm"
        }

        val item = with(form) {
            Item(
                id = id,
                itemName = itemName,
                price = price,
                quantity = quantity
            )
        }

        itemRepository.save(item)
        return "redirect:/validation/v4/items/{itemId}"
    }
}
