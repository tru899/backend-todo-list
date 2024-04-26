package ru.romanow.todolist.login

import com.codeborne.selenide.Selenide.`$`
import org.openqa.selenium.By.cssSelector
import ru.romanow.todolist.LOGIN_FORM_MODAL_NAME

class LoginPageResults {
    fun loginModal() = `$`(cssSelector("[data-id='$LOGIN_FORM_MODAL_NAME']"))
}
