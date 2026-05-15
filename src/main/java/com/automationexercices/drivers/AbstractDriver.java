package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

public abstract class AbstractDriver {
    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");
    protected String downloadsPath = System.getProperty("user.dir") + "\\src\\test\\resources";
    public abstract WebDriver createDriver();
}
