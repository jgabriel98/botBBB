import geb.Browser
import geb.navigator.Navigator
import globoBBB.EnqueteVotacaoPage
import globoBBB.GshowHomePage
import org.openqa.selenium.firefox.FirefoxDriver

class Main {
	static void main(String[] args) {
		// Using a simple println statement to print output to the console

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
}
