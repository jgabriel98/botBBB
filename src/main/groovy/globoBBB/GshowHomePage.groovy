package globoBBB

import geb.Page
import utils.logging.WaitUtil

class GshowHomePage extends Page{
	static url = "realities/bbb/"

	static at = { browser.currentUrl.contains(url)}

	static content = {
        enqueteVotacao { $(".type-enquete a") }
    }

	void acessaPaginaVotacao(){
		WaitUtil.waitToBeClickable(driver, enqueteVotacao.singleElement())
		enqueteVotacao.click()
	}

}
