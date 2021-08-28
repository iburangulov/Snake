import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        this.setTitle("Snake game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocation(800, 300);
        this.add(new GameField());
        this.setVisible(true);

    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
