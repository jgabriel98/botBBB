package utils.logging

/**
 * @see
 * <a href='http://ascii-table.com/ansi-escape-sequences-vt-100.php'>http://ascii-table.com/ansi-escape-sequences-vt-100.php</a>
 */
class AnsiCodes {
	static final String CLEAR_LINE_RIGHT = "\u001B[0K"
	static final String CLEAR_LINE_LEFT = "\u001B[1K"
	static final String CLEAR_LINE = "\u001B[2K"

	static final String CLEAR_SCREEN_DOWN = "\u001B[0K"
	static final String CLEAR_SCREEN_UP = "\u001B[1K"
	static final String CLEAR_SCREEN = "\u001B[2K"
}
