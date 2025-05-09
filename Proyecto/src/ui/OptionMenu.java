package ui;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Arrays;

public class OptionMenu {
    private int x, y;
    private int width, height;
    private List<String> options;
    private int selected = 0;
    private boolean visible = false;
    private Font font = new Font("Arial", Font.PLAIN, 16);
    private int lineHeight = 20;

    public OptionMenu(int width, int height, String... opts) {
        this.width = width;
        this.height = lineHeight * opts.length + 10;
        this.options = Arrays.asList(opts);
    }

    public void show(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public void hide() {
        this.visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void update(boolean upPressed, boolean downPressed, boolean enterPressed, boolean escPressed) {
    	if (!visible) return;

        if (upPressed) selected = (selected - 1 + options.size()) % options.size();
        if (downPressed) selected = (selected + 1) % options.size();

        if (enterPressed) {
            executeOption(options.get(selected));
            hide();
        }

        if (escPressed) {
            hide();
        }
        
    }

    private void executeOption(String opt) {
        // segun la opcion se realiza una accion
        System.out.println("Opción elegida: " + opt);
    }

    public void draw(Graphics2D g2) {
        if (!visible) return;
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 10, 10);
        g2.setFont(font);
        int textX = x + 10;
        int textY = y + lineHeight;
        for (int i = 0; i < options.size(); i++) {
            if (i == selected) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(options.get(i), textX, textY + i * lineHeight);
        }
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
