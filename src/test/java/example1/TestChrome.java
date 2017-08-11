package example1;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class TestChrome extends TestCases {
  
  @BeforeClass
  public void beforeClass() {
	  ClassLoader classLoader = getClass().getClassLoader();
	  String path  = classLoader.getResource("chromedriver-v2.31-win32").getPath();
	  System.out.println(path);
	  System.setProperty("webdriver.chrome.driver", path+"/chromedriver.exe");
	  driver = new ChromeDriver();
  }

  @AfterClass
  public void afterClass() {
	 driver.quit(); 
  }

}
