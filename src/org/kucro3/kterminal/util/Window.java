package org.kucro3.kterminal.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.internal.WindowsSupport;

public class Window {
	public Window()
	{
		this(() -> WindowsSupport.getWindowsTerminalWidth() - 1, WindowsSupport::getWindowsTerminalHeight);
	}
	
	public Window(int width, int height)
	{
		this(() -> width, () -> height);
	}
	
	Window(Supplier<Integer> height)
	{
		this(() -> WindowsSupport.getWindowsTerminalWidth() - 1, height);
	}
	
	Window(Supplier<Integer> width, Supplier<Integer> height)
	{
		this.width = width;
		this.height = height;
		this.shades = new ArrayList<>();
	}
	
	public void addLine(Line line)
	{
		lines.add(line);
	}
	
	public void clearLines()
	{
		lines.clear();
	}
	
	public int totalLines()
	{
		return lines.size();
	}
	
	public int pointer()
	{
		return pointer;
	}
	
	public boolean scrollUp()
	{
		if(pointer == 0)
			return false;
		pointer--;
		return true;
	}
	
	public boolean scrollDown()
	{
		if(pointer + 1 < lines.size())
			;
		else
			return false;
		pointer++;
		return true;
	}
	
	public Line appendLine()
	{
		Line line = new Line(width);
		lines.addLast(line);
		return line;
	}
	
	public Line prependLine()
	{
		Line line = new Line(width);
		lines.addFirst(line);
		return line;
	}
	
	public Line appendLine(Text text)
	{
		return appendLine().setText(text);
	}
	
	public Line prependLine(Text text)
	{
		return prependLine().setText(text);
	}
	
	public boolean removeLine(Line line)
	{
		return lines.remove(line);
	}
	
	public void removeLine(int index)
	{
		if(index < 0)
			index = lines.size() + index;
		lines.remove(index);
	}
	
	public Line getLine(int index)
	{
		if(index < 0)
			index = lines.size() + index;
		return lines.get(index);
	}
	
	public Line addLine(int index)
	{
		if(index < 0)
			index = lines.size() + index;
		Line line = new Line(width);
		lines.add(index, line);
		return line;
	}
	
	public Line addLine(int index, Text text)
	{
		return addLine(index).setText(text);
	}
	
	public int getWidth()
	{
		return width.get();
	}
	
	public int getHeight()
	{
		return height.get();
	}
	
	public Window addShade(int startHeight)
	{
		final WindowRef ref = new WindowRef();
		Window window = (ref.window = new Window(width, () -> ref.window.totalLines()));
		window.startHeight = () -> startHeight;
		shades.add(window);
		return window;
	}
	
	public Window addShade(int startHeight, int height)
	{
		return addShade(startHeight, () -> height);
	}
	
	public Window addShade(int startHeight, int height, int startWidth)
	{
		return addShade(startHeight, () -> height, startWidth);
	}
	
	public Window addShade(int startHeight, Supplier<Integer> height, int startWidth)
	{
		Window window = addShade(startHeight, height);
		window.startWidth = () -> startWidth;
		return window;
	}
	
	public Window addShade(int startHeight, int height, int startWidth, int width)
	{
		return addShade(startHeight, () -> height, startWidth, () -> width);
	}
	
	public Window addShade(int startHeight, Supplier<Integer> height, int startWidth, Supplier<Integer> width)
	{
		return addShade(() -> startHeight, height, () -> startWidth, width);
	}
	
	public Window addShade(Supplier<Integer> startHeight, Supplier<Integer> height, Supplier<Integer> startWidth, Supplier<Integer> width)
	{
		Window window = new Window(width, height);
		window.startWidth = startWidth;
		window.startHeight = startHeight;
		shades.add(window);
		return window;
	}
	
	public Window addShade(int startHeight, Supplier<Integer> height)
	{
		return addShade(() -> startHeight, height);
	}
	
	public Window addShade(Supplier<Integer> startHeight, Supplier<Integer> height)
	{
		Window window = new Window(width, height);
		window.startHeight = Objects.requireNonNull(startHeight);
		shades.add(window);
		return window;
	}
	
	public boolean removeShade(Window window)
	{
		return shades.remove(window);
	}
	
