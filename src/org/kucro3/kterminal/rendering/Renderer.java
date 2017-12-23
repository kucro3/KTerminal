package org.kucro3.kterminal.rendering;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.internal.WindowsSupport;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Supplier;

public class Renderer {
	public Renderer()
	{
		this(null, () -> WindowsSupport.getWindowsTerminalWidth() - 1, WindowsSupport::getWindowsTerminalHeight);
	}
	
	public Renderer(int width, int height)
	{
		this(null, () -> width, () -> height);
	}

	public Renderer(Supplier<Integer> width, Supplier<Integer> height)
	{
		this(null, width, height);
	}

	Renderer(Renderer upper, Supplier<Integer> height)
	{
		this(upper, () -> WindowsSupport.getWindowsTerminalWidth() - 1, height);
	}

	Renderer(Renderer upper, Supplier<Integer> width, Supplier<Integer> height)
	{
		this.upper = upper;
		this.width = width;
		this.height = height;
		this.shades = new ArrayList<>();
	}
	
	public void addLine(Line line)
	{
		lines.add(line);
	}

	public void ensureLines(int count)
	{
		for(int i = lines.size(); i <= count; i++)
			lines.add(new Line());
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

	public Supplier<Integer> getWidthSupplier()
	{
		return width;
	}

	public Supplier<Integer> getHeightSupplier()
	{
		return height;
	}

	public int getWidth()
	{
		return width.get();
	}
	
	public int getHeight()
	{
		return height.get();
	}
	
	public Renderer addShade(int startHeight)
	{
		final RendererRef ref = new RendererRef();
		Renderer window = (ref.window = new Renderer(this, width, () -> ref.window.totalLines()));
		window.startHeight = () -> startHeight;
		shades.add(window);
		return window;
	}
	
	public Renderer addShade(int startHeight, int height)
	{
		return addShade(startHeight, () -> height);
	}
	
	public Renderer addShade(int startHeight, int height, int startWidth)
	{
		return addShade(startHeight, () -> height, startWidth);
	}
	
	public Renderer addShade(int startHeight, Supplier<Integer> height, int startWidth)
	{
		Renderer window = addShade(startHeight, height);
		window.startWidth = () -> startWidth;
		return window;
	}
	
	public Renderer addShade(int startHeight, int height, int startWidth, int width)
	{
		return addShade(startHeight, () -> height, startWidth, () -> width);
	}
	
	public Renderer addShade(int startHeight, Supplier<Integer> height, int startWidth, Supplier<Integer> width)
	{
		return addShade(() -> startHeight, height, () -> startWidth, width);
	}
	
	public Renderer addShade(Supplier<Integer> startHeight, Supplier<Integer> height, Supplier<Integer> startWidth, Supplier<Integer> width)
	{
		Renderer window = new Renderer(this, width, height);
		window.startWidth = startWidth;
		window.startHeight = startHeight;
		shades.add(window);
		return window;
	}
	
	public Renderer addShade(int startHeight, Supplier<Integer> height)
	{
		return addShade(() -> startHeight, height);
	}
	
	public Renderer addShade(Supplier<Integer> startHeight, Supplier<Integer> height)
	{
		Renderer window = new Renderer(this, width, height);
		window.startHeight = Objects.requireNonNull(startHeight);
		shades.add(window);
		return window;
	}
	
	public boolean removeShade(Renderer window)
	{
		return shades.remove(window);
	}
	
	public boolean swapShade(Renderer shade0, Renderer shade1)
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

	public void setUpdateCallback(UpdateCallback callback)
	{
		this.callback = callback;
	}

	public void update()
	{
		if(this.callback != null)
			callback.callback();
	}
	
	public void display(PrintStream os)
	{
		if(upper != null)
			upper.display(os);
		else
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
		for(Renderer shade : shades)
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

	protected UpdateCallback callback;
	
	protected Supplier<Integer> startWidth = () -> 0;
	
	protected Supplier<Integer> startHeight = () -> 0;
	
	private final ArrayList<Renderer> shades;
	
	private final Supplier<Integer> width;
	
	private final Supplier<Integer> height;
	
	int pointer;
	
	private final LinkedList<Line> lines = new LinkedList<>();

	private final Renderer upper;
	
	private static class RendererRef
	{
		Renderer window;
	}

	public interface UpdateCallback
	{
		public void callback();
	}
}
