package globoBBB

import geb.Module
import geb.navigator.Navigator
import geb.waiting.WaitTimeoutException
import jdk.jshell.spi.ExecutionControl

class CaptchaModule extends Module {
	static content = {
		imagemInteira(wait: 2, required: false) {
			//def fotosPerfis = $('picture img').findAll { it.displayed }.collect() as List<Navigator>	//pega as imagens que são de foto de perfil
			$('img').find { 	//pega todas imagens que nao sao de perfil, no caso apenas uma:  o captcha
				it.displayed &&
				//!(it in fotosPerfis)  &&
				it.properties.width / it.properties.height > 4	//verifica se a proporcao horizontal é pelo menos 5x maior em relacao a vertical
			}
		}
		subImagens { throw ExecutionControl.NotImplementedException }	//retorna uma lista ou algo iteravel, das 5 mini imagens, provavelmente as 5 regiões clicaveis da imagem. Aqui vai ser gambiarra pq eh dificil
	}

	void esperaEscolhaSerProcessada(){
		try { waitFor(3) { estaProcessandoEscolha() }	//primeiro espera o inicio do processo do voto
		} catch(WaitTimeoutException ignore){}

		waitFor(15) { !estaProcessandoEscolha() }
	}

	boolean estaProcessandoEscolha(){
		return !$('div', text: contains('Processando seu voto')).empty
	}
}
