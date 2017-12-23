package org.kucro3.kterminal.util;

public enum Keyboard {
	FUNCTION_0(0, true),
	CTRL_A(1),
	CTRL_B(2),
	CTRL_C(3),
	CTRL_D(4),
	CTRL_E(5),
	CTRL_F(6),
	CTRL_G(7),
	CTRL_H__BACKSPACE(8),
	CTRL_I__TAB(9),
	CTRL_J(10),
	CTRL_K(11),
	CTRL_L(12),
	CTRL_M__ENTER(13),
	CTRL_N(14),
	CTRL_O(15),
	CTRL_P(16),
	CTRL_Q(17),
	CTRL_R(18),
	CTRL_S(19),
	CTRL_T(20),
	CTRL_U(21),
	CTRL_V(22),
	CTRL_W(23),
	CTRL_X(24),
	CTRL_Y(25),
	CTRL_Z(26),
	ESC(27),

	CAESURA(96, '`'),
	TILDE(126, '~'),
	EXCLAMATION_MARK(33, '!'),
	AT(64, '@'),
	POUND_SIGN(35, '#'),
	DOLLAR_SIGN(36, '$'),
	PERCENT(37, '%'),
	CARET(94, '^'),
	AND(38, '&'),
	STAR(42, '*'),
	LEFT_BRACKET(40, '('),
	RIGHT_BRACKET(41, ')'),
	HYPHEN(45, '-'),
	UNDERLINE(95, '_'),
	PLUS(43, '+'),
	EQUALS(61, '='),
	OBRACE(123, '{'),
	EBRACE(125, '}'),
	LEFT_SQUARE_BRACKET(91, '['),
	RIGHT_SQUARE_BRACKET(93, ']'),
	PIPE(124, '|'),
	BACKSLASH(92,'\\'),
	COLON(58, ':'),
	SEMICOLON(59, ';'),
	DOUBLE_QUOTE(34, '"'),
	SINGLE_QUOTE(39, '\''),
	COMMA(44, ','),
	DOT(46, '.'),
	SLASH(47, '/'),
	QUESTION_MARK(63, '?'),
	LESS_THAN(60, '<'),
	GREATER_THAN(62, '>'),

	SPACE(32, ' '),
	
	NUM_0(48, '0'),
	NUM_1(49, '1'),
	NUM_2(50, '2'),
	NUM_3(51, '3'),
	NUM_4(52, '4'),
	NUM_5(53, '5'),
	NUM_6(54, '6'),
	NUM_7(55, '7'),
	NUM_8(56, '8'),
	NUM_9(57, '9'),
	
	WORD_A(65, 'A'),
	WORD_B(66, 'B'),
	WORD_C(67, 'C'),
	WORD_D(68, 'D'),
	WORD_E(69, 'E'),
	WORD_F(70, 'F'),
	WORD_G(71, 'G'),
	WORD_H(72, 'H'),
	WORD_I(73, 'I'),
	WORD_J(74, 'J'),
	WORD_K(75, 'K'),
	WORD_L(76, 'L'),
	WORD_M(77, 'M'),
	WORD_N(78, 'N'),
	WORD_O(79, 'O'),
	WORD_P(80, 'P'),
	WORD_Q(81, 'Q'),
	WORD_R(82, 'R'),
	WORD_S(83, 'S'),
	WORD_T(84, 'T'),
	WORD_U(85, 'U'),
	WORD_V(86, 'V'),
	WORD_W(87, 'W'),
	WORD_X(88, 'X'),
	WORD_Y(89, 'Y'),
	WORD_Z(90, 'Z'),
	
	
	WORD_a(97, 'a'),
	WORD_b(98, 'b'),
	WORD_c(99, 'c'),
	WORD_d(100, 'd'),
	WORD_e(101, 'e'),
	WORD_f(102, 'f'),
	WORD_g(103, 'g'),
	WORD_h(104, 'h'),
	WORD_i(105, 'i'),
	WORD_j(106, 'j'),
	WORD_k(107, 'k'),
	WORD_l(108, 'l'),
	WORD_m(109, 'm'),
	WORD_n(110, 'n'),
	WORD_o(111, 'o'),
	WORD_p(112, 'p'),
	WORD_q(113, 'q'),
	WORD_r(114, 'r'),
	WORD_s(115, 's'),
	WORD_t(116, 't'),
	WORD_u(117, 'u'),
	WORD_v(118, 'v'),
	WORD_w(119, 'w'),
	WORD_x(120, 'x'),
	WORD_y(121, 'y'),
	WORD_z(122, 'z'),
	
	FUNCTION_224(224, true),
	
