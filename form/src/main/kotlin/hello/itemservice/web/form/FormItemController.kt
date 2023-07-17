package hello.itemservice.web.form

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import hello.itemservice.web.form.dto.ItemDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/form/items")
class FormItemController(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
){
    @GetMapping
    fun items(model: Model): String = itemRepository
        .findAll()
        .let { model.addAttribute("items", it) }
        .let { "form/items" }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)

        return "form/item"
    }

    @GetMapping("/add")
    fun addForm(): String  = "form/addForm"

    @PostMapping("/add")
    fun addItem(@ModelAttribute item: ItemDto, redirectAttributes: RedirectAttributes): String =
        Item(
            id = idGenerator.next(),
            itemName = item.itemName,
            price = item.price,
            quantity = item.quantity,
        )
            .let(itemRepository::save)
            .let {
                redirectAttributes.addAttribute("itemId", it.id)
                redirectAttributes.addAttribute("status", true)
            }
            .let { "redirect:/form/items/{itemId}" }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String = itemRepository
        .findById(itemId)
        .let { model.addAttribute("item", it) }
        .let { "form/editForm" }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: ItemDto): String = Item(
        id = itemId,
        itemName = item.itemName,
        price = item.price,
        quantity = item.quantity,
    )
        .let(itemRepository::save)
        .let { "redirect:/form/items/{itemId}" }
}
