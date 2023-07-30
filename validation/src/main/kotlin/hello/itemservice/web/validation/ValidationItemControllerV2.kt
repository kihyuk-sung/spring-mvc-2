package hello.itemservice.web.validation

import hello.itemservice.domain.item.IdGenerator
import hello.itemservice.domain.item.dto.ItemDto
import hello.itemservice.domain.item.ItemRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
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

//    @PostMapping("/add")
    fun addItemV1(@ModelAttribute("item") item: ItemDto, bindingResult: BindingResult, redirectAttributes: RedirectAttributes, model: Model): String {
        if (!StringUtils.hasText(item.itemName)) {
            bindingResult.addError(FieldError("item", "itemName", "상품 이름은 필수입니다."))
        }
        if (item.price == null || item.price < 1000 || item.price > 1_000_000) {
            bindingResult.addError(FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."))
        }
        if (item.quantity == null || item.quantity < 1 || item.quantity >= 9_999) {
            bindingResult.addError(FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."))
        }

        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price * item.quantity
            if (resultPrice < 10_000) {
                bindingResult.addError(ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = $resultPrice"))
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult)
            return "validation/v2/addForm"
        }

        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v2/items/{itemId}"
    }

//    @PostMapping("/add")
    fun addItemV2(@ModelAttribute("item") item: ItemDto, bindingResult: BindingResult, redirectAttributes: RedirectAttributes, model: Model): String {
        if (!StringUtils.hasText(item.itemName)) {
            bindingResult.addError(FieldError("item", "itemName", item.itemName, false, null, null, "상품 이름은 필수입니다."))
        }
        if (item.price == null || item.price < 1000 || item.price > 1_000_000) {
            bindingResult.addError(FieldError("item", "price", item.price, false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."))
        }
        if (item.quantity == null || item.quantity < 1 || item.quantity >= 9_999) {
            bindingResult.addError(FieldError("item", "quantity", item.quantity, false, null, null, "수량은 최대 9,999 까지 허용합니다."))
        }

        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price * item.quantity
            if (resultPrice < 10_000) {
                bindingResult.addError(ObjectError("item", null, null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = $resultPrice"))
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult)
            return "validation/v2/addForm"
        }

        val (id) = itemRepository.save(item.toItem(idGenerator.next()))
        redirectAttributes.addAttribute("itemId", id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v2/items/{itemId}"
    }

    @PostMapping("/add")
    fun addItemV3(@ModelAttribute("item") item: ItemDto, bindingResult: BindingResult, redirectAttributes: RedirectAttributes, model: Model): String {
        if (!StringUtils.hasText(item.itemName)) {
            bindingResult.addError(FieldError("item", "itemName", item.itemName, false, arrayOf("required.item.itemName"), null, null))
        }
        if (item.price == null || item.price < 1000 || item.price > 1_000_000) {
            bindingResult.addError(FieldError("item", "price", item.price, false, arrayOf("range.item.price"), arrayOf(1_000, 1_000_000), null))
        }
        if (item.quantity == null || item.quantity < 1 || item.quantity >= 9_999) {
            bindingResult.addError(FieldError("item", "quantity", item.quantity, false, arrayOf("max.item.quantity"), arrayOf(9_999), null))
        }

        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price * item.quantity
            if (resultPrice < 10_000) {
                bindingResult.addError(ObjectError("item", arrayOf("totalPriceMin"), arrayOf(10_000, resultPrice),null))
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult)
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
