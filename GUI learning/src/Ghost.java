import javax.swing.*;
public class Ghost extends JPanel {

    int xDistanceToPlayer = 0, yDistanceToPlayer = 0;
    int nextDirection = 0, currentDirection = 1;
    int xCurrentLoc, yCurrentLoc;
    MapAnalyzer map;
    GameFrame frame;
    int ghostSize;
    JLabel ghostLabel;
    int xPlayerCurrentLoc = 1, yPlayerCurrentLoc = 1;
    int xSpeed = 0, ySpeed = 0;
    PlayerObject player;
    int bestDir, goodDir;
    static boolean createUpgrade = false;

    public Ghost(int xStartPosition, int yStartPosition, int size, GameFrame frame, PlayerObject playerObject){
        xCurrentLoc = xStartPosition;
        yCurrentLoc = yStartPosition;
        player = playerObject;

        map = GameFrame.getMap();
        setLayout(null);
        this.frame = frame;
        ghostSize = size;

        ImageIcon ghostIcon = new ImageIcon("textures/ghostBeta.png");

        ghostLabel = new JLabel(ghostIcon);
        ghostLabel.setOpaque(true);
        ghostLabel.setBounds(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize);
        add(ghostLabel);

        this.setBackground(Frame.backgroundColor);

        xPlayerCurrentLoc = player.xPosition;
        yPlayerCurrentLoc = player.yPosition;

        xDistanceToPlayer = xPlayerCurrentLoc - xCurrentLoc;
        yDistanceToPlayer = yPlayerCurrentLoc - yCurrentLoc;

        chooseBestDirection();
        currentDirection = 1;
        nextDirection = 3;
        translateDirectionToSpeed(currentDirection);

        Thread thread = new Thread(() -> {
            while (true) {

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("ERROR: Thread.sleep InterruptedException in Ghost");
                }

                createBestDirection();
                if(canMove(bestDir)){
                    translateDirectionToSpeed(bestDir);
                }

                if (reachedWall()) {
                    chooseBestDirection();
                    translateDirectionToSpeed(currentDirection);
                }
                ghostLabel.repaint();
                ghostLabel.revalidate();
                if(createUpgrade){
                    if(xCurrentLoc % Frame.voxelSize == 0 && yCurrentLoc % Frame.voxelSize == 0){
                        if(!GameFrame.map1.checkPointUnder(xCurrentLoc,yCurrentLoc) && !GameFrame.map1.checkUpgradeUnder(xCurrentLoc,yCurrentLoc)) {
                            frame.createUpgradeObject(xCurrentLoc, yCurrentLoc);
                            createUpgrade = false;
                        }
                    }
                }
                moveGhost();

                frame.checkCollisionWithPlayer(xCurrentLoc, yCurrentLoc);
            }
        });
        thread.start();



    }
    void createBestDirection(){
        xPlayerCurrentLoc = player.xPosition;
        yPlayerCurrentLoc = player.yPosition;

        xDistanceToPlayer = xPlayerCurrentLoc - xCurrentLoc;
        yDistanceToPlayer = yPlayerCurrentLoc - yCurrentLoc;

        if (Math.abs(xDistanceToPlayer) > Math.abs(yDistanceToPlayer)) { //выбераем расстояние побольше
            if (xDistanceToPlayer > 0) {
                bestDir = 4; // right
            } else {
                bestDir = 3; // left
            }
            if (yDistanceToPlayer > 0) {
                goodDir = 2; // down
            } else {
                goodDir = 1; // up
            }
        } else {
            if (yDistanceToPlayer > 0) {
                bestDir = 2; // down
            } else {
                bestDir = 1; // up
            }
            if (xDistanceToPlayer > 0) {
                goodDir = 4; // right
            } else {
                goodDir = 3; // left
            }
        }
    }
    void chooseBestDirection() {

        createBestDirection();

        if (!canMove(bestDir)) {
            int temp = bestDir;
            bestDir = goodDir;
            goodDir = temp;
        }
        while (!canMove(bestDir) && !canMove(goodDir)) {
            bestDir = getRandomDirection();
        }
        currentDirection = bestDir;
    }
    boolean canMove(int direction) {
        return switch (direction) {
            case 1 -> map.getUpperWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize) < yCurrentLoc;
            case 2 -> map.getLowerWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize) > yCurrentLoc;
            case 3 -> map.getLeftWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize) < xCurrentLoc;
            case 4 -> map.getRightWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize) > xCurrentLoc;
            default -> false;
        };
    }
    boolean reachedWall() {
        return switch (currentDirection) {
            case 1 -> yCurrentLoc <= map.getUpperWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize);
            case 2 -> yCurrentLoc >= map.getLowerWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize);
            case 3 -> xCurrentLoc <= map.getLeftWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize);
            case 4 -> xCurrentLoc >= map.getRightWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize);
            default -> false;
        };
    }
    void moveGhost() {
        xCurrentLoc = Math.max(map.getLeftWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize),
                Math.min(xCurrentLoc + xSpeed, map.getRightWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize)));
        yCurrentLoc = Math.max(map.getUpperWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize),
                Math.min(yCurrentLoc + ySpeed, map.getLowerWallCoordinate(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize)));
        ghostLabel.setBounds(xCurrentLoc, yCurrentLoc, ghostSize, ghostSize);
        ghostLabel.repaint();
        ghostLabel.revalidate();
    }


    void translateDirectionToSpeed(int direction) {
        switch (direction) {
            case 1:  // up
                xSpeed = 0;
                ySpeed = (int)(-2 * UpgradeObject.ghostSpeedMultiplier);
                break;
            case 2:  // down
                xSpeed = 0;
                ySpeed = (int)(2 * UpgradeObject.ghostSpeedMultiplier);;
                break;
            case 3:  // left
                xSpeed = (int)(-2 * UpgradeObject.ghostSpeedMultiplier);
                ySpeed = 0;
                break;
            case 4:  // right
                xSpeed = (int)(2 * UpgradeObject.ghostSpeedMultiplier);
                ySpeed = 0;
                break;
        }
    }
    int getRandomDirection() {
        int[] directions = {1, 2, 3, 4};
        int randomIndex = (int) (Math.random() * directions.length);
        return directions[randomIndex];
    }
}
