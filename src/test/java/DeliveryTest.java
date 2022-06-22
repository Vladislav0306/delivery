import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class DeliveryTest {
    @Test
    void shouldTestAllFieldsCorrectly() {
        open("http://localhost:9999");
        int day = 3;
        String date = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Ежков Владислав");
        $("[data-test-id=phone] input").setValue("+79183477293");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='notification']>.notification__content").shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + date));
    }
}
