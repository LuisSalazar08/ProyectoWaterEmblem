package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ManejadorTeclas;

public class Cursor extends Entidad {
    private BufferedImage sprite;
    private Unidad unidadSeleccionada;
    private ManejadorMovimiento mMV;
    private boolean enterPresionadoAnterior = false;

    public Cursor(GamePanel gP, ManejadorTeclas mT) {
        super(gP, mT);
        CalculaMovimiento cmv = new CalculaMovimiento(this, this.mT, this.tileSize);
        this.mMV = new ManejadorMovimiento(cmv, 100, 100);
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/spritesCursor/CursorPlayer.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void update() {
        boolean keyPressed = this.mT.getMovimiento();
        mMV.updateMV(keyPressed);
        actualizarSeleccion(gP);
    }

    public void actualizarSeleccion(GamePanel gP) {
        int tileCursorX = mundoX / gP.getTamanioTile();
        int tileCursorY = mundoY / gP.getTamanioTile();
        unidadSeleccionada = null;
        for (Entidad u : gP.getME().getEntidades()) {
            int tilePX = u.getMundoX() / gP.getTamanioTile();
            int tilePY = u.getMundoY() / gP.getTamanioTile();
            if (tilePX == tileCursorX && tilePY == tileCursorY) {
                unidadSeleccionada = (Unidad) u;
                break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int tileSize = gP.getTamanioTile();
        int tileCol = this.mundoX / tileSize;
        int tileRow = this.mundoY / tileSize;
        int tileScreenX = tileCol * tileSize - gP.getJugador().getMundoX() + this.pantallaX;
        int tileScreenY = tileRow * tileSize - gP.getJugador().getMundoY() + this.pantallaY;
        if (unidadSeleccionada != null) {
            g2.setColor(new Color(0, 255, 0, 100));
            g2.fillRect(tileScreenX, tileScreenY, tileSize, tileSize);
        }
        int auxY = 32;
        int cursorScreenX = this.mundoX - gP.getJugador().getMundoX() + this.pantallaX;
        int cursorScreenY = this.mundoY - gP.getJugador().getMundoY() + this.pantallaY - auxY;
        g2.drawImage(sprite, cursorScreenX, cursorScreenY, tileSize, tileSize, null);
    }

    public void resetUnidadSeleccionada() {
        this.unidadSeleccionada = null;
    }

    public Unidad getUnidadSeleccionada() {
        return this.unidadSeleccionada;
    }

    public void setUnidadSeleccionada(Unidad u) {
        this.unidadSeleccionada = u;
    }

    public void setWorldPosition(int worldX, int worldY) {
        this.mundoX = worldX;
        this.mundoY = worldY;
    }

    public void setTilePosition(int col, int row) {
        this.mundoX = col * tileSize;
        this.mundoY = row * tileSize;
    }

    public int getTileX() {
        return mundoX / tileSize;
    }

    public int getTileY() {
        return mundoY / tileSize;
    }

    public boolean isEnterJustPressed(ManejadorTeclas mT) {
        boolean ahora = mT.getTeclaEnter();
        boolean justPressed = ahora && !enterPresionadoAnterior;
        enterPresionadoAnterior = ahora;
        return justPressed;
    }
}