package Bot.TwitterBot;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class App 
{
	public static void passRecaptcha(WebDriver driver) {
		try {
			WebElement start = driver.findElement(By.cssSelector("[type=\"submit\"]"));
			start.click();
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	WebElement form = driver.findElement(By.tagName("form"));
	    	Actions builder = new Actions(driver);   
	    	builder.moveToElement(form, 20, 39).click().build().perform();
	    	
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	driver.findElement(By.cssSelector("[value=\"Continue\"]"));
	    	
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	driver.findElement(By.cssSelector("[value=\"Continue to Twitter\"]"));
		} catch(Exception e) {
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
    public static void main( String[] args )
    {
    	System.setProperty("webdriver.chrome.driver", "chromedriver");
    	WebDriver driver = new ChromeDriver();
    	driver.get("https://twitter.com");
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	
    	WebElement login = driver.findElement(By.cssSelector("[data-testid='loginButton']"));
    	login.click();
    	
    	User user = new User(args[0],args[1]);
    	String hashtag = args[2];
    	
    	WebElement username = driver.findElement(By.cssSelector("[name=\"session[username_or_email]\"]"));
    	username.sendKeys(user.getEmail());
    	
    	WebElement password = driver.findElement(By.cssSelector("[name=\"session[password]\"]"));
    	password.sendKeys(user.getPassword());
    	
    	WebElement loginButton = driver.findElement(By.cssSelector("[data-testid=\"LoginForm_Login_Button\"]"));
    	loginButton.click();
    	
    	
    	WebElement searchBox = driver.findElement(By.cssSelector("[aria-label=\"Search query\"]"));
    	searchBox.sendKeys(hashtag);
    	searchBox.submit();
    	int count = 50;
    	while(count-- > 0) {
    		
    		try {
		    	List<WebElement> tweets = driver.findElements(By.cssSelector("[data-testid=\"retweet\"]"));
		    	for(WebElement tweet : tweets) {
		    		tweet.click();
		    		WebElement retweetConfirm = driver.findElement(By.cssSelector("[data-testid=\"retweetConfirm\""));
		        	retweetConfirm.click();
		    	}   
		    	
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		        js.executeScript("window.scrollBy(0,500)");
		    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		} catch(Exception e) {
    			System.out.println(e);
    			passRecaptcha(driver);
    		}
    	}
    	System.out.println("Successful");
    }
}
