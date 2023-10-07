package ru.netology.testmode.data.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.data.DataGenerator.getRandomPassword;

public class ClientTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("successful login of the registered user")
    void successfulLoginOfTheRegisteredUserTest() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue((registeredUser.getLogin()));
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("unregistered user")
    void unregisteredUserTest() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue((notRegisteredUser.getLogin()));
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("blocked user")
    void blockedUserTest() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue((blockedUser.getLogin()));
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("incorrect login")
    void incorrectLoginTest() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("incorrect password")
    void incorrectPasswordTest() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue((registeredUser.getLogin()));
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
