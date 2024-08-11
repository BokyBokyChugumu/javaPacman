import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerObject extends JPanel {
    JLabel playerLabel;
    int xPosition;
    int yPosition;
    int playerSize;
    int xSpeed = 0;
    int ySpeed = 0;
    MapAnalyzer map;
    int nextDirection = 0, currentDirection = 1;
    GameFrame frame;
    int counter = 0, animationCounter = 1;

    public PlayerObject(int xStartPosition, int yStartPosition, int size, GameFrame frame) {

        map = GameFrame.getMap();
        setLayout(null);

        this.frame = frame;
        xPosition = xStartPosition;
        yPosition = yStartPosition;
        playerSize = size;


        ImageIcon PacMan3Icon = new ImageIcon("textures/pacman3.png");
        ImageIcon PacMan2leftIcon = new ImageIcon("textures/pacman2left.png");
        ImageIcon PacMan2rightIcon = new ImageIcon("textures/pacman2right.png");
        ImageIcon PacMan2upperIcon = new ImageIcon("textures/pacman2upper.png");
        ImageIcon PacMan2lowerIcon = new ImageIcon("textures/pacman2lower.png");
        ImageIcon PacMan1leftIcon = new ImageIcon("textures/pacman1left.png");
        ImageIcon PacMan1rightIcon = new ImageIcon("textures/pacman1right.png");
        ImageIcon PacMan1upperIcon = new ImageIcon("textures/pacman1upper.png");
        ImageIcon PacMan1lowerIcon = new ImageIcon("textures/pacman1lower.png");

        playerLabel = new JLabel(PacMan3Icon);
        playerLabel.setOpaque(true);
        playerLabel.setBounds(xPosition, yPosition, playerSize, playerSize);
        this.setBackground(Frame.backgroundColor);
        add(playerLabel);



        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        //System.out.println("up button clicked");
                        if(map.getUpperWallCoordinate(xPosition, yPosition, playerSize, playerSize) == yPosition){
                            nextDirection = 1;
                        }else {
                            xSpeed = 0;
                            ySpeed = (int)(-2 * UpgradeObject.playerSpeedMultiplier);
                            currentDirection = 1;
                            nextDirection = 0;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        //System.out.println("down button clicked");
                        if(map.getLowerWallCoordinate(xPosition, yPosition, playerSize, playerSize) == yPosition){
                            nextDirection = 2;
                        }else {
                            xSpeed = 0;
                            ySpeed = (int)(2 * UpgradeObject.playerSpeedMultiplier);
                            currentDirection = 2;
                            nextDirection = 0;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        //System.out.println("left button clicked");
                        if(map.getLeftWallCoordinate(xPosition, yPosition, playerSize, playerSize) == xPosition){
                            nextDirection = 3;
                        }else {
                            xSpeed = (int)(-2 * UpgradeObject.playerSpeedMultiplier);
                            ySpeed = 0;
                            currentDirection = 3;
                            nextDirection = 0;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        //System.out.println("right button clicked");
                        if(map.getRightWallCoordinate(xPosition, yPosition, playerSize, playerSize) == xPosition){
                            nextDirection = 4;
                        }else {
                            xSpeed = (int)(2 * UpgradeObject.playerSpeedMultiplier);
                            ySpeed = 0;
                            currentDirection = 4;
                            nextDirection = 0;
                        }
                        break;
                }

                playerLabel.setBounds(xPosition, yPosition, playerSize, playerSize);



            }
        });

        setFocusable(true);
        requestFocusInWindow();


        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("ERROR: Thread.sleep InterruptedException in Player");
                }


                if(counter == 30){
                    frame.myRepaint();
                    counterToZero();
                }else {
                    counterPlusOne();
                }
                for(var v: frame.upgradeList){
                    v.repaint();
                    v.revalidate();
                }
                for(var v: frame.pointList){
                    v.repaint();
                    v.revalidate();
                }

                switch (nextDirection){
                    case 1:  //up
                        if(map.getUpperWallCoordinate(xPosition, yPosition, playerSize, playerSize) < yPosition){
                            xSpeed = 0;
                            ySpeed = (int)(-2 * UpgradeObject.playerSpeedMultiplier);
                            nextDirection = 0;
                            currentDirection = 1;

                        }
                        break;
                    case 2:  //down
                        if(map.getLowerWallCoordinate(xPosition, yPosition, playerSize, playerSize) > yPosition){
                            xSpeed = 0;
                            ySpeed = (int)(2 * UpgradeObject.playerSpeedMultiplier);
                            nextDirection = 0;
                            currentDirection = 2;

                        }
                        break;
                    case 3:  //left
                        if(map.getLeftWallCoordinate(xPosition, yPosition, playerSize, playerSize) < xPosition){
                            xSpeed = (int)(-2 * UpgradeObject.playerSpeedMultiplier);
                            ySpeed = 0;
                            nextDirection = 0;
                            currentDirection = 3;
                        }
                        break;
                    case 4:  //right
                        if(map.getRightWallCoordinate(xPosition, yPosition, playerSize, playerSize) > xPosition){
                            xSpeed = (int)(2 * UpgradeObject.playerSpeedMultiplier);
                            ySpeed = 0;
                            nextDirection = 0;
                            currentDirection = 4;

                        }
                        break;
                }
                if(counter % 5 == 0) {
                    switch (currentDirection) {
                        case 1: // up

                            switch (animationCounter) {
                                case 1:
                                    playerLabel.setIcon(PacMan1upperIcon);
                                    animationCounter++;
                                    break;
                                case 2:
                                    playerLabel.setIcon(PacMan2upperIcon);
                                    animationCounter++;
                                    break;
                                case 3:
                                    playerLabel.setIcon(PacMan3Icon);
                                    animationCounter++;
                                    break;
                                case 4:
                                    playerLabel.setIcon(PacMan2upperIcon);
                                    animationCounter = 1;
                                    break;
                            }
                            break;
                        case 2: // down
                            switch (animationCounter) {
                                case 1:
                                    playerLabel.setIcon(PacMan1lowerIcon);
                                    animationCounter++;
                                    break;
                                case 2:
                                    playerLabel.setIcon(PacMan2lowerIcon);
                                    animationCounter++;
                                    break;
                                case 3:
                                    playerLabel.setIcon(PacMan3Icon);
                                    animationCounter++;
                                    break;
                                case 4:
                                    playerLabel.setIcon(PacMan2lowerIcon);
                                    animationCounter = 1;
                                    break;
                            }
                            break;
                        case 3: // left
                            switch (animationCounter) {
                                case 1:
                                    playerLabel.setIcon(PacMan1leftIcon);
                                    animationCounter++;
                                    break;
                                case 2:
                                    playerLabel.setIcon(PacMan2leftIcon);
                                    animationCounter++;
                                    break;
                                case 3:
                                    playerLabel.setIcon(PacMan3Icon);
                                    animationCounter++;
                                    break;
                                case 4:
                                    playerLabel.setIcon(PacMan2leftIcon);
                                    animationCounter = 1;
                                    break;
                            }
                            break;
                        case 4: // right
                            switch (animationCounter) {
                                case 1:
                                    playerLabel.setIcon(PacMan1rightIcon);
                                    animationCounter++;
                                    break;
                                case 2:
                                    playerLabel.setIcon(PacMan2rightIcon);
                                    animationCounter++;
                                    break;
                                case 3:
                                    playerLabel.setIcon(PacMan3Icon);
                                    animationCounter++;
                                    break;
                                case 4:
                                    playerLabel.setIcon(PacMan2rightIcon);
                                    animationCounter = 1;
                                    break;
                            }
                            break;
                    }
                }
                playerLabel.repaint();

                if(frame.getPointListSize() == 0){
                    frame.updateGameFrame();
                }

                if(map.checkPointUnder(xPosition, yPosition)){
                    frame.addPoints(10);
                    frame.deletePoint(xPosition, yPosition);
                }
                if(map.checkUpgradeUnder(xPosition, yPosition)){
                    int index = frame.findUpgradeIndex(xPosition, yPosition);
                    frame.upgradeList.get(index).makeUpgrade();
                    frame.deleteUpgrade(xPosition, yPosition);
                }
                frame.updateTime();
                movePlayer();
            }
        });
        thread.start();


    }

    void counterPlusOne(){
        counter++;
    }
    void counterToZero(){
        counter = 0;
    }
    void movePlayer() {

        //         Math.max(left border, Math.min(new location, right border));
        xPosition = Math.max(map.getLeftWallCoordinate(xPosition, yPosition, playerSize, playerSize), Math.min(xPosition + xSpeed, map.getRightWallCoordinate(xPosition,yPosition, playerSize, playerSize)));
        yPosition = Math.max(map.getUpperWallCoordinate(xPosition, yPosition, playerSize, playerSize), Math.min(yPosition + ySpeed, map.getLowerWallCoordinate(xPosition,yPosition, playerSize, playerSize)));
        playerLabel.setBounds(xPosition, yPosition, playerSize, playerSize);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450, 500);
    }



}