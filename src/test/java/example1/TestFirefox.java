package example1;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestFirefox extends TestCases {
	
  @BeforeClass
  public void beforeClass() {
	  ClassLoader classLoader = getClass().getClassLoader();
	  String path  = classLoader.getResource("geckodriver-v0.18.0-win32").getPath();
	  System.setProperty("webdriver.gecko.driver", path+"/geckodriver.exe");
	  driver = new FirefoxDriver();
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
