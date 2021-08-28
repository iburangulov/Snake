import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 400;
    private final int DOT_SIZE = 20;
    private final int MAX_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[MAX_DOTS];
    private int[] y = new int[MAX_DOTS];
    private int snakeSize;
    private Timer timer;
    private DIRECTION direction = DIRECTION.RIGTH;
    private boolean inGame;

    void move() {
        for (int i = snakeSize; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (this.direction) {
            case RIGTH -> x[0] += DOT_SIZE;
            case LEFT -> x[0] -= DOT_SIZE;
            case UP -> y[0] -= DOT_SIZE;
            case DOWN -> y[0] += DOT_SIZE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(this.apple, this.appleX, this.appleY, this);
            for (int i = 0; i < this.snakeSize; i++) {
                g.drawImage(this.dot, x[i], y[i], this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            this.checkApple();
            this.move();
        }
        repaint();
    }

    void checkApple() {
        if (this.x[0] == this.appleX && this.y[0] == this.appleY) {
            this.snakeSize++;
            this.createApple();
            this.checkCollisions();
        }
    }

    void checkCollisions() {
        for (int i = this.snakeSize; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                this.inGame = false;
            }

            if (this.x[0] > this.SIZE || this.x[0] < 0 || this.y[0] > this.SIZE ||
                    this.y[0] < 0) {
                this.inGame = false;
            }
        }
    }

    private enum DIRECTION {
        LEFT, RIGTH, UP, DOWN
    }

    public GameField() {
        this.inGame = true;
        this.setBackground(Color.BLACK);
        this.loadImages();
        this.init();
        this.addKeyListener(new KeyListener());
        this.setFocusable(true);
    }

    public void loadImages() {
        this.dot = new ImageIcon("dot.png").getImage();
        this.apple = new ImageIcon("apple.png").getImage();
    }

    void init() {
        this.snakeSize = 3;
        for (int i = 0; i < snakeSize; i++) {
            this.x[i] = 60 - i * DOT_SIZE;
            this.y[i] = 100;
        }
        this.timer = new Timer(250, this);
        this.timer.start();
        this.createApple();
    }

    void createApple() {
        this.appleX = new Random().nextInt(20) * DOT_SIZE;
        this.appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (direction != DIRECTION.LEFT) direction = DIRECTION.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != DIRECTION.RIGTH) direction = DIRECTION.RIGTH;
                    break;
                case KeyEvent.VK_UP:
                    if (direction != DIRECTION.UP) direction = DIRECTION.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != DIRECTION.DOWN) direction = DIRECTION.DOWN;
                    break;
            }
        }
    }

}
