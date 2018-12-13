package io.seetest.pwa.tests;


import com.experitest.appium.SeeTestCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.SeeTestProperties;

import java.util.Properties;

import static utils.SeeTestProperties.SEETEST_REPORT_TYPE;

/**
 * Base class for all tests.
 */
public abstract class TestBase {

    public static final String ENV_VAR_ACCESS_KEY = "SEETEST_IO_ACCESS_KEY";
    public static final String URL = "https://trivago.co.uk";

    DesiredCapabilities dc = new DesiredCapabilities();
    RemoteWebDriver driver = null;
    String os;
    Properties properties;
    String deviceQuery;
    String deviceId;
    Logger LOGGER = null;
    String url = null;
    String browser = null;

    /**
     * Core setup function, which sets up the selenium/appium drivers.
     * @param testContext Test Context for the Test.
     */
    @Parameters({"os", "browser" , "deviceId"})
    @BeforeClass
    public void setUp(@Optional("android") String os, @Optional ("chrome") String browser,
                      @Optional("") String deviceId, ITestContext testContext) {

        LOGGER = new Log4jLoggerFactory().getLogger(testContext.getCurrentXmlTest().getName());
        LOGGER.info("Enter TestBase setUp");
        this.os = os;
        this.deviceId = deviceId;
        this.browser = browser;
        properties = SeeTestProperties.getSeeTestProperties();
        url = properties.getProperty(SeeTestProperties.WEB_URL, "https://trivago.co.uk");


        String accessKey = System.getenv(ENV_VAR_ACCESS_KEY);

        if (accessKey == null || accessKey.length() < 10) {
            LOGGER.error("Access key must be set in Environment variable SEETEST_IO_ACCESS_KEY");
            LOGGER.info("To get access get to to https://cloud.seetest.io or learn at https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key", accessKey);
            throw new RuntimeException("Access key invalid : accessKey - " + accessKey);
        }

        dc.setCapability(SeeTestCapabilityType.ACCESS_KEY, accessKey);
        dc.setCapability("testType", properties.getProperty(SEETEST_REPORT_TYPE));

        this.initDefaultDesiredCapabilities();
        dc.setCapability("testName",
                testContext.getCurrentXmlTest().getName() + "." + this.getClass().getSimpleName());


        initDriver();

        if (checkPWAUrl()) {
            installPWA();
        }
    }

    /**
     * Initialize default properties.
     *
     */
    protected void initDefaultDesiredCapabilities() {
        LOGGER.info("Setting up Desired Capabilities");

        if (!("".equals(deviceId)) && deviceId != null) {
            dc.setCapability(MobileCapabilityType.UDID, deviceId);
            LOGGER.info("Reserving device", deviceId);
        } else {
            String query = String.format("@os='%s'", os);
            dc.setCapability(SeeTestCapabilityType.DEVICE_QUERY, query);
            LOGGER.info("Device Query = {}", query);
        }
        LOGGER.info("Desired Capabilities setup complete");
    }

    /**
     * Install the PWA
     *
     */
    protected void installPWA() {
        // do nothing
    }


    /**
     * Un Install the PWA
     *
     */
    protected void uninstallPWA() {
        // do nothing
    }

    /**
     * Setup environment for running PWA web site.
     */
    protected void readyForPWA() {
        // do nothing.
    }

    /**
     * Checks id the url is PWA.
     * @return
     */
    protected boolean checkPWAUrl() {
        LOGGER.info("checkPWAUrl returning - " + false);
        return false;
    }


    @AfterClass
    protected void tearDown() {
        if (checkPWAUrl()) {
            uninstallPWA();
        }
        driver.quit();
    }

    /**
     * Initializes Driver.
     */
    protected void initDriver() {
        driver = os.equals("android") ?
                new AndroidDriver(SeeTestProperties.SEETEST_IO_APPIUM_URL, dc) :
                new IOSDriver(SeeTestProperties.SEETEST_IO_APPIUM_URL, dc);
    }
}

