package unidades;

import java.util.Random;

public enum NombresUnidad {
    MARTH("Marth"),
    LYN("Lyn"),
    ELIWOOD("Eliwood"),
    HECTOR("Hector"),
    ROY("Roy"),
    IKE("Ike"),
    LUCINA("Lucina"),
    CORRIN("Corrin"),
    ROBIN("Robin"),
    TIKI("Tiki"),
    CELICA("Celica"),
    EPHRAIM("Ephraim");

    private final String displayName;
    private static final Random RNG = new Random();

    NombresUnidad(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String aleatorio() {
        NombresUnidad[] valores = values();
        return valores[RNG.nextInt(valores.length)].displayName;
    }
}