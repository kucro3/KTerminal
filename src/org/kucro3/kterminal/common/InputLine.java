package org.kucro3.kterminal.common;

import org.kucro3.kterminal.rendering.Line;
import org.kucro3.kterminal.rendering.Renderer;
import org.kucro3.kterminal.rendering.Text;
import org.kucro3.kterminal.util.ClipboardUtil;
import org.kucro3.kterminal.util.Color;
import org.kucro3.kterminal.util.Keyboard;

public class InputLine implements Component {
    @Override
    public void unload(Renderer renderer)
    {
        this.line.reset();
        this.line = null;
        this.renderer = null;
    }

    @Override
    public void load(Renderer renderer)
    {
        renderer.ensureLines(1);

        this.renderer = renderer;
        this.line = renderer.getLine(0);
    }

    @Override
    public void handle(int ansi)
    {
        if(line == null)
            return;

        if(!focused)
            return;

        Keyboard key = Keyboard.getKey(ansi);

        if(flag)
            switch(key)
            {
                case WORD_K: // left
                    if(cursor > 0)
                        cursor--;
                    flag = false;
                    break;

                case WORD_M: // right
                    if(cursor < buffer.length())
                        cursor++;
                    flag = false;
                    break;

                default:
                    flag = false;
                    return;
            }
        else if(key.isCharacter())
            buffer.insert(cursor++, key.getCharacter());
        else if(key.hasExt())
        {
            flag = true;
            return;
        }
        else switch(key)
        {
            case CTRL_X:
                ClipboardUtil.putString(buffer.toString());
                buffer.delete(0, buffer.length());
                cursor = 0;
                break;

            case CTRL_C:
                ClipboardUtil.putString(buffer.toString());
                break;

            case CTRL_V:
                String s0 = ClipboardUtil.getString();
                if(s0 != null)
                {
                    buffer.delete(0, buffer.length()).append(s0);
                    cursor = s0.length();
                }
                break;

            case CTRL_H__BACKSPACE:
                buffer.deleteCharAt(--cursor);
                break;

            default:
                return;
        }

        updateLine();
    }

    void updateLine()
    {
        String s0, s1, s2;
        s0 = buffer.substring(0, cursor);

        if(cursor == buffer.length())
        {
            s1 = " ";
            s2 = "";
        }
        else
        {
            s1 = buffer.charAt(cursor) + "";

            if(cursor < buffer.length())
                s2 = buffer.substring(cursor + 1, buffer.length());
            else
                s2 = "";
        }

        line.setText(Text
                .of(s0)
                .append(Text
                        .of(s1)
                        .setBgColor(Color.WHITE)
                        .setFgColor(Color.BLACK)
                        .append(Text.of(s2))));

        renderer.update();
    }

    @Override
    public boolean focused()
    {
        return focused;
    }

    @Override
    public void focus()
    {
        focused = true;
    }

    @Override
    public void unfocus()
    {
        focused = false;
    }

    public int getCursor()
    {
        return cursor;
    }

    public String getText()
    {
        return buffer.toString();
    }

    public void setText(String text)
    {
        this.buffer.delete(0, buffer.length());
        this.buffer.append(text);
        this.cursor = text.length();

        this.updateLine();
    }

    protected Renderer renderer;

    boolean flag = false;

    private volatile boolean focused = true;

    protected StringBuilder buffer = new StringBuilder();

    protected int cursor;

    protected Line line;
}