	// 0
	EXT_CTRL_AT(FUNCTION_0, 3),
	EXT_CTRL_ALT_BACKSPACE(FUNCTION_0, 14),
	EXT_CTRL_ALT_Q(FUNCTION_0, 16),
	EXT_CTRL_ALT_W(FUNCTION_0, 17),
	EXT_CTRL_ALT_E(FUNCTION_0, 18),
	EXT_CTRL_ALT_R(FUNCTION_0, 19),
	EXT_CTRL_ALT_T(FUNCTION_0, 20),
	EXT_CTRL_ALT_Y(FUNCTION_0, 21),
	EXT_CTRL_ALT_U(FUNCTION_0, 22),
	EXT_CTRL_ALT_I(FUNCTION_0, 23),
	EXT_CTRL_ALT_O(FUNCTION_0, 24),
	EXT_CTRL_ALT_P(FUNCTION_0, 25),
	EXT_CTRL_ALT_LEFT_BRACKET(FUNCTION_0, 26),
	EXT_CTRL_ALT_RIGHT_BRACKET(FUNCTION_0, 27),
	EXT_CTRL_ALT_ENTER(FUNCTION_0, 28),
	EXT_CTRL_ALT_A(FUNCTION_0, 30),
	EXT_CTRL_ALT_S(FUNCTION_0, 31),
	EXT_CTRL_ALT_D(FUNCTION_0, 32),
	EXT_CTRL_ALT_F(FUNCTION_0, 33),
	EXT_CTRL_ALT_G(FUNCTION_0, 34),
	EXT_CTRL_ALT_H(FUNCTION_0, 35),
	EXT_CTRL_ALT_J(FUNCTION_0, 36),
	EXT_CTRL_ALT_K(FUNCTION_0, 37),
	EXT_CTRL_ALT_L(FUNCTION_0, 38),
	EXT_CTRL_ALT_WAVE(FUNCTION_0, 41),
	EXT_CTRL_ALT_Z(FUNCTION_0, 44),
	EXT_CTRL_ALT_X(FUNCTION_0, 45),
	EXT_CTRL_ALT_C(FUNCTION_0, 46),
	EXT_CTRL_ALT_V(FUNCTION_0, 47),
	EXT_CTRL_ALT_B(FUNCTION_0, 48),
	EXT_CTRL_ALT_N(FUNCTION_0, 49),
	EXT_CTRL_ALT_M(FUNCTION_0, 50),
	EXT_F1(FUNCTION_0, 59),
	EXT_F2(FUNCTION_0, 60),
	EXT_F3(FUNCTION_0, 61),
	EXT_F4(FUNCTION_0, 62),
	EXT_F5(FUNCTION_0, 63),
	EXT_F6(FUNCTION_0, 64),
	EXT_F7(FUNCTION_0, 65),
	EXT_F8(FUNCTION_0, 66),
	EXT_F9(FUNCTION_0, 67),
	EXT_F10(FUNCTION_0, 68),
	EXT_CTRL_F1(FUNCTION_0, 94),
	EXT_CTRL_F2(FUNCTION_0, 95),
	EXT_CTRL_F3(FUNCTION_0, 96),
	EXT_CTRL_F4(FUNCTION_0, 97),
	EXT_CTRL_F5(FUNCTION_0, 98),
	EXT_CTRL_F6(FUNCTION_0, 99),
	EXT_CTRL_F7(FUNCTION_0, 100),
	EXT_CTRL_F8(FUNCTION_0, 101),
	EXT_CTRL_F9(FUNCTION_0, 102),
	EXT_CTRL_F10(FUNCTION_0, 103),
	EXT_CTRL_ALT_F1(FUNCTION_0, 104),
	EXT_CTRL_ALT_F2(FUNCTION_0, 105),
	EXT_CTRL_ALT_F3(FUNCTION_0, 106),
	EXT_CTRL_ALT_F4(FUNCTION_0, 107),
	EXT_CTRL_ALT_F5(FUNCTION_0, 108),
	EXT_CTRL_ALT_F6(FUNCTION_0, 109),
	EXT_CTRL_ALT_F7(FUNCTION_0, 110),
	EXT_CTRL_ALT_F8(FUNCTION_0, 111),
	EXT_CTRL_ALT_F9(FUNCTION_0, 112),
	EXT_CTRL_ALT_F10(FUNCTION_0, 113),
	EXT_CTRL_ALT_1(FUNCTION_0, 120),
	EXT_CTRL_ALT_2(FUNCTION_0, 121),
	EXT_CTRL_ALT_3(FUNCTION_0, 122),
	EXT_CTRL_ALT_4(FUNCTION_0, 123),
	EXT_CTRL_ALT_5(FUNCTION_0, 124),
	EXT_CTRL_ALT_6(FUNCTION_0, 125),
	EXT_CTRL_ALT_7(FUNCTION_0, 126),
	EXT_CTRL_ALT_8(FUNCTION_0, 127),
	EXT_CTRL_ALT_9(FUNCTION_0, 128),
	EXT_CTRL_ALT_0(FUNCTION_0, 129),
	EXT_CTRL_TAB(FUNCTION_0, 148),
	EXT_CTRL_ALT_HOME(FUNCTION_0, 151),
	EXT_CTRL_ALT_UP(FUNCTION_0, 152),
	EXT_CTRL_ALT_PAGE_UP(FUNCTION_0, 153),
	EXT_CTRL_ALT_LEFT(FUNCTION_0, 155),
	EXT_CTRL_ALT_RIGHT(FUNCTION_0, 157),
	EXT_CTRL_ALT_END(FUNCTION_0, 159),
	EXT_CTRL_ALT_DOWN(FUNCTION_0, 160),
	EXT_CTRL_ALT_PAGE_DOWN(FUNCTION_0, 161),
	EXT_CTRL_ALT_INSERT(FUNCTION_0, 162),
	
