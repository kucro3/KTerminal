package org.kucro3.kterminal.common;

import org.kucro3.kterminal.rendering.Renderer;

public interface Component {
    public void unload(Renderer renderer);

    public void load(Renderer renderer);

    public void handle(int ansi);

    public boolean focused();

    public void focus();

    public void unfocus();
}
