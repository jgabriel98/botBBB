package globoBBB.login

import geb.Module

class LoginPopupModule extends Module{
	static content = {
		popUpContainer { $('#globoid-modal-container .login-modal') }
		closeButton { popUpContainer.$('.close-modal') }
		loginPageIFrame(page: LoginPage) { popUpContainer.$('iframe') }
	}


}
