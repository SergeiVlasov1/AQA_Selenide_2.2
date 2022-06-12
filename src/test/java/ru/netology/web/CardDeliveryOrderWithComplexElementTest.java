package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderWithComplexElementTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldChooseCityFromDropDownListAndDateInCalendarOneWeekAhead() {
        $("[data-test-id='city'] input").setValue("мо");
        $(".input__popup").shouldBe(Condition.visible);
        $(byText("Москва")).click();
        $("[data-test-id='city'] input").shouldHave(Condition.exactValue("Москва"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[type='button']").click();
        $(".calendar-input__calendar-wrapper").shouldBe(Condition.visible);
        if ((LocalDate.now().plusDays(7)).getMonthValue() != (LocalDate.now()).getMonthValue()) {
        }
        $(byText(String.valueOf(LocalDate.now().plusDays(7).getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue("Громов Иван");
        $("[data-test-id='phone'] input").setValue("+79371745355");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text(formatter.format(LocalDate.now().plusDays(7))))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldChooseCityFromDropDownListAndDateInCalendarOneMonthAhead() {
        $("[data-test-id='city'] input").setValue("во");
        $(".input__popup").shouldBe(Condition.visible);
        $(byText("Воронеж")).click();
        $("[data-test-id='city'] input").shouldHave(Condition.exactValue("Воронеж"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[type='button']").click();
        $(".calendar-input__calendar-wrapper").shouldBe(Condition.visible);
        if ((LocalDate.now().plusDays(31)).getMonthValue() != (LocalDate.now()).getMonthValue()) {
            $("[role=button][data-step='1']").click();
        }
        $(byText(String.valueOf(LocalDate.now().plusDays(31).getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue("Громов Иван");
        $("[data-test-id='phone'] input").setValue("+79371745355");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text(formatter.format(LocalDate.now().plusDays(31))))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
