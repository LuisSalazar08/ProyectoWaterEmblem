package unidades;

public enum Clases {
    Jinete("Jinete"),
    Infanteria("Infanteria"),
    Acorazado("Acorazado"),
    Volador("Volador");

    private final String displayName;
    Clases(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
