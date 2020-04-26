package utils.logging

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class WaitUtil {
	static void waitToBeClickable(WebDriver driver, WebElement element){
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element))
	}
}
