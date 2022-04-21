import DAO.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ServicePage servicePage = new ServicePage(driver);
        WebElement name_filter = servicePage.getName_filter();
        name_filter.sendKeys("а");
        Select sel = new Select(servicePage.getSort());
        sel.selectByValue("По возрастанию");
        WebElement findBtn = servicePage.getFindBtn();
        findBtn.click();
        WebElement table = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        WebElement href = tableRows.get(1).findElements(By.tagName("td")).get(0);
        href.findElement(By.tagName("a")).click();
        WebElement address = wait.until(visibilityOfElementLocated(By.name("address")));
        address.sendKeys("new address");
        WebElement updateBtn = wait.until(visibilityOfElementLocated(By.id("upd_button")));
        updateBtn.click();
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/client/registration";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.name("fcn"))).sendKeys("new fcn");
        wait.until(visibilityOfElementLocated(By.name("type"))).sendKeys("new type");
        wait.until(visibilityOfElementLocated(By.name("address"))).sendKeys("new address");
        wait.until(visibilityOfElementLocated(By.name("email"))).sendKeys("new email");
        wait.until(visibilityOfElementLocated(By.name("btn"))).click();
        assertEquals(driver.getTitle(), "Successful");
        Pattern pattern = Pattern.compile("Client added successfully with id = (\\d+)");
        String str = driver.findElement(By.id("msg")).getText();
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        URL = "http://localhost:8080/delete_client?id=" + matcher.group(1);
        driver.get(URL);
        assertEquals(driver.getTitle(), "Successful");
    }

    @Test
    public void testServicePage() {
        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("services"))).click();
        ServicePage servicePage = new ServicePage(driver);
        WebElement name_filter = servicePage.getName_filter();
        name_filter.sendKeys("а");
        Select sel = new Select(servicePage.getSort());
        sel.selectByValue("По возрастанию");
        WebElement findBtn = servicePage.getFindBtn();
        findBtn.click();
        WebElement table = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        WebElement href = tableRows.get(1).findElements(By.tagName("td")).get(0);
        href.findElement(By.tagName("a")).click();
        WebElement address = wait.until(visibilityOfElementLocated(By.name("structure")));
        address.sendKeys("new structure");
        WebElement updateBtn = wait.until(visibilityOfElementLocated(By.id("upd_btn")));
        updateBtn.click();
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/service/registration";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.name("name"))).sendKeys("new name");
        wait.until(visibilityOfElementLocated(By.name("pay_per_month"))).sendKeys("100");
        wait.until(visibilityOfElementLocated(By.name("pay_per_day"))).sendKeys("100");
        wait.until(visibilityOfElementLocated(By.name("start_cost"))).sendKeys("100");
        wait.until(visibilityOfElementLocated(By.name("structure"))).sendKeys("SMS : 100");
        wait.until(visibilityOfElementLocated(By.id("add_btn"))).click();
        assertEquals(driver.getTitle(), "Successful");
        Pattern pattern = Pattern.compile("Service added successfully with id = (\\d+)");
        String str = driver.findElement(By.id("msg")).getText();
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        URL = "http://localhost:8080/delete_service?id=" + matcher.group(1);
        driver.get(URL);
        assertEquals(driver.getTitle(), "Successful");
    }

    @Test
    public void testNumbersPage() {
        URL = "http://localhost:8080/clients";
        driver.get(URL);
        WebElement table = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        WebElement href = tableRows.get(1).findElements(By.tagName("td")).get(0);
        href.findElement(By.tagName("a")).click();
        wait.until(visibilityOfElementLocated(By.id("num_button"))).click();

        wait.until(visibilityOfElementLocated(By.name("number"))).sendKeys("8888");
        wait.until(visibilityOfElementLocated(By.name("balance"))).sendKeys("30");
        wait.until(visibilityOfElementLocated(By.name("max_credit"))).sendKeys("1000000");
        wait.until(visibilityOfElementLocated(By.id("ui-button"))).click();
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/delete_number?id=8888";
        driver.get(URL);
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("numbers"))).click();
        assertEquals(driver.getTitle(), "Numbers");
    }

    @Test
    public void testServiceHistoryPage() {
        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("services"))).click();
        WebElement table = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        WebElement href = tableRows.get(1).findElements(By.tagName("td")).get(0);
        href.findElement(By.tagName("a")).click();
        wait.until(visibilityOfElementLocated(By.id("num_button"))).click();

        wait.until(visibilityOfElementLocated(By.name("number"))).sendKeys("888");
        wait.until(visibilityOfElementLocated(By.name("ctime"))).sendKeys("2017-02-14 19:30:00");
        wait.until(visibilityOfElementLocated(By.name("dtime"))).sendKeys("2017-02-14 19:30:40");
        wait.until(visibilityOfElementLocated(By.name("reg"))).click();
        assertEquals(driver.getTitle(), "Successful");
        Pattern pattern = Pattern.compile("ServiceHistory added successfully with id = (\\d+)");
        String str = driver.findElement(By.id("msg")).getText();
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        URL = "http://localhost:8080/delete_service_history?id=" + matcher.group(1);
        driver.get(URL);
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("service_history"))).click();
        assertEquals(driver.getTitle(), "ServiceHistory");
    }


    @Test
    public void testTransactionsPage() {
        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("transactions_reg"))).click();

        wait.until(visibilityOfElementLocated(By.name("number"))).sendKeys("888");
        wait.until(visibilityOfElementLocated(By.name("type"))).sendKeys("зачисление");
        wait.until(visibilityOfElementLocated(By.name("time"))).sendKeys("2017-02-14 19:30:00");
        wait.until(visibilityOfElementLocated(By.name("sum"))).sendKeys("2028");
        wait.until(visibilityOfElementLocated(By.className("btn-primary"))).click();
        assertEquals(driver.getTitle(), "Successful");
        Pattern pattern = Pattern.compile("Transactions added successfully with id = (\\d+)");
        String str = driver.findElement(By.id("msg")).getText();
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        URL = "http://localhost:8080/delete_transactions?id=" + matcher.group(1);
        driver.get(URL);
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("transactions"))).click();
        assertEquals(driver.getTitle(), "Журнал транзакций");
    }



}
