package globoBBB

import geb.Module
import geb.Page
import geb.navigator.Navigator

class EnqueteVotacaoPage extends Page {

	static at = { browser.currentUrl.contains('realities/bbb/bbb20/votacao') }

	static content = {
		loginPopup { module(LoginModule) }
		quadroCandidatos { $('#banner_slb_topo ~ div').findAll { it.text().trim().contains('\n') } }
		listaNomeCandidatos { quadroParaEscolher.text().split('\n') }
		candidato { String nomeCandidato ->
			quadroCandidatos.children()
					.find { it.text().trim().toLowerCase() == nomeCandidato.trim().toLowerCase() } as Navigator
		}
		votarNovamente { $('button',text: contains('Votar Novamente')) }
	}

	void votaCandidato(String nome) {
		Navigator target = candidato(nome)
		target.click()
		pedeParaFazerLoginSePrecisar()
		escolheImageDoCaptcha()
	}

	void pedeParaFazerLoginSePrecisar(){

	}

	private escolheImageDoCaptcha(){

	}

}
