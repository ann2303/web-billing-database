import DAO.*;
import com.google.common.collect.Lists;
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
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.*;

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
        wait = new WebDriverWait(driver,15);
        driver.manage().timeouts().pageLoadTimeout(15, SECONDS);
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
        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("clients"))).click();

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

        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("client_reg"))).click();
        wait.until(visibilityOfElementLocated(By.name("fcn"))).sendKeys("fcn_new");
        wait.until(visibilityOfElementLocated(By.name("type"))).sendKeys("new type");
        wait.until(visibilityOfElementLocated(By.name("address"))).sendKeys("new address");
        wait.until(visibilityOfElementLocated(By.name("email"))).sendKeys("new email");
        wait.until(visibilityOfElementLocated(By.name("btn"))).click();
        assertEquals(driver.getTitle(), "Successful");
        Pattern pattern = Pattern.compile("Client added successfully with id = (\\d+)");
        String str = driver.findElement(By.id("msg")).getText();
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        wait.until(visibilityOfElementLocated(By.id("main_page"))).click(); // check added
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("clients"))).click();
        ServicePage servicePage1 = new ServicePage(driver);
        WebElement name_filter1 = servicePage1.getName_filter();
        name_filter1.sendKeys("fcn_new");
        WebElement findBtn1 = servicePage.getFindBtn();
        findBtn1.click();
        WebElement table1 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows1 = table1.findElements(By.tagName("tr"));
        assertEquals(tableRows1.size(), 2); // table header + 1 row
        assertNotEquals(tableRows1.get(1).getText(), "No clients :(");

        WebElement href1 = tableRows1.get(1).findElements(By.tagName("td")).get(0);
        href1.findElement(By.tagName("a")).click();

        wait.until(visibilityOfElementLocated(By.id("del_button"))).click();
        assertEquals(driver.getTitle(), "Successful");
        wait.until(visibilityOfElementLocated(By.id("main_page"))).click(); // check
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("clients"))).click();
        ServicePage servicePage2 = new ServicePage(driver);
        WebElement name_filter2 = servicePage2.getName_filter();
        name_filter2.sendKeys("fcn_new");
        WebElement findBtn2 = servicePage.getFindBtn();
        findBtn2.click();
        WebElement table2 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows2 = table2.findElements(By.tagName("tr"));
        assertEquals(tableRows2.size(), 2); // table header + no clients
        assertEquals(tableRows2.get(1).getText(), "No clients :(");
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
        wait.until(visibilityOfElementLocated(By.name("name"))).sendKeys("service_name");
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
        wait.until(visibilityOfElementLocated(By.id("main_page"))).click(); // check
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("services"))).click();
        ServicePage servicePage1 = new ServicePage(driver);
        WebElement name_filter1 = servicePage1.getName_filter();
        name_filter1.sendKeys("service_name");
        WebElement findBtn1 = servicePage.getFindBtn();
        findBtn1.click();
        WebElement table1 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows1 = table1.findElements(By.tagName("tr"));
        assertEquals(tableRows1.size(), 2); // table header + 1 row
        assertNotEquals(tableRows1.get(1).getText(), "No services :(");

        WebElement href1 = tableRows1.get(1).findElements(By.tagName("td")).get(0);
        href1.findElement(By.tagName("a")).click();

        wait.until(visibilityOfElementLocated(By.id("del_btn"))).click();
        assertEquals(driver.getTitle(), "Successful");
        wait.until(visibilityOfElementLocated(By.id("main_page"))).click();
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("services"))).click();
        ServicePage servicePage2 = new ServicePage(driver);
        WebElement name_filter2 = servicePage2.getName_filter();
        name_filter2.sendKeys("service_name");
        WebElement findBtn2 = servicePage.getFindBtn();
        findBtn2.click();
        WebElement table2 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows2 = table2.findElements(By.tagName("tr"));
        assertEquals(tableRows2.size(), 2); // table header
        assertEquals(tableRows2.get(1).getText(), "No services :(");
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

        wait.until(visibilityOfElementLocated(By.name("number"))).sendKeys("88888");
        wait.until(visibilityOfElementLocated(By.name("balance"))).sendKeys("30");
        wait.until(visibilityOfElementLocated(By.name("max_credit"))).sendKeys("1000000");
        wait.until(visibilityOfElementLocated(By.id("ui-button"))).click();
        assertEquals(driver.getTitle(), "Successful");

        wait.until(visibilityOfElementLocated(By.id("del2"))).click();
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("numbers"))).click();
        assertEquals(driver.getTitle(), "Numbers");
        WebElement table1 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows1 = table1.findElements(By.tagName("tr"));
        List<String> l = Lists.newArrayList("89375082191", "Ссылка владельца", "101.0", "5001.0"); // check content
        assertTrue(tableRows1.stream()
                .map(row -> row.findElements(By.tagName("td")).stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList()))
                .anyMatch(list -> list.equals(l)));
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

        wait.until(visibilityOfElementLocated(By.id("del4"))).click();
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("service_history"))).click();
        assertEquals(driver.getTitle(), "ServiceHistory");
        WebElement table1 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows1 = table1.findElements(By.tagName("tr"));
        List<String> l = Lists.newArrayList("7",
                "Service's page",
                "7",
                "2021-10-09 12:00:00.0",
                "2021-11-04 12:00:00.0"); // check content
        assertTrue(tableRows1.stream()
                .map(row -> row.findElements(By.tagName("td")).stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList()))
                .anyMatch(list -> list.equals(l)));

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

        wait.until(visibilityOfElementLocated(By.id("del5"))).click();
        assertEquals(driver.getTitle(), "Successful");

        URL = "http://localhost:8080/";
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.id("btn"))).click();
        wait.until(visibilityOfElementLocated(By.id("transactions"))).click();
        assertEquals(driver.getTitle(), "Журнал транзакций");
        WebElement table1 = wait.until(visibilityOfElementLocated(By.className("table")));
        List<WebElement> tableRows1 = table1.findElements(By.tagName("tr"));
        List<String> l = Lists.newArrayList("89375082191", "2021-10-05 12:00:00.0", "списание", "90.0"); // check content
        assertTrue(tableRows1.stream()
                .map(row -> row.findElements(By.tagName("td")).stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList()))
                .anyMatch(list -> list.equals(l)));
    }

}
