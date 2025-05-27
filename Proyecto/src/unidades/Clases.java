package unidades;

public enum Clases {
    Jinete("Jinete"),
    Infanteria("Infanteria"),
    Acorazado("Acorazado"),
    Volador("Volador"),
	Boss("Boss");

    private final String displayName;
    Clases(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
