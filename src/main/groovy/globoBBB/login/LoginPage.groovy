package globoBBB.login

import geb.Page

class LoginPage extends Page{
	static at = { !base.empty }

	static content = {
		base { $('div.content #tpl-content') }
		loginForm{ $('#login-form')}
	}
}
