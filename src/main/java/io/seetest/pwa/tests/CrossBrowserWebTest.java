package io.seetest.pwa.tests;


import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.SeeTestProperties;

/**
 * Example test for Cross browser test in devices.
 */
public class CrossBrowserWebTest extends TestBase {

    /**
     * Sets up the default Desired Capabilities.
     */
    protected void initDefaultDesiredCapabilities() {
        dc.setCapability(CapabilityType.VERSION, "Any");
        dc.setCapability(CapabilityType.PLATFORM, os);
        dc.setCapability(MobileCapabilityType.BROWSER_NAME, browser);
    }

    @Test
    protected void testCrossBrowserTest() {
        LOGGER.info("Enter testCrossBrowserTest()");
        if ("android".equals(os) && browser.equals("Chrome")) {
            readyForPWA();
        } else {
            driver.get(url);
        }
        String logMsg = String.format("Testing with - OS = %s ; Browser = %s", os , browser);
        LOGGER.info(logMsg);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        String xPathQuery = properties.getProperty(SeeTestProperties.XPATH_QUERY);
        By xpathQuery = By.xpath(xPathQuery);
        LOGGER.info("Test Found - " + driver.findElement(xpathQuery).getText());
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Swipe to Top.
     */
    private void swipeTop() {
        TouchAction myAction = new TouchAction((MobileDriver)driver);
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.85);
        int endY = (int) (size.width * 0.05);
        int coordX = (int) (size.width * 0.50);
        LOGGER.info("X = " + size.width + "; Y" + size.height );
        myAction.press(coordX,startY).waitAction(3000).moveTo(coordX,endY).release().perform();
    }

    /**
     * Overides the driver initialization.
     */
    protected void initDriver() {
        driver = new RemoteWebDriver(SeeTestProperties.SEETEST_IO_APPIUM_URL, dc);
    }
}

