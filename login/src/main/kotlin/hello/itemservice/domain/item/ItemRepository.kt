package hello.itemservice.domain.item

import org.springframework.stereotype.Repository

@Repository
class ItemRepository {
    private val store: MutableMap<Long, Item> = HashMap()

    fun save(item: Item): Item = item
        .also { store[it.id] = it }

    fun findById(id: Long): Item? = store[id]

    fun findAll(): List<Item> = store.values.toList()

    fun clear(): Unit = store.clear()
}
