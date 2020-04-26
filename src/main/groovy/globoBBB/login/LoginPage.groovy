package globoBBB.login

import geb.Page

class LoginPage extends Page{
	static at = { !base.empty }

	static content = {
		base (wait: 2){ $('div.content #tpl-content') }
		loginForm (wait:2){ $('#login-form')}
	}
}
