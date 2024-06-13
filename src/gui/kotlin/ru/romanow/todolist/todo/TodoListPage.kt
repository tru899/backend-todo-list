package ru.romanow.todolist.todo

import com.codeborne.selenide.Condition.hidden
import com.codeborne.selenide.Selectors.byCssSelector
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.`$`
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.openqa.selenium.By.cssSelector
import ru.romanow.todolist.utils.ITEM_REMOVE_BUTTON
import ru.romanow.todolist.utils.LOADER
import ru.romanow.todolist.utils.NEW_ITEM_BUTTON
import ru.romanow.todolist.utils.NEW_ITEM_INPUT
import ru.romanow.todolist.utils.TODO_ITEMS_ITEM
import java.util.*

class TodoListPage {
    fun addNewItem(): Item {
        val text = randomAlphabetic(8)
        `$`(cssSelector("[data-id='$NEW_ITEM_INPUT']")).sendKeys(text)
        `$`(cssSelector("[data-id='$NEW_ITEM_BUTTON']")).click()

        `$`(byCssSelector("[data-id='$LOADER']")).shouldBe(hidden)

        val uid = `$`(byText(text)).attr("data-id")
            ?.substring("$TODO_ITEMS_ITEM-".length)

        return Item(UUID.fromString(uid), text)
    }

    fun removeItem(item: Item) {
        `$`(cssSelector("[data-id='$ITEM_REMOVE_BUTTON-${item.uid}'")).click()
    }
}

data class Item(
    val uid: UUID,
    val text: String,
)
