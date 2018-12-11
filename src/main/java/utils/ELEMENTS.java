package utils;

import org.openqa.selenium.By;

public enum ELEMENTS {

    CHROME_MENU("//*[@id='menu_button']"),
    CHROME_MENU_ADD_SCREEN("//*[@text='Add to Home screen']"),
    CHROME_ADD_SCREEN("//*[@text='ADD']");

    private String location;

    /**
     * Constructor.
     *
     * @param location
     */
    ELEMENTS(String location) {
        this.location = location;
    }

    public By getBy() {
        return By.xpath(location);
    }

}
