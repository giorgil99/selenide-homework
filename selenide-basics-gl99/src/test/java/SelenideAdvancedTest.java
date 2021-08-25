import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Configuration.reportsFolder;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Test
public class SelenideAdvancedTest {

    public void advance() {
        // Create folder in your src/main/resources directory named 'Reports'
        // Configure your test to save in case of failure only screenshots (without pagesource)
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.holdBrowserOpen = true;
        Path root = Paths.get(".").normalize().toAbsolutePath();
        Configuration.reportsFolder = root + "\\src\\main\\resources\\Reports";
        Configuration.savePageSource = false;

        open("https://demoqa.com/books");

//    Using find() and findAll() methods Find all books with publisher 'O'Reilly Media' containing title 'Javascript'
//    Check with Test NG soft assertions that size of returned list equals to 10(failed case)
        long expectedSize = 10;
        long bookListSize = $("div.rt-table").find("div.rt-tbody").findAll("div.rt-tr-group").stream().
                filter(el -> el.getText().contains("O'Reilly Media")).filter(el -> el.getText().contains("JavaScript")).count();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(bookListSize, expectedSize);

//    Check that first match row's title equals to 'Learning JavaScript Design Patterns'(success case)
        String firstMatch = $("div.rt-table").find("div.rt-tbody").findAll("div.rt-tr-group").stream().
                filter(el -> el.getText().contains("O'Reilly Media")).filter(el -> el.getText().contains("JavaScript")).
                findFirst().get().find("div.action-buttons").getText();

        String expectedText = "Learning JavaScript Design Patterns";
        softAssert.assertEquals(firstMatch, expectedText);

//        Using stream() click on all matching row's title

        for (int i = 0; i < bookListSize; i++) {
            Stream<SelenideElement> stream = $("div.rt-table").find("div.rt-tbody").findAll("div.rt-tr-group").stream().
                    filter(el -> el.getText().contains("O'Reilly Media")).filter(el -> el.getText().contains("JavaScript"));
            List<SelenideElement> list = stream.collect(Collectors.toList());
            list.get(i).scrollIntoView(true);
            list.get(i).find("div.action-buttons").click();
            System.out.println(i);
            $("#addNewRecordButton").scrollIntoView(true);
            $("#addNewRecordButton").click();
//            switchTo().window(1);


        }
        // Check with assertion that configuration works properly

        softAssert.assertTrue(!Configuration.savePageSource);
        softAssert.assertTrue(reportsFolder.equalsIgnoreCase(root + "\\src\\main\\resources\\Reports"));

        softAssert.assertAll();


    }

    // these lines cause NoSuchElementException to get screenshot of error that is saved in ./resources/Reports folder
    public void errorScreen() {
        $("#addNewRecordButton").click();
        System.out.println();
    }
}


