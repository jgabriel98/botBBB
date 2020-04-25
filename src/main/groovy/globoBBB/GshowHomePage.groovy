package globoBBB

import geb.Page

class GshowHomePage extends Page{
	static url = "realities/bbb/"

	static at = { browser.currentUrl.contains(url)}

	static content = {
        enqueteVotacao { $(".type-enquete a") }
    }

	void acessaPaginaVotacao(){
		enqueteVotacao.click()
	}

}
