package breakthrough.view;

import javax.swing.*;

public class GameMenu extends JMenuBar {

    private static final int[] boardSizes = new int[]{6, 8, 10};

    private JMenu gameMenu = new JMenu("Game");
    private JMenu newMenu = new JMenu("New");
    private NewMenuItem[] newMenuItems = new NewMenuItem[boardSizes.length];

    public GameMenu() {
        super();
        add(gameMenu);
        gameMenu.add(newMenu);

        for (int i = 0; i < boardSizes.length; i++) {
            int boardSize = boardSizes[i];
            NewMenuItem sizeMenuItem = new NewMenuItem(boardSize);
            newMenuItems[i] = sizeMenuItem;
            newMenu.add(sizeMenuItem);
        }
    }

    public NewMenuItem[] getNewMenuItems() {
        return newMenuItems;
    }
}
