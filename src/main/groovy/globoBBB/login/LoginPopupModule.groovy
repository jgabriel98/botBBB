package globoBBB.login

import geb.Module
import geb.error.RequiredPageContentNotPresent
import geb.waiting.WaitTimeoutException
import jline.internal.Log

class LoginPopupModule extends Module {

	static content = {
		popUpContainer { $('#globoid-modal-container .login-modal') }	//elemento fora do iframe
		closeButton { popUpContainer.$('.close-modal') }				//elemento fora do iframe
		loginPageIFrame(page: LoginPage) { popUpContainer.$('iframe') }	//elemento fora do iframe (é o iframe em si)
	}

	boolean isInsideIt(){
		try{
			return browser.page(LoginPage).verifyAt()
		}catch(Exception e){
			return false
		}catch(WaitTimeoutException e){
			return false
		}catch(RequiredPageContentNotPresent e){
			return false
		}
	}
}
