package breakthrough.view;

import javax.swing.*;

public class GameMenu extends JMenuBar {

    private JMenu gameMenu;
    private JMenu newMenu;

    public GameMenu() {
        super();
        gameMenu = new JMenu("Game");
        newMenu = new JMenu("New");
        add(gameMenu);
        gameMenu.add(newMenu);
    }

    public JMenu getGameMenu() {
        return gameMenu;
    }

    public JMenu getNewMenu() {
        return newMenu;
    }

}
