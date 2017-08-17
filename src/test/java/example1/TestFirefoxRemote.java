package example1;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestFirefoxRemote extends TestCases {
	
  @BeforeClass
  public void beforeClass() throws MalformedURLException {
	  ClassLoader classLoader = getClass().getClassLoader();
	  String path  = classLoader.getResource("geckodriver-v0.18.0-win32").getPath();
	  System.setProperty("webdriver.gecko.driver", path+"/geckodriver.exe");
 	  driver = new RemoteWebDriver(new URL("http://caqaapp:4444/wd/hub"), DesiredCapabilities.firefox());
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
