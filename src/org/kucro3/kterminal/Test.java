package org.kucro3.kterminal;

import org.fusesource.jansi.Ansi;
import org.kucro3.kterminal.util.*;

public class Test {
	public static void main0(String[] args) throws Exception
	{
		System.out.println(Text.of("SHADE").subtext(0, 5));
	}
	
	public static void main(String[] args) throws Exception
	{
//		KTerminal.setCurrentScreen(new TestScreen());
		KTerminal.systemInstall();
//		KTerminal.startInput();
//		KTerminal.startOutput();
		Window window = new Window();
		window.addLine(new Line().setText(Text.of("Test for line 0                                ").setFgColor(Color.RED).setBgColor(Color.WHITE)));
		window.addLine(new Line().setText(Text.of("Test for line 1                                ").setFgColor(Color.GREEN).setBgColor(Color.WHITE)));
		window.addLine(new Line().setText(Text.of("Test for line 2                                ").setFgColor(Color.BLUE).setBgColor(Color.WHITE)));
		
		Window shade = window.addShade(-1, 1, 6, 5);
		shade.addLine(new Line().setText(Text.of("SHADE")));
		
		while(true)
		{
			window.display(System.out);
//			System.out.println(line.toString());
			Thread.sleep(500);
			KTerminal.cleanScreen();
		}
	}
	
	static class TestScreen extends AbstractScreen
	{
		@Override
		public boolean display() 
		{
//			if(!changed)
//				return true;
			
			Line line = new Line();
			line.setText(Text.of("123456789123456789123456789123456789"));
			
			int l = KTerminal.getWindowsWidth();
			int h = KTerminal.getWindowsHeight();
			String el = KTerminal.emptyLine(l);
			Ansi ansi = Ansi.ansi();
			KTerminal.cleanScreen();
			ansi.a(line).newline();
			for(int i = 1; i < h - 1; i++)
				ansi.a(el).newline();
			ansi.a(el);
			ansi.cursor(0, 0);
			System.out.print(ansi.toString());
			ansi.reset();
			
			changed = false;
			
			return true;
		}

		@Override
		public void handle(int ansi) 
		{
			changed = true;
			buff = "" + Character.isAlphabetic(ansi);
		}
		
		volatile boolean changed;
		
		String buff = "";
	}
}
