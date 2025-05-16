package Main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import armas.Armas;
import unidades.Clases;
import unidades.NombresUnidad;
import unidades.Stats;
import unidades.creadorUnidades;
import entidad.Entidad;
import entidad.Unidad;

public class ManejadorEntidades {
    private List<Entidad> entidades = new ArrayList<>();
    private final GamePanel gp;

    public ManejadorEntidades(GamePanel gp) {
        this.gp = gp;
        this.generarEntidades();
    }

    public void generarEntidades() {
        int tileSize = gp.getTamanioTile();
        int centerX = (gp.getMaxColMundo() / 2) * tileSize;
        int centerY = (gp.getMaxRenMundo() / 2) * tileSize;

        Clases[] tipos = {
            Clases.Jinete,
            Clases.Infanteria,
            Clases.Acorazado,
            Clases.Volador
        };

        for (int i = 0; i < tipos.length; i++) {
            int x = centerX - i * 2 * tileSize;
            int y = centerY;
            String nombreAleatorio = NombresUnidad.aleatorio();
            Stats stats = creadorUnidades.getStatsBase(tipos[i]);
            Unidad unidad = new Unidad(nombreAleatorio,gp, gp.getMT(), x, y, stats, tipos[i]);
            unidad.setArmaEquipada(Armas.Excalibur);
            this.add(unidad);
        }
    }

    public void add(Entidad e) {
        entidades.add(e);
    }

    public void remove(Entidad e) {
        entidades.remove(e);
    }

    public void updateAll() {
        for (Entidad e : entidades)
            e.update();
    }

    public void drawALL(Graphics2D g2) {
        for (Entidad e : entidades)
            e.draw(g2);
    }

    public List<Entidad> getEntidades() {
        return this.entidades;
    }
}
