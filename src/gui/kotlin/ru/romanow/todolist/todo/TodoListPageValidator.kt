package ru.romanow.todolist.todo

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selectors.byCssSelector
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.`$`
import org.openqa.selenium.By.cssSelector
import ru.romanow.todolist.LOADER
import ru.romanow.todolist.TODO_ITEMS_ITEM
import ru.romanow.todolist.TODO_ITEMS_ROW


class TodoListPageValidator {
    fun validate(count: Int = -1, item: Item? = null, state: State = State.EXISTS): Int {
        `$`(byCssSelector("[data-id='$LOADER']")).shouldBe(hidden)

        val items = `$$`(cssSelector("[data-id*='$TODO_ITEMS_ROW']"))
        if (count > 0) {
            items.shouldHave(size(count))
        }
        if (item != null) {
            val element = `$`(cssSelector("[data-id='$TODO_ITEMS_ITEM-${item.uid}']"))
            if (state == State.EXISTS) {
                element.shouldHave(text(item.text))
            } else {
                element.shouldNot(exist)
            }
        }
        return items.size
    }
}

enum class State { EXISTS, ABSENT }
