package breakthrough.view;

import javax.swing.*;

/**
 * The menu of the app.
 */
public class GameMenu extends JMenuBar {

    /**
     * The size of the board the user can choose from.
     */
    private static final int[] boardSizes = new int[]{6, 8, 10};

    /**
     * The topmost level of the menu.
     */
    private JMenu gameMenu = new JMenu("Game");

    /**
     * The menu that includes all the possible new game sizes.
     */
    private JMenu newMenu = new JMenu("New");

    /**
     * The new game menu items.
     */
    private NewMenuItem[] newMenuItems = new NewMenuItem[boardSizes.length];

    /**
     * Constructor the the GameMenu object.
     */
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

    /**
     *
     * @return Value of the newMenuItems property.
     */
    public NewMenuItem[] getNewMenuItems() {
        return newMenuItems;
    }
}
