package amazon.tests;

import amazon.pages.AmazonHomePage;
import amazon.utils.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AmazonTest extends TestBase {
    AmazonHomePage amazonHomePage = null;
    @Test(description = "Verify the Amazon home ")
    public void verifyUserIsAbleToOpenAmazonHomePage(){
        amazonHomePage =new AmazonHomePage(driver);
        logger.info("Opening amazon base URL");
        driver.get(Properties.baseUrl);
        logger.info("Getting the URL title");
        String title = driver.getTitle();
        logger.info("Asserting the home URL title");
        Assert.assertTrue(title.equals("Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in"),"titile is not matched");

    }
    @Test(description = "Verifying all the navigation links")
    public void verifyNavigationLinks(){
        amazonHomePage =new AmazonHomePage(driver);
        logger.info("Opening bug home URL");
        driver.get(Properties.baseUrl);
        logger.info("Getting the URL title");
        Map<String,String> linkIdMap = amazonHomePage.getLinkTexts();
        logger.info("Clicking on each navigation links");
        Set<String> linkset = linkIdMap.keySet();
        for (String linkText : linkset) {
            logger.info("verifying "+linkText +"navigation link");
            amazonHomePage.validateNavigationLink(linkText);
            String linkId = linkIdMap.get(linkText);
            String baseURL = amazonHomePage.getBaseURL();
            amazonHomePage.backToHome();
            if (!linkText.equals("Prime")){
                Assert.assertTrue(baseURL.trim().contains(linkId.trim()),"Navigation URL is not matched");
            }else {
                Assert.assertTrue(baseURL.trim().contains("footer_amznow"),"Navigation URL is not matched");
            }

        }

    }

}