	public boolean swapShade(Window shade0, Window shade1)
	{
		if(shade0 == shade1)
			return true;
		
		int index0, index1;
		
		if((index0 = shades.indexOf(shade0)) < 0)
			return false;
		
		if((index1 = shades.indexOf(shade1)) < 0)
			return false;
		
		shades.set(index1, shade0);
		shades.set(index0, shade1);
		
		return true;
	}
	
	public void display(PrintStream os)
	{
		os.print(render());
	}
	
	public int visibleLines()
	{
		return Math.min(height.get(), totalLines() - pointer);
	}
	
	public List<Line> renderLines()
	{
		int height = this.height.get();
		int width = this.width.get();
		Line[] buffer = new Line[height];
		
		for(int i = 0; i < buffer.length; i++)
			buffer[i] = new Line();
		
		// self
		int visible = visibleLines();
		Iterator<Line> iterator = lines.iterator();
		for(int i = 0; i < visible; i++)
			try {
				if(i < pointer)
					iterator.next();
				else
					buffer[i - pointer] = iterator.next().copy();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		
		// shades
		for(Window shade : shades)
		{
			List<Line> lines = shade.renderLines();
			int startHeight = shade.startHeight.get();
			int startWidth = shade.startWidth.get();
			
			if(startHeight < 0)
				startHeight = height + startHeight;
			
			if(startWidth < 0)
				startWidth = width + startWidth;
			
			if(startHeight < 0)
				throw new IndexOutOfBoundsException("Illegal startHeight");
			
			if(startWidth < 0)
				throw new IndexOutOfBoundsException("Illegal startWidth");
			
			if(startHeight < height && startWidth < width)
				;
			else
				continue;
			
			int visibleHeight = Math.min(height - startHeight, shade.getHeight());
			int visibleWidth = Math.min(width - startWidth, shade.getWidth());
			int endHeight = startHeight + visibleHeight;
			int endWidth = startWidth + visibleWidth;
			
			for(int i = startHeight, j = 0; i < endHeight; i++, j++)
				try {
					Line origin = buffer[i];
					
					Text originalText = origin.getText();
					Text line = lines.get(j).getText();
					
					int paddingLength = startWidth - originalText.contentLength();
					
					if(paddingLength < 0)
					{
						Text ending;
						if(endWidth < width && endWidth < originalText.contentLength())
							ending = originalText.subtext(endWidth);
						else
							ending = Text.empty();
						
						originalText = originalText.subtext(0, startWidth);
						
						if(line.contentLength() == visibleWidth)
							originalText = originalText.append(line);
						else if(line.contentLength() > visibleWidth)
							originalText = originalText.append(line.subtext(0, visibleWidth));
						else
						{
							char[] paddingBuffer = new char[visibleWidth - line.contentLength()];
							Arrays.fill(paddingBuffer, ' ');
							String padding = new String(paddingBuffer);
							
							originalText = originalText.append(line);
							originalText = originalText.append(Text.of(padding));
						}
						
						originalText = originalText.append(ending);
					}
					else
					{
						char[] paddingBuffer = new char[paddingLength];
						Arrays.fill(paddingBuffer, ' ');
						String padding = new String(paddingBuffer);
						
						originalText = originalText.append(Text.of(padding));
						originalText = originalText.append(line.subtext(0, visibleWidth));
					}
					
					origin.setText(originalText);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
		}
		
		return Arrays.asList(buffer);
	}
	
	public Object render()
	{
		StringBuilder sb = new StringBuilder();
		List<Line> lines = renderLines();
		int i = 0, s = lines.size() - 1;
		for(; i < s; i++)
			sb.append(lines.get(i).toString()).append(Ansi.ansi().newline().toString());
		sb.append(lines.get(i).toString());
		return sb.toString();
	}
	
	protected Supplier<Integer> startWidth = () -> 0;
	
	protected Supplier<Integer> startHeight = () -> 0;
	
	private final ArrayList<Window> shades;
	
	private final Supplier<Integer> width;
	
	private final Supplier<Integer> height;
	
	int pointer;
	
	private final LinkedList<Line> lines = new LinkedList<>();
	
	private static class WindowRef
	{
		Window window;
	}
}
