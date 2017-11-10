package org.kucro3.kterminal;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.internal.Kernel32;

public class KTerminalThread {
	private KTerminalThread()
	{
	}
	
	static class KTerminalOutputThread extends Thread {
		KTerminalOutputThread()
		{
			super("KTerminal-Output");
		}
		
		@Override
		public void run()
		{
			while(!interrupted && KTerminal.getCurrentScreen().display())
			{
				try {
					Thread.sleep(KTerminal.getUpdateDelay());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				System.out.print(KTerminal.currentCleanScreen(ansi));
			}
		}
		
		@Override
		public void interrupt()
		{
			this.interrupted = true;
			super.interrupt();
		}
		
		final Ansi ansi = Ansi.ansi();
		
		volatile boolean interrupted = false;
	}
	
	static class KTerminalInputThread extends Thread
	{
		KTerminalInputThread()
		{
			super("KTerminal-Input");
		}
		
		@Override
		public void run()
		{
			while(!interrupted)
			{
				int ansi = Kernel32._getch();
				KTerminal.getCurrentScreen().handle(ansi);
			}
		}
		
		@Override
		public void interrupt()
		{
			this.interrupted = true;
			super.interrupt();
		}
		
		volatile boolean interrupted;
	}
}
