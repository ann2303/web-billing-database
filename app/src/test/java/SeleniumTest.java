import DAO.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SeleniumTest {

    String URL = "http://localhost:8080/";
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait;
    ClientDAO clientDAO = new ClientDAO();
    ServiceDAO serviceDAO = new ServiceDAO();
    NumberDAO numberDAO = new NumberDAO();
    ServiceHistoryDAO serviceHistoryDAO = new ServiceHistoryDAO();
    TransactionsDAO transactionsDAO = new TransactionsDAO();

    public class ServicePage {
        public WebDriver driver;

        public ServicePage(WebDriver driver) {
            PageFactory.initElements(driver, this);
            this.driver = driver;
        }

        public WebElement getMenu() {
            return menu;
        }

        public WebElement getFindBtn() {
            return findBtn;
        }

        public WebElement getName_filter() {
            return name_filter;
        }

        public WebElement getSort() {
            return sort;
        }

        @FindBy(xpath = "//*[contains(@id, 'btn')]")
        private WebElement menu;

        @FindBy(xpath = "//*[contains(@id, 'find')]")
        private WebElement findBtn;

        @FindBy(xpath = "//*[contains(@id, 'name_filter')]")
        private WebElement name_filter;
        @FindBy(xpath = "//*[contains(@id, 'sort')]")
        private WebElement sort;

    }

    @BeforeClass
    public void setup() {
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().pageLoadTimeout(10, SECONDS);
        driver.manage().window().setSize(new Dimension(1000, 1000));
        System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
    }

    @Test
    public void testRootPage() {
        URL = "http://localhost:8080/";
        driver.get(URL);
        ServicePage servicePage = new ServicePage(driver);
        WebElement name_filter = servicePage.getName_filter();
        name_filter.sendKeys("а");
        Select sel = new Select(servicePage.getSort());
        sel.selectByValue("По возрастанию");
        WebElement findBtn = servicePage.getFindBtn();
        findBtn.click();

        WebElement table = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        tableRows.remove(0);
        tableRows.forEach(row -> {
            String str = row.findElements(By.tagName("td")).get(0).getText();
            assertTrue(str.contains("а"));
        });
    }

    @Test
    public void testClientsPage() {
        URL = "http://localhost:8080/clients";
        driver.get(URL);
        WebElement table = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        WebElement href = tableRows.get(1).findElements(By.tagName("td")).get(0);
        href.findElement(By.tagName("a")).click();
        WebElement address = wait.until(visibilityOfElementLocated(By.name("address")));
        address.sendKeys("new address");
        WebElement updateBtn = wait.until(visibilityOfElementLocated(By.id("upd_button")));
        updateBtn.click();

        assertEquals(driver.getTitle(), "Successful");
    }



}
