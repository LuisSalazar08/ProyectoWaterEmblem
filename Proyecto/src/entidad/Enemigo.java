package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Main.GamePanel;
import tile.ManejadorTiles;
import unidades.Clases;
import unidades.Stats;
import armas.Armas;
import unidades.Inventario;

public class Enemigo extends Entidad {
    private Stats stats;
    private Inventario inventario;
    private Armas armaEquipada;
    private Clases clase;
    private List<int[]> movimientoTiles;
    private boolean hasActed;
    private boolean hasMoved;
    private int rangoVision = 5;
    private Unidad objetivo;
    private String nombre;
    
    private BufferedImage[] idleFrames;
    private int frameActual = 0;
    private int contadorFrames = 0;
    private final int velocidadAnimacion = 10;
    
    private int pantallaX;
    private int pantallaY;


    public Enemigo(String nombre, GamePanel gp, int mundoX, int mundoY, Stats stats, Clases clase) {
        super(gp,gp.getMT());
        this.nombre = nombre;
        this.mundoX = mundoX;
        this.mundoY = mundoY;
        this.stats = stats;
        this.clase = clase;
        this.inventario = new Inventario(this);
        this.armaEquipada = this.inventario.getAllArmas()[0];
        this.movimientoTiles = new ArrayList<>();
        this.cargarSprites();
    }
    private void actualizarPosicionPantalla() 
    {
        // Misma lógica que en Unidad para posición relativa a la cámara
        pantallaX = mundoX - gP.getJugador().getMundoX() + gP.getAnchoPantalla()/2;
        pantallaY = mundoY - gP.getJugador().getMundoY() + gP.getAltoPantalla()/2;
    }

    @Override
    public void update() 
    {
        if(hasActed || !gP.getTM().isEnemyTurn()) return;
        
        contadorFrames++;
        if (contadorFrames > velocidadAnimacion) 
        {
        	frameActual = (frameActual + 1) % idleFrames.length;
        	contadorFrames = 0;
        }
        
        seleccionarObjetivo();
        if(objetivo != null && objetivo.isViva()) {
            if(!hasMoved) mover();
            atacar();
        }
        hasActed = true;
        gP.getTM().notificarAccionEnemigo();
    }

    private void seleccionarObjetivo() {
        List<Unidad> unidades = gP.getME().getEntidades().stream()
            .filter(e -> e instanceof Unidad)
            .map(e -> (Unidad)e)
            .toList();
        
        objetivo = unidades.stream()
            .min((u1, u2) -> Integer.compare(calcularDistancia(u1), calcularDistancia(u2)))
            .orElse(null);
    }

    private int calcularDistancia(Unidad u) {
        int ts = gP.getTamanioTile();
        return (Math.abs(u.getMundoX() - mundoX) + 
              Math.abs(u.getMundoY() - mundoY)) / ts;
    }

    public void initMovimientoBounds() 
    {
        int ts = gP.getTamanioTile();
        int mov = stats.getMOV();
        int col = mundoX / ts;
        int fila = mundoY / ts;
        
        movimientoTiles.clear();
        for(int dx = -mov; dx <= mov; dx++) {
            for(int dy = -(mov - Math.abs(dx)); dy <= (mov - Math.abs(dx)); dy++) {
                int wx = (col + dx) * ts;
                int wy = (fila + dy) * ts;
                movimientoTiles.add(new int[]{wx, wy});
            }
        }
    }
    private void cargarSprites() 
    {
        idleFrames = new BufferedImage[6];
        try {
            for (int i = 0; i < 6; i++) {
                idleFrames[i] = ImageIO.read(getClass().getResourceAsStream(
                    "/spritesUnidad/" + this.getTipoUnidad() + i + ".png"
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void mover() 
    {
        initMovimientoBounds();
        int[] mejorPos = movimientoTiles.stream()
            .filter(this::esTileValido)
            .min((t1, t2) -> Integer.compare(
                calcularDistanciaTile(t1, objetivo),
                calcularDistanciaTile(t2, objetivo)))
            .orElse(new int[]{mundoX, mundoY});
        
        mundoX = mejorPos[0];
        mundoY = mejorPos[1];
        hasMoved = true;
    }

    private int calcularDistanciaTile(int[] tile, Unidad u) {
        return (Math.abs(tile[0] - u.getMundoX()) + 
              Math.abs(tile[1] - u.getMundoY()));
    }

    private boolean esTileValido(int[] tile) {
        ManejadorTiles mt = gP.getManejadorTiles();
        int ts = gP.getTamanioTile();
        int col = tile[0] / ts;
        int fila = tile[1] / ts;
        return mt.isPassable(fila, col) && 
              gP.getME().getEntidades().stream()
                .noneMatch(e -> e.getMundoX() == tile[0] && e.getMundoY() == tile[1]);
    }

    private void atacar() 
    {
        if(calcularDistancia(objetivo) <= armaEquipada.getAlcance()) 
        {
            int danio = stats.getSTR() + armaEquipada.getPoder() - objetivo.getStats().getDEF();
            objetivo.getStats().setHP(objetivo.getStats().getHP() - danio);
            if(!objetivo.isViva()) gP.getME().remove(objetivo);
        }
    }

    @Override
    public void draw(Graphics2D g2) 
    {
    	actualizarPosicionPantalla(); // Actualizar posición cada frame
        
        // Dibujar sprite animado con filtro rojo
        if (idleFrames != null && idleFrames[frameActual] != null) {
            g2.drawImage(
                idleFrames[frameActual], 
                pantallaX, 
                pantallaY, 
                gP.getTamanioTile(), 
                gP.getTamanioTile(), 
                null
            );
        } 

        // Resaltado de selección (opcional para debug)
        if(gP.getTM().isEnemyTurn()) 
        {
            g2.setColor(new Color(255, 0, 0, 100));
            g2.fillRect(pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile());
        }

        // Borde identificativo
        g2.setColor(Color.RED);
        g2.drawRect(pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile());
        
    }

    public String getTipoUnidad() {
        return clase.toString();
    }
    public boolean hasActed() { return hasActed; }
    public void setHasActed(boolean acted) { this.hasActed = acted; }
    public boolean hasMoved() { return hasMoved; }
    public void setHasMoved(boolean moved) { this.hasMoved = moved; }
    public boolean isViva() { return stats.getHP() > 0; }
    public Stats getStats() { return stats; }
}