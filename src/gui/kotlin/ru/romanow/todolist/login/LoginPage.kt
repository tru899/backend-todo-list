package ru.romanow.todolist.login

import com.codeborne.selenide.Selectors.byId
import com.codeborne.selenide.Selenide.`$`
import org.openqa.selenium.By.cssSelector
import ru.romanow.todolist.LOGIN_FORM_OAUTH0_BUTTON

class LoginPage {
    fun authorize() {
        `$`(cssSelector("[data-id='$LOGIN_FORM_OAUTH0_BUTTON']")).click()
        `$`(byId("username")).sendKeys(USERNAME)
        `$`(byId("password")).sendKeys(PASSWORD)
        `$`(cssSelector("[type=submit]")).click()
    }

    companion object {
        private const val USERNAME = "ronin@romanow-alex.ru"
        private const val PASSWORD = "Qwerty123"
    }
}