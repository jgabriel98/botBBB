import geb.Browser
import globoBBB.EnqueteVotacaoPage
import globoBBB.GshowHomePage
import org.openqa.selenium.firefox.FirefoxDriver

class Main {
	static void main(String[] args) {
		configuraVariaveisDeAmbiente()

		Browser browser = new Browser(driver: new FirefoxDriver())
		browser.baseUrl = 'https://gshow.globo.com/'

		browser.to GshowHomePage
		GshowHomePage homePage = browser.page(GshowHomePage)

		homePage.waitFor { homePage.enqueteVotacao.displayed }
		homePage.acessaPaginaVotacao()

		EnqueteVotacaoPage enquetePage = browser.page(EnqueteVotacaoPage)
		while(true) {
			enquetePage.votaCandidato('Rafa')
			enquetePage.votarNovamente.click()
		}

		browser.quit()
	}

	static void configuraVariaveisDeAmbiente() {
		//escreve apenas se n ja for definida
		System.setProperty( "webdriver.gecko.driver",  System.getProperty("webdriver.gecko.driver")  ?: "../resources/geckodriver"  )
		System.setProperty( "webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver") ?: "../resources/chromedriver" )
	}
}
