package org.kucro3.kterminal.common;

import org.kucro3.kterminal.rendering.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Layer {
    public Layer()
    {
        this(new Renderer());
    }

    public Layer(Renderer renderer)
    {
        this.renderer = renderer;
    }

    public Component getComponent()
    {
        return component;
    }

    public void setComponent(Component component)
    {
        if(this.component != null)
            this.component.unload(renderer);

        this.component = component;

        if(component != null)
            component.load(renderer);
        else
            renderer.clearLines();
    }

    public Renderer getRenderer()
    {
        return renderer;
    }

    public Layer cover()
    {
        return append(new Layer(renderer.addShade(0, renderer.getHeightSupplier(), 0, renderer.getWidthSupplier())));
    }

    public Layer cover(int startingHeight, int startingWidth)
    {
        return append(new Layer(renderer.addShade(startingHeight, renderer.getHeightSupplier(), startingWidth, renderer.getWidthSupplier())));
    }

    public Layer cover(Supplier<Integer> startingHeight, Supplier<Integer> startingWidth)
    {
        return append(new Layer(renderer.addShade(startingHeight, renderer.getHeightSupplier(), startingWidth, renderer.getWidthSupplier())));
    }

    Layer append(Layer layer)
    {
        layers.add(layer);
        return layer;
    }

    protected final List<Layer> layers = new ArrayList<>();

    protected final Renderer renderer;

    protected Component component;
}
