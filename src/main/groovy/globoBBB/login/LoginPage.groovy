package globoBBB.login

import geb.Page

class LoginPage extends Page{
	static content = {
		loginForm{ $('#login-form')}
	}
}
