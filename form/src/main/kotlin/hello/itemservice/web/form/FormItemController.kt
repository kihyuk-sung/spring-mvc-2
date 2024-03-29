package hello.itemservice.web.form

import hello.itemservice.domain.item.*
import hello.itemservice.web.form.dto.AddItemDto
import hello.itemservice.web.form.dto.ItemDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/form/items")
class FormItemController(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ModelAttribute("regions")
    fun regions(): Map<String, String> = mapOf(
        "SEOUL" to "서울",
        "BUSAN" to "부산",
        "JEJU" to "제주",
    )

    @ModelAttribute("itemTypes")
    fun itemTypes(): Array<ItemType> = ItemType.values()

    @ModelAttribute("deliveryCodes")
    fun deliveryCodes(): List<DeliveryCode> = listOf(
        DeliveryCode("FAST", "빠른 배송"),
        DeliveryCode("NORMAL", "일반 배송"),
        DeliveryCode("SLOW", "느린 배송"),
    )

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
    fun addForm(model: Model): String = "form/addForm"
        .apply {
            model.addAttribute("item", AddItemDto())
        }

    @PostMapping("/add")
    fun addItem(@ModelAttribute item: ItemDto, redirectAttributes: RedirectAttributes): String = item
        .toItem(idGenerator.next())
        .apply { log.info("item.open={}", item.open) }
        .apply { log.info("item.regions={}", item.regions) }
        .apply { log.info("item.itemType={}", item.itemType) }
        .let(itemRepository::save)
        .let {
            redirectAttributes.addAttribute("itemId", it.id)
            redirectAttributes.addAttribute("status", true)
        }
        .let { "redirect:/form/items/{itemId}" }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String = "form/editForm"
        .apply {
            itemRepository
                .findById(itemId)
                .let { model.addAttribute("item", it) }
        }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: ItemDto): String = item
        .toItem(itemId)
        .let(itemRepository::save)
        .let { "redirect:/form/items/{itemId}" }
}
