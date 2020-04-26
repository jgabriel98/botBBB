package globoBBB

import geb.Page
import geb.error.RequiredPageContentNotPresent
import geb.navigator.Navigator
import globoBBB.login.LoginPopupModule
import groovy.time.TimeCategory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import utils.logging.AnsiCodes

import static utils.logging.WaitUtil.*
import static utils.logging.AnsiColors.*


class EnqueteVotacaoPage extends Page {

	static at = { browser.currentUrl.contains('realities/bbb/bbb20/votacao') }

	static content = {
		loginPopup { module(LoginPopupModule) as LoginPopupModule}
		quadroCandidatos(wait: true) { $('#banner_slb_topo ~ div').findAll { it.text().trim().contains('\n') } }
		listaNomeCandidatos { quadroCandidatos.text().split('\n') }
		candidato { String nomeCandidato ->
			quadroCandidatos.children()
					.find { it.text().trim().toLowerCase() == nomeCandidato.trim().toLowerCase() } as Navigator
		}
		captcha { module(CaptchaModule) as CaptchaModule }
		votarNovamente { $('button', text: contains('Votar Novamente')) }
	}

	/**
	 * vota no candidato com o nome informado
	 * @param nome
	 * @return quantidade de tentativas feitas para votar no candidato
	 */
	int votaCandidato(String nome) {
		Navigator target = candidato(nome)

		boolean estaAutenticado = false
		boolean precisouAutenticar
		while(!estaAutenticado){
			waitFor{ target.click() }
			precisouAutenticar = pedeParaFazerLoginSePrecisar() //isso funciona pq se tiver autenticado o método vai retornar que nao pediu pra fazer login. todo: alterar para algo que verifique forma melhor se está autenticado ou nao
			estaAutenticado = captcha.imagemInteira as Boolean
		}

		if(precisouAutenticar) {
			println color('Autenticado(a) no sistema', LIGHT_GREEN)
			println('o bot irá prosseguir normalmente, não será necessário fazer login novamente até que a sessao expire')
		}

		return clicaNoCaptchaAteAcertar()
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

	/**
	 * continua clicando no captcha até aceitar o voto
	 * @return quantidade de tentativas
	 */
	private int clicaNoCaptchaAteAcertar(){
		int tentativas = 0
		while(captcha.imagemInteira?.displayed){
			clicaNoCaptcha()							//parece estar demorando clicar de novo quando erra o captcha, investigar isso
			captcha.esperaEscolhaSerProcessada()
			tentativas++
		}
		return tentativas
	}

	private void clicaNoCaptcha() {
		waitToBeClickable(driver, captcha.imagemInteira.singleElement())
		//println('escolhendo uma imagem qualquer no captcha')
		captcha.imagemInteira.click()
	}

	private static void imprimeTempoRestante(Date inicio, int tempoLimite){
		int segundosPassados = TimeCategory.minus(new Date(), inicio).seconds
		print '\rTempo restante:' + color( tempoLimite-segundosPassados as String, BOLD) + color('s', ITALIC) + AnsiCodes.CLEAR_LINE_RIGHT
		System.out.flush()
	}

	/**
	 * @return se os o popup de login está aberto, estando dentro dele ou não
	 */
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
