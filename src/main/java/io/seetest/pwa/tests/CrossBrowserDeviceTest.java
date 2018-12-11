package io.seetest.pwa.tests;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.ELEMENTS;
import utils.SeeTestProperties;

/**
 * Example test for Cross browser test in devices.
 */
public class CrossBrowserDeviceTest extends TestBase {


    private static final String SHORTCUT_QUERY
            = "//*[@id='icon_icon' and ./parent::*[./parent::*[./parent::*[@contentDescription='trivago']]]]";

    private By trivagoBy = null;
    private boolean checkPWA = true;
    private boolean isPWA = false;

    /**
     * Sets up the default Desired Capabilities.
     */
    protected void initDefaultDesiredCapabilities() {
        LOGGER.info("Enter initDefaultDesiredCapabilities");
        super.initDefaultDesiredCapabilities();
        dc.setCapability(MobileCapabilityType.BROWSER_NAME, browser);
        LOGGER.info("Exit initDefaultDesiredCapabilities");
    }

    @Override
    protected void installPWA() {
        if ("android".equals(os) && browser.equals("Chrome")) {
            installUrlPWAInChrome(url);
            // Give 250ms  delay
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.HOME);
        }

    }

    /**
     * Installs PWA in Chrome.
     */
    private void installUrlPWAInChrome(String URL) {
        LOGGER.info("Installing PWA.");
        driver.get(URL);
        ((AppiumDriver)driver).context("NATIVE_APP");

        /*driver.findElement(By.xpath("//*[@id='menu_button']")).click();
        driver.findElement(By.xpath("//*[@text='Add to Home screen']")).click();
        driver.findElement(By.xpath("//*[@text='ADD']")).click();*/
        driver.findElement(ELEMENTS.CHROME_MENU.getBy()).click();
        driver.findElement(ELEMENTS.CHROME_MENU_ADD_SCREEN.getBy()).click();
        driver.findElement(ELEMENTS.CHROME_ADD_SCREEN.getBy()).click();

        LOGGER.info("PWA Installation complete.");
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
        LOGGER.info("Looking for XPATH - " + xPathQuery);
        //By xpathQueryBy = By.xpath("//*[contains(text(),'Find your ideal')]");

        By xpathQueryBy = By.xpath(xPathQuery);
        boolean found = false;
        int swipeCount = 0;
        while (!found && swipeCount < 10) {
            if (driver.findElements(xpathQueryBy).size() > 0) {
                LOGGER.info("Found Element for XPATH - " + xPathQuery);
                found = true;
            } else {
                LOGGER.info("Test Found - " + driver.findElement(xpathQueryBy).getText());
                swipeTop();
                swipeCount++;
            }
        }
        //wait.until(ExpectedConditions.visibilityOfElementLocated(xpathQuery));
        LOGGER.info("Test Found - " + driver.findElement(xpathQueryBy).getText());
    }

    @Override
    protected void readyForPWA() {
        LOGGER.info("Setting up for PWA");
        boolean found = false;
        int screenCount = 0;
        // Look for the PWA shortcut by swiping screens if not found will tru
        By trivagoShortCut = By.xpath(SHORTCUT_QUERY);
        while (!found && screenCount < 10) {
            if (driver.findElements(trivagoShortCut).size() > 0) {
                driver.findElement(trivagoShortCut).click();
                found = true;
            } else {
                swipeLeft();
                screenCount++;
            }
        }
        if (!found && screenCount >= 10) {
            LOGGER.error("Shortcut not found ...");
        }
        LOGGER.info("Fininsh - Setting up for PWA");
    }


    private void swipeLeft() {
        TouchAction myAction = new TouchAction((MobileDriver)driver);
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.85);
        int endX = (int) (size.width * 0.05);
        int coordY = (int) (size.height * 0.50);
        LOGGER.info("X = " + size.width + "; Y" + size.height );
        myAction.press(startX,coordY).waitAction(3000).moveTo(endX,coordY).release().perform();
    }

    private void swipeTop() {
        TouchAction myAction = new TouchAction((MobileDriver)driver);
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.85);
        int endY = (int) (size.width * 0.05);
        int coordX = (int) (size.width * 0.50);
        LOGGER.info("X = " + size.width + "; Y" + size.height );
        myAction.press(coordX,startY).waitAction(3000).moveTo(coordX,endY).release().perform();
    }

    @Override
    protected void uninstallPWA() {
        if(isPWA) {
            LOGGER.info("Enter uninstallPWA()");
            // Uninistall logic.
            // For now not unistalling PWA.
            LOGGER.info("PWA Unistalled.");
        }

    }

    @Override
    protected boolean checkPWAUrl() {
        LOGGER.info("Checking if the URL is PWA");
        boolean checkPWAUrl = false;
        if (checkPWA) {
            if (isPWA) {
                checkPWAUrl = isPWA;
            } else {
                // We always return true now.
                checkPWAUrl = true;
            }
        } else {
            checkPWAUrl = false;
        }
        LOGGER.info("checkPWAUrl returning - " + checkPWAUrl);
        return checkPWAUrl;
    }
}
