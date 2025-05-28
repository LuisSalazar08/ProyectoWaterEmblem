package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
        pantallaX = mundoX - gP.getJugador().getMundoX() + gP.getAnchoPantalla()/2;
        pantallaY = mundoY - gP.getJugador().getMundoY() + gP.getAltoPantalla()/2;
    }

    @Override
    public void update() 
    {
    	if(hasActed || hasMoved) return;
    	contadorFrames++;
    	if (contadorFrames > velocidadAnimacion) 
    	{
    		frameActual = (frameActual + 1) % idleFrames.length;
    		contadorFrames = 0;
    	}
    	if(!gP.getTM().isEnemyTurn()) return;

        seleccionarObjetivo();
        if(objetivo != null && objetivo.isViva()) {
            if(!hasMoved) mover();
            atacar();
        }
        hasActed = true;
        gP.getTM().notificarAccionEnemigo();
    }

    private void seleccionarObjetivo() 
    {
    	List<Unidad> unidades = gP.getME().getEntidades().stream()
                .filter(e -> e instanceof Unidad && !(e instanceof Enemigo)) // Filtro clave
                .map(e -> (Unidad)e)
                .filter(Unidad::isViva)
                .toList();
        
        objetivo = unidades.stream()
            .min((u1, u2) -> Integer.compare(calcularDistancia(u1), calcularDistancia(u2)))
            .orElse(null);
        System.out.println("Objetivo seleccionado: " + (objetivo != null ? objetivo.getNombre() : "Ninguno"));
        
    }

    private int calcularDistancia(Unidad u) 
    {
        int ts = gP.getTamanioTile();
        return (Math.abs(u.getMundoX() - mundoX) + 
              Math.abs(u.getMundoY() - mundoY)) / ts;
    }

    public void initMovimientoBounds() 
    {
    	int mov = this.getStats().getMOV();
        int ts = gP.getTamanioTile();
        int col = mundoX / ts;
        int fila = mundoY / ts;
        
        movimientoTiles.clear();
        for(int dx = -mov; dx <= mov; dx++) 
        {
            for(int dy = -(mov - Math.abs(dx)); dy <= (mov - Math.abs(dx)); dy++) {
                int targetCol = col+ dx;
                int targetRow = fila+ dy;
                movimientoTiles.add(new int[]{
                    targetCol * ts, 
                    targetRow * ts
                });
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
        int ts = gP.getTamanioTile();
        movimientoTiles.stream()
        .filter(this::esTileValido)
        .min(Comparator.comparingInt(t -> 
            Math.abs(t[0] - objetivo.getMundoX()) + 
            Math.abs(t[1] - objetivo.getMundoY())
        ))
        .ifPresent(mejorPos -> {
            this.mundoX = mejorPos[0];
            this.mundoY = mejorPos[1];
            this.hasMoved = true;
        });
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
    	if(this.objetivo==null || !this.objetivo.isViva()) return;
        if(calcularDistancia(objetivo) <= armaEquipada.getAlcance()) 
        {
        	int danio = stats.getSTR() + armaEquipada.getPoder() - objetivo.getStats().getDEF();
            danio = Math.max(0, danio); 
            objetivo.getStats().setHP(objetivo.getStats().getHP() - danio);
            if(!objetivo.isViva()) gP.getME().getEntidades().remove(objetivo);
        }
    }

    @Override
    public void draw(Graphics2D g2) 
    {
    	actualizarPosicionPantalla();
        
        if (idleFrames != null && idleFrames[frameActual] != null) {
            g2.drawImage(
                idleFrames[frameActual], 
                this.pantallaX, 
                this.pantallaY, 
                gP.getTamanioTile(), 
                gP.getTamanioTile(), 
                null
            );
        } 
        if(gP.getTM().isEnemyTurn()) 
        {
            g2.setColor(new Color(255, 0, 0, 100));
            g2.fillRect(pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile());
        }

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