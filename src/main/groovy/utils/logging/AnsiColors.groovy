package utils.logging

/**
 * Small ANSI coloring utility.
 * <br>Original from <a href='https://gist.github.com/tvinke/db4d21dfdbdae49e6f92dcf1ca6120de'>tvinke</a>
 * <br>Modified by <a href='https://github.com/jgabriel98'>João Gabriel</a>
 * @see
 * <a href="http://www.bluesock.org/~willg/dev/ansi.html">http://www.bluesock.org/~willg/dev/ansi.html</a>
 * <br>
 * <a href="https://gist.github.com/dainkaplan/4651352">https://gist.github.com/dainkaplan/4651352</a>
 */
class AnsiColors {
	static final String RESET = "\u001B[0m"

    static final String	BOLD            = "\u001B[1m"
    static final String	ITALIC	        = "\u001B[3m"
    static final String	UNDERLINE       = "\u001B[4m"
    static final String	BLINK           = "\u001B[5m"
    static final String	RAPID_BLINK	    = "\u001B[6m"
    static final String	REVERSE_VIDEO   = "\u001B[7m"
    static final String	INVISIBLE_TEXT  = "\u001B[8m"

    static final String RESET_BOLD           = "\u001B[21m"     //parece n ser mt suportado, melhor usar o reset normal
    static final String	RESET_ITALIC	     = "\u001B[23m"
    static final String	RESET_UNDERLINE      = "\u001B[24m"
    static final String	RESET_BLINK          = "\u001B[25m"
    static final String	RESET_RAPID_BLINK	 = "\u001B[26m"
    static final String	RESET_REVERSE_VIDEO  = "\u001B[27m"
    static final String	RESET_INVISIBLE_TEXT = "\u001B[28m"

    //echo -e "Normal \e[1mBold \e[21mNormal"

    static final String	BLACK           = "\u001B[30m"
    static final String	RED             = "\u001B[31m"
    static final String	GREEN           = "\u001B[32m"
    static final String	YELLOW          = "\u001B[33m"
    static final String	BLUE            = "\u001B[34m"
    static final String	MAGENTA         = "\u001B[35m"
    static final String	CYAN            = "\u001B[36m"
    static final String	WHITE           = "\u001B[37m"
    static final String	DEFAULT         = "\u001B[39m"

    static final String	DARK_GRAY       = "\u001B[1;90m"
    static final String	LIGHT_RED       = "\u001B[1;31m"
    static final String	LIGHT_GREEN     = "\u001B[1;32m"
    static final String LIGHT_YELLOW    = "\u001B[1;33m"
    static final String	LIGHT_BLUE      = "\u001B[1;34m"
    static final String	LIGHT_PURPLE    = "\u001B[1;35m"
    static final String	LIGHT_CYAN      = "\u001B[1;36m"
    static final String	LIGHT_GRAY      = "\u001B[1;37m"

    static String color(String text, String ansiValue) {
        String restoreAnsi
        switch (ansiValue) {
            case BOLD:              restoreAnsi = RESET; break         //reset bold n funciona em todos lugares
            case ITALIC:            restoreAnsi = RESET_ITALIC; break
            case UNDERLINE:         restoreAnsi = RESET_UNDERLINE; break
            case BLINK:             restoreAnsi = RESET_BLINK; break
            case RAPID_BLINK:       restoreAnsi = RESET_RAPID_BLINK; break
            case REVERSE_VIDEO:     restoreAnsi = RESET_REVERSE_VIDEO; break
            case INVISIBLE_TEXT:    restoreAnsi = RESET_INVISIBLE_TEXT; break
            default:                restoreAnsi = DEFAULT
        }
        ansiValue + text + restoreAnsi
    }

    //acho q ta bugado ainda
    static String color(String text, List<String> ansiValues) {
        if(ansiValues.size()==1)
            return color(text, ansiValues[0])

        if(ansiValues.size()>1)
            return  color( color(text, ansiValues[0]), ansiValues.subList(1, ansiValues.size()) )
    }

	//método inutil, porém seu nome facilita a compreensão de como usar (?)
	//-> ver se essa desculpa é válida mesmo ou só balela
	static String set(String ansiValue){
		return ansiValue
	}
}
