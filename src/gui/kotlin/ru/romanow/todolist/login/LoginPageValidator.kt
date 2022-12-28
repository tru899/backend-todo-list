package ru.romanow.todolist.login

import com.codeborne.selenide.Selenide.`$`
import org.openqa.selenium.By
import ru.romanow.todolist.LOGIN_FORM_MODAL_NAME

class LoginPageValidator {
    fun validate() {
        `$`(By.cssSelector("[data-id='$LOGIN_FORM_MODAL_NAME']")).exists()
    }
}