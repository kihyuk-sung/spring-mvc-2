package hello.itemservice.domain.item

enum class ItemType(
    private val description: String,
) {
    BOOK("도서"),
    FOOD("음식"),
    ETC("기타"),
}
