package org.kucro3.kterminal.util;

import org.fusesource.jansi.Ansi;

public enum Erase {
	ALL(Ansi.Erase.ALL),
	BACKWARD(Ansi.Erase.BACKWARD),
	FORWARD(Ansi.Erase.FORWARD);
	
	private Erase(Object handle)
	{
		this.handle = handle;
	}
	
	public final Object getHandle()
	{
		return handle;
	}
	
	private final Object handle;
}
