package org.kucro3.kterminal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractScreen implements Screen {
	@Override
	public synchronized Optional<Screen> getParent() 
	{
		return Optional.ofNullable(parent);
	}

	@Override
	public synchronized void setParent(Screen parent) 
	{
		this.parent = parent;
	}

	@Override
	public Map<String, Screen> getChildren() 
	{
		return Collections.unmodifiableMap(new HashMap<>(children));
	}

	@Override
	public Optional<Screen> getChild(String id) 
	{
		return Optional.ofNullable(children.get(id));
	}

	@Override
	public void addChild(String id, Screen screen) 
	{
		children.put(id, screen);
	}

	@Override
	public boolean removeChild(String id) 
	{
		return children.remove(id) != null;
	}

	@Override
	public void clearChildren() 
	{
		children.clear();
	}
	
	protected Screen parent;
	
	protected Map<String, Screen> children = new ConcurrentHashMap<>();
}
