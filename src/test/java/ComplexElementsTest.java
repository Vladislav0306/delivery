import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class ComplexElementsTest {

    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    String date;
    String month;
    String day;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 100);
        date =  new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
        month = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
        day = Integer.toString(calendar.get(Calendar.DATE));
        open("http://localhost:9999");
    }

    @Test
    void shouldTestAllFieldsCorrectly() {
        $("[data-test-id=city] input").setValue("Кр");
        $$("div.popup__content div").find(exactText("Краснодар")).click();
        $("span[data-test-id='date'] button").click();

        while (!$("div.calendar__name").getText().equals(month)) {
            $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
        }

        $$("table.calendar__layout td").find(text(String.valueOf(day))).click();
        $("[data-test-id=name] input").setValue("Ежков Владислав");
        $("[data-test-id=phone] input").setValue("+79183477293");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='notification']>.notification__content").shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + date));
    }
}