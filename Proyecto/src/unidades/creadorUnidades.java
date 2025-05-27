package unidades;

import armas.TipoArma;

public class creadorUnidades {

    public static Stats getStatsBase(Clases clase) {
        switch (clase) {
            case Jinete:
                return new Stats(24,24, 7, 0, 6, 8, 4, 5, 1, 8, 3, clase, TipoArma.Lanza);
            case Infanteria:
                return new Stats(22, 22, 6, 2, 5, 6, 5, 4, 3, 7, 2, clase, TipoArma.Espada);
            case Acorazado:
                return new Stats(28, 28, 8, 0, 4, 3, 2, 10, 5, 10, 2, clase, TipoArma.Hacha);
            case Volador:
                return new Stats(23, 23, 6, 1, 6, 9, 4, 3, 2, 6, 4, clase, TipoArma.Lanza);
            case Boss:
                return new Stats(200, 200, 8, 1, 6, 9, 4, 3, 2, 6, 4, clase, TipoArma.Lanza);
            default:
                throw new IllegalArgumentException("Clase no reconocida: " + clase);
        }
    }
}