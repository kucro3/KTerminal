package org.kucro3.kterminal.rendering;

import org.kucro3.kterminal.KTerminal;

import java.util.Objects;
import java.util.function.Supplier;

public class Line {
	public Line()
	{
		this(() -> KTerminal.getWindowsWidth());
	}
	
	public Line(int width)
	{
		this(() -> width);
	}
	
	Line(Supplier<Integer> width)
	{
		this.width = width;
	}
	
	public Line reset()
	{
		this.text = Text.empty();
		return this;
	}

	public Line setText(Text text)
	{
		this.text = Objects.requireNonNull(text);
		return this;
	}

	public Text getText()
	{
		return this.text;
	}

	public Line copy()
	{
		return new Line(width).setText(text);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		int totalLength = 0;
		int width = this.width.get();
		Text text = this.text;

		if(width > 0)
			do {
				int nextTotal = totalLength + text.contentLength();
				int delta = width - nextTotal;
				if(delta == 0)
				{
					sb.append(text.getString());
					return sb.toString();
				}
				else if(delta < 0)
				{
					sb.append(text.substring(0, text.contentLength() + delta).getString());
					return sb.toString();
				}
				else
				{
					totalLength += text.contentLength();
					sb.append(text.getString());
				}
			} while((text = text.next().orElse(null)) != null);

		return sb.toString();
	}

	Text text = Text.empty();
	
	private final Supplier<Integer> width;
}
