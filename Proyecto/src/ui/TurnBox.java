package ui;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.TurnManager;

public class TurnBox 
{
	private int x, y, width, height;
    private final int screenWidth;
    private final int margin = 10;

    public TurnBox(int screenWidth, int w, int h) 
    {
        this.screenWidth = screenWidth;
        this.width = w;
        this.height = h;
        calculatePosition();
    }

    private void calculatePosition() {
        this.x = screenWidth - width - margin;
        this.y = margin;
    }

    public void draw(Graphics2D g2, TurnManager TU) 
    {
        if (TU == null) return;
        
        calculatePosition();
        
        // Fondo
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(x, y, width, height);
        
        // Borde
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);
        
        // Textos
        g2.setColor(Color.WHITE);
        g2.drawString("Turno: " + TU.getCurrentTurn(), x + 5, y + 15);
        
        String turno = TU.isPlayerTurn() ? "Jugador" : "Enemigo";
        Color colorTurno = TU.isPlayerTurn() ? Color.GREEN : Color.RED;
        g2.setColor(colorTurno);
        g2.drawString(turno, x + 5, y + 30);
    }
}