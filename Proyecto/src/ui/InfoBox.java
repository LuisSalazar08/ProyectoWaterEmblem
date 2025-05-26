package ui;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
        g2.drawString(u.getNombre()+ " " + "(" + u.getTipoUnidad() + ")", textX, textY);

        textY += lineHeight;
        g2.drawString("HP: " + u.getStats().getHP() + "/" + u.getStats().getMAXHP(), textX, textY);

        textY += lineHeight;
        g2.drawString("Fuerza: " + u.getStats().getSTR(), textX, textY); 
        
     //sprite ampliado
        int spriteSize = 96; 
        int spriteX = x + width + 10; 
        int spriteY = y; 

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(spriteX, spriteY, spriteSize, spriteSize);

        g2.setColor(Color.WHITE);
        g2.drawRect(spriteX, spriteY, spriteSize, spriteSize);

        BufferedImage sprite = u.getIdleFrameActual(); 
        if (sprite != null) {
            g2.drawImage(sprite, spriteX, spriteY, spriteSize, spriteSize, null);
        
        }
    }
}