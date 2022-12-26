package amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AmazonHomePage extends BasePage{

    @FindBy(xpath = "//a[@class='nav-a  ']")
    List<WebElement> navigationLinks;

    public AmazonHomePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public  Map<String,String> getLinkTexts(){
        List<String> list =new ArrayList<>();
        Map<String,String> linkIdMap= new LinkedHashMap<>();
        String refId = null;
        try {
            for (WebElement element: navigationLinks) {
                if (isElementVisible(element)){
                    list.add(element.getText());
                    refId=element.getAttribute("data-csa-c-content-id");
                    linkIdMap.put(element.getText(),refId);
                }
            }
        }catch (Exception e){
         e.printStackTrace();
        }finally {
            System.out.println(linkIdMap);
            return linkIdMap;
        }
    }
    public void validateNavigationLink(String linkText) {
        try {
            WebElement element = driver.findElement(By.xpath("//a[contains(text(),\"" + linkText + "\")]"));
            waitForElementToBeVisible(element);
            jsClick(element);
            wait(2000);
        }
         catch (Exception e) {
            e.printStackTrace();
        }
   }
   public String getBaseURL(){
      return driver.getCurrentUrl();
   }
    public void backToHome(){
        wait(4000);
        driver.navigate().back();
    }

}
