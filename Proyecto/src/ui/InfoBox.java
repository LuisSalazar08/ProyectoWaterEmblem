package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import entidad.Unidad;

public class InfoBox {
    private int x, y, width, height;
    
    public InfoBox(int x, int y, int w, int h) {
        this.x = x; this.y = y;
        this.width = w; this.height = h;
    }
    
    public void draw(Graphics2D g2, Unidad u) {
        if (u == null) return;
        
        // fondo 
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(x, y, width, height);

        // borde
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);
        
        // texto con atributos
        g2.setColor(Color.WHITE);
        int lineHeight = 20;
        int textX = x + 10;
        int textY = y + lineHeight;
        g2.drawString("Nombre: " + u.getNombre(), textX, textY);

        textY += lineHeight;
        g2.drawString("HP: " + u.getHp() + "/" + u.getMaxHp(), textX, textY);

        textY += lineHeight;
        g2.drawString("Fuerza: " + u.getFuerza(), textX, textY);

       
    }
}