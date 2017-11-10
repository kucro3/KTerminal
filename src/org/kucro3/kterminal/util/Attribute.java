package org.kucro3.kterminal.util;

import org.fusesource.jansi.Ansi;

public enum Attribute {
	BLINK_FAST(Ansi.Attribute.BLINK_FAST),
	BLINK_OFF(Ansi.Attribute.BLINK_OFF),
	BLINK_SLOW(Ansi.Attribute.BLINK_SLOW),
	CONCEAL_OFF(Ansi.Attribute.CONCEAL_OFF),
	CONCEAL_ON(Ansi.Attribute.CONCEAL_ON),
	INTENSITY_BOLD(Ansi.Attribute.INTENSITY_BOLD),
	INTENSITY_BLOD_OFF(Ansi.Attribute.INTENSITY_BOLD_OFF),
	INTENSITY_FAINT(Ansi.Attribute.INTENSITY_FAINT),
	ITALIC(Ansi.Attribute.ITALIC),
	ITALIC_OFF(Ansi.Attribute.ITALIC_OFF),
	NEGATIVE_OFF(Ansi.Attribute.NEGATIVE_OFF),
	NEGATIVE_ON(Ansi.Attribute.NEGATIVE_ON),
	RESET(Ansi.Attribute.RESET),
	STRIKETHROUGH_OFF(Ansi.Attribute.STRIKETHROUGH_OFF),
	STRIKETHROUGH_ON(Ansi.Attribute.STRIKETHROUGH_ON),
	UNDERLINE(Ansi.Attribute.UNDERLINE),
	UNDERLINE_DOUBLE(Ansi.Attribute.UNDERLINE_DOUBLE),
	UNDERLINE_OFF(Ansi.Attribute.UNDERLINE_OFF),
	;
	
	private Attribute(Object obj)
	{
		this.handle = obj;
	}
	
	public final Object getHandle()
	{
		return handle;
	}
	
	private final Object handle;
}
