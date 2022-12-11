import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        GameScreen gameScreen = new GameScreen("SPACE GAME");
        gameScreen.setResizable(false);
        gameScreen.setFocusable(false);
        gameScreen.setSize(800,600);
        gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        game.requestFocus();
        game.addKeyListener(game);
        game.setFocusable(true);
        game.setFocusTraversalKeysEnabled(false);

        gameScreen.add(game);
        gameScreen.setVisible(true);
    }
}