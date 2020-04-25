package globoBBB

import geb.Page
import geb.error.RequiredPageContentNotPresent
import geb.navigator.Navigator
import globoBBB.login.LoginPage
import globoBBB.login.LoginPopupModule
import groovy.time.TimeCategory
import org.openqa.selenium.WebDriverException

import static utils.logging.AnsiColors.*


class EnqueteVotacaoPage extends Page {

	static at = { browser.currentUrl.contains('realities/bbb/bbb20/votacao') }

	static content = {
		loginPopup { module(LoginPopupModule) as LoginPopupModule}
		quadroCandidatos { $('#banner_slb_topo ~ div').findAll { it.text().trim().contains('\n') } }
		listaNomeCandidatos { quadroCandidatos.text().split('\n') }
		candidato { String nomeCandidato ->
			quadroCandidatos.children()
					.find { it.text().trim().toLowerCase() == nomeCandidato.trim().toLowerCase() } as Navigator
		}
		//captcha {  }
		votarNovamente { $('button', text: contains('Votar Novamente')) }
	}

	void votaCandidato(String nome) {
		Navigator target = candidato(nome)

		boolean estaAutenticado = false
		while(!estaAutenticado){
			target.click()
			//isso funciona pq se tiver autenticado o método vai retornar que nao pediu pra fazer login. todo: alterar para algo que verifique forma melhor se está autenticado ou nao
			estaAutenticado = !pedeParaFazerLoginSePrecisar()
		}

		println color('Autenticado(a) com sucesso!', LIGHT_GREEN)
		println('o bot irá prosseguir normalmente, não será necessário fazer login novamente.')

		escolheImageDoCaptcha()
	}

	/**
	 * Pede para o usuário se autenticar na plataforma, caso necessário
	 * @return true se pediu para fazer login, false caso contrário
	 */
	private boolean pedeParaFazerLoginSePrecisar() {
		LoginPopupModule loginPopup = browser.module(LoginPopupModule)
		if (!loginPopupEstaAberto())
			return false

		waitFor { loginPopup.loginPageIFrame }
		pedeParaFazerLogin()
		println color('Verificando se autenticou...', DARK_GRAY)

		waitFor { !loginPopupEstaAberto() }	//espera 'camadas extras' sairem da frente, pra tela ser interagivel de novo

		return true
	}

	private void pedeParaFazerLogin() {
		withFrame(loginPopup.loginPageIFrame) {
			println color('É necessário que você esteja autenticado para votar,', LIGHT_YELLOW)
			println color("por favor faça login ${color('através do popup', [UNDERLINE, BOLD])} (por qualquer meio)", DEFAULT)

			final int timeout = 120
			Date start = new Date()
			waitFor(timeout, 0.5) {
				imprimeTempoRestante(start, timeout)
				!loginPopupEstaAberto()
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

	private boolean loginPopupEstaAberto() {
		try {
			return loginPopup.isInsideIt() ?: loginPopup.popUpContainer.displayed
		} catch (WebDriverException e) {
			if (e.message.startsWith('TypeError: can\'t access dead object'))
				return false
			else throw e
		} catch (RequiredPageContentNotPresent e){
			return false
		}
	}

}
