package org.kucro3.kterminal.util;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

import org.fusesource.jansi.Ansi;

public final class Text {	
	public static Text empty()
	{
		return new Text();
	}
	
	public static Text of()
	{
		return empty();
	}
	
	public static Text of(Object content)
	{
		return new Text(content.toString());
	}
	
	public static Text of(String format, Object... args)
	{
		return new Text(String.format(format, args));
	}
	
	public static Text of(Object content, Attribute attribute)
	{
		return new Text(content.toString(), attribute);
	}
	
	public static Text of(Object content, Color fgColor, Color bgColor)
	{
		return new Text(content.toString(), fgColor, bgColor);
	}
	
	private Text()
	{
		this("");
	}
	
	private Text(String content)
	{
		this(content, null, null, null);
	}
	
	private Text(String content, Attribute attribute)
	{
		this(content, attribute, null, null);
	}
	
	private Text(String content, Color fgColor, Color bgColor)
	{
		this(content, null, fgColor, bgColor);
	}
	
	private Text(String content, Attribute attribute, Color fgColor, Color bgColor)
	{
		this(content, attribute, fgColor, bgColor, null);
	}
	
	private Text(String content, Attribute attribute, Color fgColor, Color bgColor, Text next)
	{
		this.content = Objects.requireNonNull(content);
		this.attribute = attribute;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.next = next;
		
		this.single = single0();
		this.string = string0();
		this.length = length0();
	}
	
	public String getContent()
	{
		return content;
	}
	
	public Optional<Attribute> getAttribute()
	{
		return Optional.ofNullable(this.attribute);
	}
	
	public Optional<Color> getBgColor()
	{
		return Optional.ofNullable(this.bgColor);
	}
	
	public Optional<Color> getFgColor()
	{
		return Optional.ofNullable(this.fgColor);
	}
	
	public Optional<Text> next()
	{
		return Optional.ofNullable(this.next);
	}
	
	public Text substring(int beginIndex)
	{
		return setContent(getContent().substring(beginIndex));
	}
	
	public Text substring(int beginIndex, int endIndex)
	{
		return setContent(getContent().substring(beginIndex, endIndex));
	}
	
	public Text subtext(int beginIndex)
	{
		return subtext(beginIndex, length());
	}
	
	public Text subtext(int beginIndex, int endIndex)
	{
		if(beginIndex == endIndex)
			return Text.empty();
		
		if(beginIndex < 0 || endIndex < 0 || endIndex < beginIndex)
			throw new IllegalArgumentException();
		
		LinkedList<Text> link = new LinkedList<>();
		
		boolean inRegion = false;
		
		int offset = 0;
		Text text = this;
		do {
			if(inRegion)
				if((endIndex - offset) < text.contentLength())
				{
					inRegion = false;
					link.addLast(text.substring(0, endIndex - offset).setNext(null));
					break;
				}
				else
					link.addLast(text);
			else if((beginIndex - offset) < text.contentLength())
				if((endIndex - offset) <= text.contentLength())
					return text.substring(beginIndex - offset, endIndex - offset).setNext(null);
				else
				{
					inRegion = true;
					link.addLast(text.substring(beginIndex - offset));
				}
			
			offset += text.contentLength();
		} while((text = text.next) != null);
		
		if(inRegion || link.isEmpty())
			throw new IndexOutOfBoundsException();
		
		return fromLink(link);
	}
	
	static Text fromLink(LinkedList<Text> link)
	{
		if(link.isEmpty())
			throw new IllegalStateException("Should not reach here");
		
		Text temp = null, ret = null;
		while((temp = link.pollLast()) != null)
			ret = temp.setNext(ret);
		
		return ret;
	}
	
	public int length()
	{
		return this.length;
	}
	
	public Text concat(String content)
	{
		return setContent(getContent() + content);
	}
	
	public int contentLength()
	{
		return getContent().length();
	}
	
	public Text setContent(String content)
	{
		return new Text(content, attribute, fgColor, bgColor, next);
	}
	
	public Text setAttribute(Attribute attribute)
	{
		return new Text(content, attribute, fgColor, bgColor, next);
	}
	
	public Text setFgColor(Color fgColor)
	{
		return new Text(content, attribute, fgColor, bgColor, next);
	}
	
	public Text setBgColor(Color bgColor)
	{
		return new Text(content, attribute, fgColor, bgColor, next);
	}
	
	public Text setNext(Text next)
	{
		return new Text(content, attribute, fgColor, bgColor, next);
	}
	
	public Text append(Text text)
	{
		return insert(text, true);
	}
	
	public Text insertAfter(Text text)
	{
		return insert(text, false);
	}
	
	Text insert(Text text, boolean append)
	{
		Objects.requireNonNull(text);
		if(next == null)
			return new Text(content, attribute, fgColor, bgColor, text);
		
		LinkedList<Text> link = toLink();
		LinkedList<Text> next = text.toLink();
		
		if(append)
			while(!next.isEmpty())
				link.addLast(next.pollFirst());
		else
			while(!next.isEmpty())
				link.add(1, next.pollLast());
		
		return fromLink(link);
	}
	
	LinkedList<Text> toLink()
	{
		LinkedList<Text> link = new LinkedList<>();
		
		Text text = this;
		do {
			link.addLast(text);
		} while((text = text.next) != null);
		
		return link;
	}
	
	private final String single0()
	{
		final Ansi ansi = Ansi.ansi();
		
		getAttribute().ifPresent((attr) -> ansi.a(attr));
		getBgColor().ifPresent((color) -> ansi.bg((Ansi.Color) color.getHandle()));
		getFgColor().ifPresent((color) -> ansi.fg((Ansi.Color) color.getHandle()));
		
		ansi.a(content);
		
		ansi.a(Ansi.Attribute.RESET);
		ansi.bg(Ansi.Color.DEFAULT);
		ansi.fg(Ansi.Color.DEFAULT);
		
		return ansi.toString();
	}
	
	private final String string0()
	{
		StringBuilder sb = new StringBuilder();
		
		Text text = this;
		do {
			sb.append(text.getString());
		} while ((text = text.next) != null);
		
		return sb.toString();
	}
	
	private final int length0()
	{
		int length = 0;
		Text text  = this;
		do {
			length += text.contentLength();
		} while((text = text.next) != null);
		return length;
	}
	
	public String getString()
	{
		return this.single;
	}
	
	@Override
	public String toString()
	{
		return this.string;
	}
	
	private final int length;
	
	private final String single;
	
	private final String string;
	
	private final Text next;
	
	private final Color bgColor;
	
	private final Color fgColor;
	
	private final Attribute attribute;
	
	private final String content;
}
