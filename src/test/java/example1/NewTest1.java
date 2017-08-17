package example1;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class NewTest1 {
  @SuppressWarnings("unused")
  private SoftAssert softAssert;
  private ExprCalc expc;
  
  @Test
  public void verifyReturnType() {
	Object result = 1+2;
	Assert.assertSame(expc.result("1.2").getClass(), Double.class, "1.2");
	Assert.assertSame(expc.result("1 B C").getClass(), String.class, "1 B C");
	Assert.assertSame(result.getClass(),Integer.class);
	Assert.assertSame(expc.result("1+2").getClass(), Integer.class, "1+2");
	Assert.assertSame(expc.result("1.0+2").getClass(), Double.class, "1.0+2");
	Assert.assertSame(expc.result("1>2").getClass(), Boolean.class, "1>2");
	Assert.assertSame(expc.result("ABC").getClass(), String.class, "ABC");
	Assert.assertSame(expc.result("1.2").getClass(), Double.class, "1.2");
	Assert.assertSame(expc.result("100 ").getClass(), Integer.class, "100 ");
	Assert.assertSame(expc.result("-1").getClass(), Integer.class, "-1");
	Assert.assertSame(expc.result("+1.1").getClass(), Double.class, "+1.1");
	Assert.assertEquals(expc.result("*").getClass(), String.class, "*");
  }
  
  @Test
  public void verifyReturnValue() {
	Assert.assertEquals(expc.result("1.2"), 1.2);
	Assert.assertEquals(expc.result("1 B C"), "1 B C");
	Assert.assertEquals(expc.result("1+2"),1+2);
	Assert.assertEquals(expc.result("1.0+2"),1.0+2);
	Assert.assertEquals(expc.result("1>2"),1>2);
	Assert.assertEquals(expc.result("100 "),100);
	Assert.assertEquals(expc.result("(2 +2*5/11.0-6.0/3 > 1) OR (3>2) AND \"ABC 11\"==\"ABC 11\""), (2 +2*5/11.0-6.0/3 > 1) || (3>2) && "ABC 11"=="ABC 11");
	Assert.assertEquals(expc.result("(1+(3-(5.0+2))*(4-2)/2)"),(1+(3-(5.0+2))*(4-2)/2));
	Assert.assertEquals(expc.result("2 <= 2"), 2 <= 2);
	Assert.assertEquals(expc.result("-1"), -1);
	Assert.assertEquals(expc.result("+1.1"), 1.1);
	Assert.assertEquals(expc.result("1==\"1\" OR 2==1"), true);
	Assert.assertEquals(expc.result("*"), "*");
  }

  @Test
  public void f() {
	  short s1=3, s2=4;
	  byte b1=7, b2=8;
	  float f1=9, f2=10;

	  Object result = s1+s2;
	  Assert.assertSame(result.getClass(),Integer.class,"1+2");

	  result = b1/b2;
	  Assert.assertSame(result.getClass(),Integer.class,"1/2");
	  
	  result = f1/f2;
	  Assert.assertSame(result.getClass(),Float.class,"f1/2");
	  
	  result = 1==2;
	  Assert.assertSame(result.getClass(),Boolean.class,"1==2");
		
	  result = "1==2";
	  Assert.assertSame(result.getClass(),String.class,"\"1==2\"");
	
	  result = 1L;
	  Assert.assertSame(result.getClass(),Long.class,"1L");
	
	  result = b1;
	  Assert.assertSame(result.getClass(),Byte.class,"b1");
		
	  result = '1';
	  Assert.assertSame(result.getClass(),Character.class,"'1'");
	
	  result = 1f;
	  Assert.assertSame(result.getClass(),Float.class,"1f");
  
  }

  
  @BeforeClass
  public void beforeTest() {
	  softAssert = new SoftAssert();
	  expc = new ExprCalc();
  }

  @AfterClass
  public void afterTest() {
  }

}
