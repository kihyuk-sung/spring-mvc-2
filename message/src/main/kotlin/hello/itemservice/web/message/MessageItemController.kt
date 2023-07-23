package hello.itemservice.web.message

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import hello.itemservice.domain.item.dto.ItemDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/message/items")
class MessageItemController(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    @GetMapping
    fun items(model: Model): String {
        val items: List<Item> = itemRepository.findAll()
        model.addAttribute("items", items)
        return "message/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item: Item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "message/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", ItemDto())
        return "message/addForm"
    }

    @PostMapping("/add")
    fun addItem(@ModelAttribute item: ItemDto, redirectAttributes: RedirectAttributes): String {
        val savedItem: Item = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/message/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item: Item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "message/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: ItemDto): String {
        itemRepository.save(item.toItem(itemId))
        return "redirect:/message/items/{itemId}"
    }
}
