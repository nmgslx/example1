package example1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

abstract public class TestCases {
  protected WebDriver driver;
	  @Test
	  public void f() {
		  driver.get("https://www.w3schools.com/");
		  WebElement e = driver.findElement(By.cssSelector("a.w3schools-logo"));
		  Assert.assertEquals(e.getAttribute("innerText"),"w3schools.com");
	  }

}