	// 224
	EXT_HOME(FUNCTION_224, 71),
	EXT_UP(FUNCTION_224, 72),
	EXT_PAGE_UP(FUNCTION_224, 73),
	EXT_LEFT(FUNCTION_224, 75),
	EXT_RIGHT(FUNCTION_224, 77),
	EXT_END(FUNCTION_224, 79),
	EXT_DOWN(FUNCTION_224, 80),
	EXT_PAGE_DOWN(FUNCTION_224, 81),
	EXT_INSERT(FUNCTION_224, 82),
	EXT_DELETE(FUNCTION_224, 83),
	EXT_CTRL_LEFT(FUNCTION_224, 115),
	EXT_CTRL_RIGHT(FUNCTION_224, 116),
	EXT_CTRL_END(FUNCTION_224, 117),
	EXT_CTRL_PAGE_DOWN(FUNCTION_224, 118),
	EXT_CTRL_HOME(FUNCTION_224, 119),
	EXT_F11(FUNCTION_224, 133),
	EXT_F12_OR_CTRL_HOME(FUNCTION_224, 134),
	EXT_CTRL_F11(FUNCTION_224, 137),
	EXT_CTRL_F12(FUNCTION_224, 138),
	EXT_CTRL_ALT_F11(FUNCTION_224, 139),
	EXT_CTRL_ALT_F12(FUNCTION_224, 140),
	EXT_CTRL_UP(FUNCTION_224, 141),
	EXT_CTRL_DOWN(FUNCTION_224, 145),
	EXT_CTRL_INSERT(FUNCTION_224, 146),
	EXT_CTRL_DELETE(FUNCTION_224, 147),
	;
	
	private Keyboard(int code)
	{
		this(code, false);
	}
	
	private Keyboard(int code, boolean extended)
	{
		this(code, null, -1, extended);
	}

	private Keyboard(int code, char character)
	{
		this(code);
		this.isCharacter = true;
		this.character = character;
	}

	private Keyboard(Keyboard root, int extension)
	{
		this(-1, root, extension, false);
	}

	private Keyboard(int code, Keyboard root, int extension, boolean extended)
	{
		this.code = code;
		this.extended = extended;
		this.extension = extension;
		if(extended)
			this.extensions = new KeyCollection<>(Keyboard.class);
		else
			this.extensions = null;
		
		if(code != -1)
			collection().put(code, this);
		
		if(extension())
			root.extensions.put(extension, this);
	}

	public final boolean isCharacter()
	{
		return isCharacter;
	}

	public final char getCharacter()
	{
		return character;
	}
	
	public final boolean hasExt()
	{
		return extended;
	}
	
	public final Keyboard ext(int code)
	{
		return extensions.get(code);
	}
	
	public final int code()
	{
		if(code != -1)
			return code;
		return extension;
	}
	
	public final boolean extension()
	{
		return extension != -1;
	}
	
	public static Keyboard getKey(int code)
	{
		return collection().get(code);
	}
	
	public static Keyboard getKey(int code, int ext)
	{
		Keyboard key = getKey(code);
		if(key == null)
			return null;
		else if(key.extension())
			return key.ext(ext);
		else
			return key;
	}
	
	private static final KeyCollection<Keyboard> collection()
	{
		if(a == null)
			a = new KeyCollection<Keyboard>(Keyboard.class);
		return a;
	}
	
	public static void touch()
	{
		// For initializing static block
	}

	private boolean isCharacter;

	private char character;

	private static KeyCollection<Keyboard> a;
	
	private final KeyCollection<Keyboard> extensions;
	
	private final int extension;
	
	private final boolean extended;
	
	private final int code;

	static final class KeyCollection<T>
	{
		public KeyCollection(Class<?> owner)
		{
			this(owner, 256);
		}
		
		public KeyCollection(Class<?> owner, int size)
		{
			this.owner = owner;
			this.elements = new Object[size];
		}
		
		public Class<?> getEnum()
		{
			return owner;
		}
		
		public void put(int code, T obj)
		{
			// range unchecked.
			elements[code] = obj;
		}
		
		@SuppressWarnings("unchecked")
		public T get(int code)
		{
			// range unchecked.
			return (T)elements[code];
		}
		
		private final Object[] elements;
		
		private final Class<?> owner;
	}
}
