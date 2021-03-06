import geb.Browser
import globoBBB.EnqueteVotacaoPage
import globoBBB.GshowHomePage
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.CapabilityType
import utils.logging.AnsiCodes

import static utils.logging.WaitUtil.*

class Main {
	static void main(String[] args) {
		configuraVariaveisDeAmbiente()
		System.err.close()	//descomentar apenas para build de  produção

		Map<String, Long> estatisticas = [tentativasFalhas: 0, tentativasSucedidas: 0]

		Browser browser = new Browser(driver: configuraDriver())
		browser.baseUrl = 'https://gshow.globo.com/'

		browser.to GshowHomePage
		GshowHomePage homePage = browser.page(GshowHomePage)

		homePage.waitFor { homePage.enqueteVotacao.displayed }
		homePage.acessaPaginaVotacao()

		EnqueteVotacaoPage enquetePage = browser.page(EnqueteVotacaoPage)
		while(true) {
			estatisticas.tentativasFalhas += enquetePage.votaCandidato('Thelma') - 1
			estatisticas.tentativasSucedidas++
			imprimeEstatisticas(estatisticas)

			waitToBeClickable(browser.driver, enquetePage.votarNovamente.singleElement())
			enquetePage.votarNovamente.click()
		}

		browser.quit()
	}

	static void imprimeEstatisticas(Map<String, ?> estatisticas){
		String statStr = estatisticas.collect { stat ->
			return "${stat.key}: ${stat.value}".toString()
		}.join(' | ')

		print '\r' + statStr + AnsiCodes.CLEAR_LINE_RIGHT
	}

	static void configuraVariaveisDeAmbiente() {
		//escreve apenas se n ja for definida
		//System.setProperty( "webdriver.gecko.driver",  System.getProperty("webdriver.gecko.driver")  ?: "../resources/geckodriver"  )
		System.setProperty( "webdriver.gecko.driver",  System.getProperty("webdriver.gecko.driver")  ?: "../resources/main/geckodriver"  )
		//System.setProperty( "webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver") ?: "../resources/chromedriver" )
		System.setProperty( "webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver") ?: "../resources/main/chromedriver" )
	}


	static WebDriver configuraDriver() {
		//def opt = new FirefoxOptions()
		def opt = new ChromeOptions()
		opt.setExperimentalOption("excludeSwitches", ["enable-automation"]);


		//normal = complete, eager = interactive, none = none
		opt.setCapability(CapabilityType.PAGE_LOAD_STRATEGY,"none") // interactive

		//return new FirefoxDriver(opt)
		return new ChromeDriver(opt)
	}
}
