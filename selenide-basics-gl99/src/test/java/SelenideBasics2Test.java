import com.codeborne.selenide.Configuration;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;


@Test
public class SelenideBasics2Test {

    public void books() {
        Configuration.browser = "chrome";
        open("https://demoqa.com/books");
//        Using inner elements locators chain Find all books with publisher 'O'Reilly Media' containing title 'Javascript'
//        Check with selenide soft assertions that size of returned list equals to 10(failed case)
        long p = 10;
        long k = $("div.rt-tbody").findAll("div.rt-tr-group").stream().filter(el -> el.getText().contains("O'Reilly Media")).filter(el -> el.getText().contains("JavaScript")).count();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(k, p);


//        Using inner elements locators chain Find that each books images are not empty(success case)

        System.out.println($$("div>img").size()); //simple way
        System.out.println($("div.rt-tbody").findAll("img").size()); // Using inner elements locators chain
        System.out.println($("div").findAll("img").size()); // this line does not work as it returns 1! why ?

        softAssert.assertNotNull($("div.rt-tbody").findAll("img")); // asserts that element is not null

        // asserting with double negative (false that isempty) for each element
        for (int i = 0; i < $("div.rt-tbody").findAll("img").size(); i++) {

            softAssert.assertFalse($("div.rt-tbody").findAll("img").get(i).getAttribute("alt").isEmpty());
            // visualisation for run panel
            if (Objects.requireNonNull($("div.rt-tbody").findAll("img").get(i).getAttribute("alt")).isEmpty()) {
                System.out.println("not all books have image");

            } else {
                System.out.println("forTest N:" + (i + 1) + " > image for book " + "'" + $$("div.action-buttons").get(i).getText() + "'" + " is present √");


            }
        }
        softAssert.assertAll();

    }
}
