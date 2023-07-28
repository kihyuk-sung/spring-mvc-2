package hello.itemservice.web.validation

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.dto.ItemDto
import hello.itemservice.domain.item.ItemRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v2/items")
class ValidationItemControllerV2(
    private val itemRepository: ItemRepository,
    private val idGenerator: IdGenerator,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "validation/v2/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v2/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", ItemDto())
        return "validation/v2/addForm"
    }

    @PostMapping("/add")
    fun addItem(@ModelAttribute("item") item: ItemDto, redirectAttributes: RedirectAttributes, model: Model): String {
        val errors = buildMap {
            if (!StringUtils.hasText(item.itemName)) {
                put("itemName", "상품 이름은 필수입니다.")
            }
            if (item.price == null || item.price < 1000 || item.price > 1_000_000) {
                put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.")
            }
            if (item.quantity == null || item.quantity < 1 || item.quantity >= 9_999) {
                put("quantity", "수량은 최대 9,999 까지 허용합니다.")
            }

            if (item.price != null && item.quantity != null) {
                val resultPrice = item.price * item.quantity
                if (resultPrice < 10_000) {
                    put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = $resultPrice")
                }
            }
        }

        if (errors.isNotEmpty()) {
            log.info("errors = {}", errors)
            model.addAttribute("errors", errors)
            return "validation/v2/addForm"
        }

        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v2/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId) ?: throw IllegalArgumentException("not found item")
        model.addAttribute("item", item)
        return "validation/v2/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: ItemDto): String {
        itemRepository.save(item.toItem(itemId))
        return "redirect:/validation/v2/items/{itemId}"
    }
}
