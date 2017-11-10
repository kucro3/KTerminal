package org.kucro3.kterminal.util;

import org.fusesource.jansi.Ansi;

public enum Color {
	BLACK(Ansi.Color.BLACK),
	BLUE(Ansi.Color.BLUE),
	CYAN(Ansi.Color.CYAN),
	DEFAULT(Ansi.Color.DEFAULT),
	GREEN(Ansi.Color.GREEN),
	MAGENTA(Ansi.Color.MAGENTA),
	RED(Ansi.Color.RED),
	WHITE(Ansi.Color.WHITE),
	YELLOW(Ansi.Color.YELLOW);
	
	private Color(Object handle)
	{
		this.handle = handle;
	}
	
	public final Object getHandle()
	{
		return handle;
	}
	
	private final Object handle;
}
