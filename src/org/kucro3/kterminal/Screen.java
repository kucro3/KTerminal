package org.kucro3.kterminal;

import java.util.Map;
import java.util.Optional;

public interface Screen {
	public Optional<Screen> getParent();
	
	public void setParent(Screen parent);
	
	public Map<String, Screen> getChildren();
	
	public Optional<Screen> getChild(String id);
	
	public void addChild(String id, Screen screen);
	
	public boolean removeChild(String id);
	
	public void clearChildren();
	
	public boolean display();
	
	public void handle(int ansi);
}
