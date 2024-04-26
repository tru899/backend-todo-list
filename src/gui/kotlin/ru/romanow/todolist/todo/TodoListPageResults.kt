package ru.romanow.todolist.todo

import com.codeborne.selenide.Selectors.byCssSelector
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import org.openqa.selenium.By.cssSelector
import ru.romanow.todolist.LOADER
import ru.romanow.todolist.TODO_ITEMS_ITEM
import ru.romanow.todolist.TODO_ITEMS_ROW
import java.util.*

class TodoListPageResults {
    fun loader() = `$`(byCssSelector("[data-id='$LOADER']"))

    fun item(uid: UUID) = `$`(cssSelector("[data-id='$TODO_ITEMS_ITEM-$uid']"))

    fun itemsCount() = `$$`(cssSelector("[data-id*='$TODO_ITEMS_ROW']"))
}
