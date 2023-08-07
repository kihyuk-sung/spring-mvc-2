package hello.itemservice.web.validation

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.ItemRepository
import hello.itemservice.domain.item.SaveCheck
import hello.itemservice.domain.item.UpdateCheck
import hello.itemservice.domain.item.dto.ItemDto
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
@RequestMapping("/validation/v3/items")
class ValidationItemControllerV3(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "validation/v3/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v3/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", ItemDto())
        return "validation/v3/addForm"
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
            return "validation/v3/addForm"
        }

        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }

    @PostMapping("/add")
    fun addItemV2(
        @Validated(SaveCheck::class) @ModelAttribute("item") item: ItemDto,
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
            return "validation/v3/addForm"
        }

        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v3/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(
        @PathVariable itemId: Long,
        @Validated(UpdateCheck::class) @ModelAttribute("item") item: ItemDto,
        bindingResult: BindingResult,
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
            return "validation/v3/editForm"
        }

        itemRepository.save(item.toItem(itemId))
        return "redirect:/validation/v3/items/{itemId}"
    }
}
