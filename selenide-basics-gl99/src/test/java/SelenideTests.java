import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;


@Test
public class SelenideTests {

    public void checks() {
        // Configuration.browser = "chrome" ;
        open("http://the-internet.herokuapp.com/checkboxes");
        //  Set first checkbox selected
        $("form").$("input", 0).click();
        // Validate that both checkboxes has type 'checkbox'
        $("form").$("input", 0).shouldHave(Condition.type("checkbox"));
        $("form").$("input", 1).shouldHave(Condition.type("checkbox"));


        System.out.println("Test1 √");
    }

    public void dropdown() {
        open("http://the-internet.herokuapp.com/dropdown");
        // Validate that 'Please select an option' is selected
        System.out.println($(By.id("dropdown")).getText());
        $(By.id("dropdown")).shouldHave(Condition.text("Please select an option"));
        //$(By.id("dropdown")).shouldHave(Condition.value("Please select an option")); //this does not work because value is not set in html
        //  Select 'Option 2'
//        $(By.id("dropdown")).click();
//        $(By.id("dropdown")).$("option", 2).click();
        $(By.id("dropdown")).selectOption("Option 2");
        // Validate that 'Option 2' was selected
        System.out.println($(By.id("dropdown")).getText());
        $(By.id("dropdown")).shouldHave(Condition.text("Option 2"));
        //$(By.id("dropdown")).shouldHave(Condition.exactValue("2")); // this work as value=2(or1) and not with text: Option 2

        System.out.println("Test2 √");
    }

    public void permananet() {
        open("https://demoqa.com/text-box");
        // Fill fullname, valid email,current and permanent addresses using different selectors
        $(By.id("userName")).sendKeys("user 1");
        $(By.id("userEmail")).sendKeys("user_1mail@gmail.com");
        $("textarea", 0).sendKeys("near user2");
        $(".form-control", 3).sendKeys("user1 home");
        $(By.id("submit")).scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        System.out.println($$("p").get(2).getText());
        // Validate the result with Collection Assertion
        $$("p").shouldHave(CollectionCondition.texts("user 1", "user_1mail@gmail.com", "near user2", "user1 home"));
        $$("p").shouldHave(CollectionCondition.exactTexts("Name:user 1", "Email:user_1mail@gmail.com", "Current Address :near user2", "Permananet Address :user1 home"));



        System.out.println("Test3 √");

    }


}
