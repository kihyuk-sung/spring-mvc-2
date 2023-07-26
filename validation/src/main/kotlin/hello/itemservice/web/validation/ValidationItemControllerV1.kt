package hello.itemservice.web.validation

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.dto.ItemDto
import hello.itemservice.domain.item.ItemRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v1/items")
class ValidationItemControllerV1(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "validation/v1/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v1/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", ItemDto())
        return "validation/v1/addForm"
    }

    @PostMapping("/add")
    fun addItem(@ModelAttribute item: ItemDto, redirectAttributes: RedirectAttributes): String {
        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v1/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v1/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: ItemDto): String {
        itemRepository.save(item.toItem(itemId))
        return "redirect:/validation/v1/items/{itemId}"
    }
}
