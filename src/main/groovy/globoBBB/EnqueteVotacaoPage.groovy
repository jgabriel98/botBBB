package globoBBB

import geb.Page
import geb.navigator.Navigator
import globoBBB.login.LoginPopupModule
import groovy.time.TimeCategory

import static utils.logging.AnsiColors.*


class EnqueteVotacaoPage extends Page {

	static at = { browser.currentUrl.contains('realities/bbb/bbb20/votacao') }

	static content = {
		loginPopup { module(LoginPopupModule) }
		quadroCandidatos { $('#banner_slb_topo ~ div').findAll { it.text().trim().contains('\n') } }
		listaNomeCandidatos { quadroCandidatos.text().split('\n') }
		candidato { String nomeCandidato ->
			quadroCandidatos.children()
					.find { it.text().trim().toLowerCase() == nomeCandidato.trim().toLowerCase() } as Navigator
		}
		votarNovamente { $('button', text: contains('Votar Novamente')) }
	}

	void votaCandidato(String nome) {
		Navigator target = candidato(nome)
		target.click()
		pedeParaFazerLoginSePrecisar()
		escolheImageDoCaptcha()
	}

	/**
	 * Pede para o usuário se autenticar na plataforma, caso necessário
	 * @return true se pediu para fazer login, false caso contrário
	 */
	boolean pedeParaFazerLoginSePrecisar() {
		LoginPopupModule loginPopup = browser.module(LoginPopupModule)
		if (!loginPopup.popUpContainer?.displayed)
			return false

		while(loginPopup.popUpContainer.displayed){
			pedeParaFazerLogin()
			println color('Verificando se autenticou...', DARK_GRAY)
		}
		println color('Autenticado(a) com sucesso!', LIGHT_GREEN)
		println('o bot irá prosseguir normalmente, não será necessário fazer login novamente.')
		return true
	}

	private void pedeParaFazerLogin() {
		withFrame(loginPopup.loginPageIFrame) {
			println color('É necessário que você esteja autenticado para votar,', LIGHT_YELLOW)
			println color("por favor faça login ${color('através do popup', [UNDERLINE, BOLD])} (por qualquer meio)", DEFAULT)

			final int timeout = 120
			Date start = new Date()
			waitFor(timeout) {
				imprimeTempoRestante(start, timeout)
				return !loginPopup.popUpContainer?.displayed
			}
		}
	}

	private escolheImageDoCaptcha() {

	}

	private static void imprimeTempoRestante(Date inicio, int tempoLimite){
		int segundosPassados = TimeCategory.minus(new Date(), inicio).seconds
		print '\t\rTempo restante:' + color( tempoLimite-segundosPassados as String, BOLD) + color('s', ITALIC)
		System.out.flush()
	}

}
