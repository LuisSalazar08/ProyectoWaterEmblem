package ui;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import Main.GamePanel;
import Main.TurnManager;
import armas.Armas;
import entidad.Unidad;

public class OptionMenu {
    private int x, y;
    private int width, height;
    private List<String> options;
    private List<String> mainOptions;
    private int selected = 0;
    private boolean visible = false;
    private Font font = new Font("Arial", Font.PLAIN, 16);
    private int lineHeight = 20;
    private final GamePanel panel;
    private boolean inWeaponMenu = false;
    private boolean optionSelected = false;

    public OptionMenu(GamePanel panel, int width, int height, String... opts) {
        this.panel = panel;
        this.width = width;
        this.height = lineHeight * opts.length + 10;
        this.mainOptions = Arrays.asList(opts);
        this.options = new ArrayList<>(mainOptions);
    }

    public void show(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = true;
        this.optionSelected = false;
        Unidad unidad = panel.getJugador().getUnidadSeleccionada();
        List<String> availableOptions = new ArrayList<>();
        if (unidad != null && !unidad.hasMoved()) {
            availableOptions.add("Mover");
        }
        availableOptions.add("Atacar");
        availableOptions.add("Esperar");
        availableOptions.add("Salir");
        setOptions(availableOptions.toArray(new String[0]));
    }

    public void hide() {
        this.visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isOptionSelected() {
        if (optionSelected) {
            optionSelected = false;
            return true;
        }
        return false;
    }

    public int getSelectedIndex() {
        return selected;
    }

    public void setOptions(String... opts) {
        this.options = Arrays.asList(opts);
        this.height = lineHeight * opts.length + 10;
    }

    public void update(boolean upPressed, boolean downPressed, boolean enterPressed, boolean escPressed) {
        if (!visible) return;
        if (upPressed) selected = (selected - 1 + options.size()) % options.size();
        if (downPressed) selected = (selected + 1) % options.size();
        if (enterPressed) {
            optionSelected = true;
            handleEnterPress();
        }
        if (escPressed) handleEscapePress();
    }

    private void updateSelection(int direction) {
        selected = (selected + direction + options.size()) % options.size();
    }

    private int calculateHeight() {
        return lineHeight * options.size() + 10;
    }

    private void handleEnterPress() {
        boolean shouldClose = executeOption(options.get(selected));
        if (shouldClose) hide();
    }

    private void handleEscapePress() {
        if (inWeaponMenu) {
            revertToMainMenu();
        } else {
            hide();
        }
    }

    private void revertToMainMenu() {
        this.options = new ArrayList<>(mainOptions);
        this.inWeaponMenu = false;
        this.selected = 0;
        this.height = calculateHeight();
    }

    private void handleWeaponSelection(String weaponName, TurnManager tm) {
        Unidad unidad = panel.getJugador().getUnidadSeleccionada();
        if (unidad != null) {
            try {
                Armas arma = Armas.valueOf(weaponName);
            } catch (IllegalArgumentException e) {
                System.err.println("Arma inválida: " + weaponName);
            }
        }
        tm.unitDidAction(unidad);
        revertToMainMenu();
    }

    private void handleMoveAction(Unidad u) {
        if (u != null && !u.hasMoved()) {
            u.setSeleccionada(true);
            u.initMovimientoBounds();
            u.setHasMoved(true);
        }
    }

    private void enterWeaponMenu(List<String> armas) {
        this.options = armas;
        this.inWeaponMenu = true;
        this.selected = 0;
        this.height = calculateHeight();
    }

    private boolean handleAttackAction() {
        Unidad unidad = panel.getJugador().getUnidadSeleccionada();
        if (unidad != null) {
            Armas[] armas = unidad.getInventario().getAllArmas();
            List<String> armasDisponibles = new ArrayList<>();
            for (Armas arma : armas) {
                if (arma != null) armasDisponibles.add(arma.name());
            }
            if (!armasDisponibles.isEmpty()) {
                enterWeaponMenu(armasDisponibles);
                return false;
            }
        }
        return true;
    }

    private void updateOptionsAfterMove() {
        List<String> newOptions = new ArrayList<>(options);
        newOptions.remove("Mover");
        setOptions(newOptions.toArray(new String[0]));
        this.height = calculateHeight();
    }

    private boolean handleMainMenuSelection(String opt, TurnManager tm) {
        Unidad u = panel.getJugador().getUnidadSeleccionada();
        switch (opt) {
            case "Mover":
                handleMoveAction(u);
                updateOptionsAfterMove();
                u.setHasMoved(true);
                return true;
            case "Atacar":
                return handleAttackAction();
            case "Esperar":
                tm.unitDidAction(u);
                return true;
            default:
                return true;
        }
    }

    private boolean executeOption(String opt) {
        TurnManager turnManager = panel.getTM();
        Unidad unidad = panel.getJugador().getUnidadSeleccionada();
        if (!turnManager.canUnitAct(unidad)) return true;
        if (inWeaponMenu) {
            handleWeaponSelection(opt, turnManager);
            return true;
        }
        return handleMainMenuSelection(opt, turnManager);
    }

    public void draw(Graphics2D g2) {
        if (!visible) return;
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 10, 10);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 10, 10);
        g2.setFont(font);
        int textX = x + 10;
        int textY = y + lineHeight;
        for (int i = 0; i < options.size(); i++) {
            g2.setColor(i == selected ? Color.YELLOW : Color.WHITE);
            g2.drawString(options.get(i), textX, textY + i * lineHeight);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
