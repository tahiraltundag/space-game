import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(1,this);
    private int elapsedTime = 0;
    private int spentFire = 0;

    private BufferedImage image;

    private int ballX = 0;
    private int ballIsX = 2;
    private int spaceShipX = 0;
    private int spaceShipIsX = 20;
    private int fireIsY = 1;
    private List<Fire> fires = new ArrayList<>();

    Ball ball1 = new Ball(0,0);
    Ball ball2 = new Ball(763,25);

    Ball[] balls = {ball1,ball2};

    private static final int BULLET_GAP = 500;

    private long lastBulletTime = 0;

    public Game(){
        try {
            image = ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.black);
        timer.start();
    }

    public boolean checkUp(){
        for (Fire fire : fires){
            if(new Rectangle(fire.getX(), fire.getY(), 10,20).intersects(ball1.getX(), ball2.getY(), 20, 20)){
                balls[0] = null;
            }
            if(new Rectangle(fire.getX(), fire.getY(), 10,20).intersects(ball2.getX(), ball2.getY(), 20, 20)){
                balls[1] = null;
            }
            if(balls[0] == null && balls[1] == null){
                return true;
            }
        }
        return false;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if(balls[0] != null){
            g.setColor(Color.red);
            g.fillOval(ball1.getX(),ball1.getY(),20,20);
        }

        if(balls[1] != null){
            g.setColor(Color.yellow);
            g.fillOval(ball2.getX(), ball2.getY(),20,20);
        }

        g.drawImage(image,spaceShipX,480, image.getWidth() / 10, image.getHeight() / 10,this);

        for (Fire fire : fires){
            if(fire.getY() < 0){
                fires.remove(fire);
            }
        }

        g.setColor(Color.blue);
        for (Fire fire : fires){
            g.fillRect(fire.getX(), fire.getY(), 10, 20);
        }

        if(checkUp()){
            timer.stop();
            String message = "You win...\n" +
                    "Spent Fire : " + spentFire +
                    "\nElapsed Time : " + elapsedTime / 1000.0 + " second";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fire fire : fires){
            fire.setY(fire.getY() - fireIsY);
        }

        ball1action();
        ball2Action();
        repaint();
    }

    public void ball1action(){
        ball1.setX(ball1.getX() + ballIsX);
        if(ball1.getX() >= 763){
            ballIsX = -ballIsX;
        }
        if(ball1.getX() <= 0){
            ballIsX = -ballIsX;
        }
    }

    public void ball2Action(){
        ball2.setX(ball2.getX() - ballIsX);
        if(ball2.getX() >= 763){
            ballIsX = -ballIsX;
        }
        if(ball2.getX() <= 0){
            ballIsX = -ballIsX;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if(c == KeyEvent.VK_LEFT){
            if(spaceShipX <= 0){
                spaceShipX = 0;
            }
            else{
                spaceShipX -= spaceShipIsX;
            }
        }
        else if (c == KeyEvent.VK_RIGHT){
            if(spaceShipX >= 736){
                spaceShipX = 736;
            }
            else{
                spaceShipX += spaceShipIsX;
            }
        }
        else if (c == KeyEvent.VK_CONTROL){
            if(System.currentTimeMillis() - lastBulletTime >= BULLET_GAP){
                fires.add(new Fire(spaceShipX+18, 465));
                lastBulletTime = System.currentTimeMillis();
            }
            spentFire++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
