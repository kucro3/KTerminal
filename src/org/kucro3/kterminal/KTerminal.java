package org.kucro3.kterminal;

import java.util.Objects;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.Ansi.Erase;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.internal.WindowsSupport;
import org.kucro3.kterminal.KTerminalThread.KTerminalInputThread;
import org.kucro3.kterminal.KTerminalThread.KTerminalOutputThread;

public final class KTerminal {
	private KTerminal()
	{
	}
	
	public static void systemInstall()
	{
		AnsiConsole.systemInstall();
	}
	
	public static void systemUninstall()
	{
		AnsiConsole.systemUninstall();
	}
	
	public static synchronized void startOutput()
	{
		if(output != null && output.isAlive())
			throw new IllegalStateException("Already started");
		else
			(output = new KTerminalOutputThread()).start();
	}
	
	public static synchronized void terminateOutput()
	{
		if(output != null && output.isAlive())
			output.interrupt();
		else
			throw new IllegalStateException("Already dead");
	}
	
	public static synchronized void startInput()
	{
		if(input != null && input.isAlive())
			throw new IllegalStateException("Already started");
		else
			(input = new KTerminalInputThread()).start();
	}
	
	public static synchronized void terminateInput()
	{
		if(input != null && input.isAlive())
			input.interrupt();
		else
			throw new IllegalStateException("Already dead");
	}
	
	public static void cleanScreen()
	{
		cleanScreen(Color.DEFAULT);
	}
	
	public static void cleanScreen(Color bg)
	{
		Ansi ansi = Ansi.ansi();
		ansi.eraseScreen(Erase.ALL).cursor(0, 0);
		int a = WindowsSupport.getWindowsTerminalHeight(),
			b = WindowsSupport.getWindowsTerminalWidth();
		String line = emptyLine(b);
		for(int j = 0; j < a - 1; j++)
			ansi.bg(bg).a(line).newline();
		ansi.bg(bg).a(line.substring(1));
		System.out.print(ansi.eraseScreen(Erase.ALL).cursor(0, 0).reset());
	}
	
	public static String emptyLine(int width)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < width - 1; i++)
			builder.append(" ");
		return builder.toString();
	}
	
	public static Screen getCurrentScreen()
	{
		return screen;
	}
	
	public static void setCurrentScreen(Screen screen)
	{
		KTerminal.screen = Objects.requireNonNull(screen);
	}
	
	public static int getWindowsHeight()
	{
		return WindowsSupport.getWindowsTerminalHeight();
	}
	
	public static int getWindowsWidth()
	{
		return WindowsSupport.getWindowsTerminalWidth();
	}
	
	public static void setUpdateDelay(long delay)
	{
		if(delay < 0)
			throw new IllegalArgumentException("delay");
		updateDelay = delay;
	}
	
	public static long getUpdateDelay()
	{
		return updateDelay;
	}
	
	static volatile long updateDelay = 10;
	
	static KTerminalInputThread input;
	
	static KTerminalOutputThread output;
	
	private static volatile Screen screen;
}
