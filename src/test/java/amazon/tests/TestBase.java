package amazon.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase {

    WebDriver driver = null;

    //Extend Report
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeSuite
    public void beforeSuite() {
        createFolderExtendReport();
        htmlReporter = new ExtentHtmlReporter(
                System.getProperty("user.dir") + "/ExtendReport/" + "TestReport" + ".html");
        htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host name", "QA");
        extent.setSystemInfo("Environment", System.getProperty("env").toString());
        htmlReporter.config().setDocumentTitle("Amazon Automation report"); // Tile of report
        htmlReporter.config().setReportName("Amazon Automation report"); // name of the report
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // location of the chart
        htmlReporter.config().setTheme(Theme.STANDARD);

    }
    public void createFolderExtendReport() {
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "ExtendReport");
            file.mkdir();
            boolean flag = file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestResult result, java.lang.reflect.Method methodName) {
        //Extend Report
        logger = extent.createTest(methodName.getName()); // create new entry in the report
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        System.out.println("Before method");
//        System.setProperty("webdriver.chrome.driver", "libs/chromedriver");
        if (System.getProperty("browser").equals("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(System.getProperty("browser").equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();

    }
    @AfterMethod(alwaysRun = true)
    public void captureScreenShot(ITestResult result, java.lang.reflect.Method methodname, ITestContext context){
        if (result.getStatus() == ITestResult.FAILURE) {
            getScreenshot(driver,result.getMethod().getMethodName() );
        }
    }
    public static String getScreenshot(WebDriver driver, String screenshotName) {
        String destination = null;
        try {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            destination = System.getProperty("user.dir") + "/target/Screenshots/" + screenshotName+"_"+dateName + ".png";
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            return destination;
        }
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
    @AfterSuite(alwaysRun = true)
    public void close() throws Exception {
        extent.flush();
    }
}
