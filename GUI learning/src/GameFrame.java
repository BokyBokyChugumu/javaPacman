import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameFrame extends JPanel {
    public static MapAnalyzer map1;
    ArrayList<CollectablePoint> pointList = new ArrayList<>();
    ArrayList<UpgradeObject> upgradeList = new ArrayList<>();
    public int score = 0;
    public JLabel scoreLabel;
    public JLabel timeLabel;
    public JLabel levelLabel;
    public JLabel upgradeLabel;
    public JLabel HPLabel;
    public int HPAmount = 3;
    int miliSecondsForOutput = 0;
    int[][] textureMap;

    public int[][] mapReference;

    public PlayerObject player;
    Ghost ghost;
    Ghost ghost2;
    int level;
    public int xPlayerStartPosition, yPlayerStartPosition;
    public int xGhostStartPosition, yGhostStartPosition;
    Frame frame;

    public GameFrame(Frame frame,int level){
        this.level = level;
        setLayout(null);
        this.frame = frame;

        this.setBackground(Frame.backgroundColor);

        textureMap = new int[Frame.yWindowSize / 15][Frame.xWindowSize / 15];

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(645, 30, 135, 40);
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);


        levelLabel = new JLabel("LV " + level);
        levelLabel.setBounds(645, 90, 135, 40);
        levelLabel.setForeground(Color.WHITE);
        add(levelLabel);


        timeLabel = new JLabel("Time: 0:0");
        timeLabel.setBounds(645, 60, 135, 40);
        timeLabel.setForeground(Color.WHITE);
        add(timeLabel);

        upgradeLabel = new JLabel(UpgradeObject.upgradeDescription);
        upgradeLabel.setBounds(645, 150, 135, 40);
        upgradeLabel.setForeground(Color.WHITE);
        add(upgradeLabel);

        ImageIcon hpIcon = new ImageIcon("textures/3hp.png");
        HPLabel = new JLabel(hpIcon);
        HPLabel.setBounds(645, 200, 135, 40);
        HPLabel.setForeground(Color.WHITE);
        add(HPLabel);

        JLabel upgrade1Label = new JLabel("Current upgrade: ");
        upgrade1Label.setBounds(645, 120, 135, 40);
        upgrade1Label.setForeground(Color.WHITE);
        add(upgrade1Label);

        setPreferredSize(new Dimension(Frame.xWindowSize, Frame.yWindowSize));
        setLayout(null);
        map1 = new MapAnalyzer(Frame.xWindowSize, Frame.yWindowSize);

        ImageIcon ExitButtonIcon = new ImageIcon("textures/ExitButton.png");
        JButton exitGame = new JButton();
        exitGame.setBounds(645,540,135,60);
        exitGame.setIcon(ExitButtonIcon);
        exitGame.addActionListener(e -> {
            frame.setVisible(false);
            Frame.mainMenuFrame.setVisible(true);
            frame.dispose();
        });
        add(exitGame);

        Thread thread2 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("ERROR: Thread.sleep InterruptedException in Ghost");
                }
                if((int)(Math.random() * 4) == 0){
                    Ghost.createUpgrade = true;
                }
            }
        });
        thread2.start();

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("ERROR: Thread.sleep InterruptedException in Ghost");
                }
                upgradeLabel.setText(UpgradeObject.upgradeDescription);
            }
        });
        thread.start();
    }

    public void checkCollisionWithPlayer(int xCoordinate, int yCoordinate){
        if(player.xPosition == xCoordinate && player.yPosition == yCoordinate){
            if(!UpgradeObject.invulnerability) playerDamaged();
        }
    }

    public void playerDamaged(){
        HPAmount--;

        ghost.xCurrentLoc = xGhostStartPosition;
        ghost.yCurrentLoc = yGhostStartPosition;

        player.xPosition = xPlayerStartPosition;
        player.yPosition = yPlayerStartPosition;
        player.xSpeed = 0;
        player.ySpeed = 0;

        if(HPAmount == 2){
            HPLabel.setIcon(new ImageIcon("textures/2hp.png"));
        }
        if(HPAmount == 1){
            HPLabel.setIcon(new ImageIcon("textures/1hp.png"));
        }
        if(HPAmount == 0){
            JTextField textField = new JTextField(25);
            String name;

            name = textField.getText();

            Frame.addToTheScores(name, score, miliSecondsForOutput);
            frame.setVisible(false);
            Frame.mainMenuFrame.setVisible(true);
            frame.dispose();
        }
    }
    public int getPointListSize(){
        return pointList.size();
    }

    public void updateGameFrame(){
        ghost.xCurrentLoc = xGhostStartPosition;
        ghost.yCurrentLoc = yGhostStartPosition;

        player.xPosition = xPlayerStartPosition;
        player.yPosition = yPlayerStartPosition;
        player.xSpeed = 0;
        player.ySpeed = 0;

        level++;
        levelLabel.setText("LV " + level);

        for (int y = 0; y < 22; y++){
            for(int x = 0; x < 27; x++) {
                if (mapReference[y][x] == 0) {
                    createCollectablePoint(x * Frame.voxelSize, y * Frame.voxelSize);
                }
            }
        }

        revalidate();
        repaint();
    }
    public void createGhost(int xStartPosition,int yStartPosition){
        xGhostStartPosition = xStartPosition;
        yGhostStartPosition = yStartPosition;
        Ghost ghost = new Ghost(xStartPosition, yStartPosition, Frame.voxelSize, this, player);
        ghost.setBounds(0,0, Frame.xWindowSize, Frame.yWindowSize);
        add(ghost);
        if(this.ghost == null){
            this.ghost = ghost;
        }else{
            ghost2 = ghost;
        }
    }
    public void createPlayer(int xStartPosition,int yStartPosition){
        xPlayerStartPosition = xStartPosition;
        yPlayerStartPosition = yStartPosition;
        player = new PlayerObject(xStartPosition, yStartPosition, Frame.voxelSize, this);
        player.setBounds(0,0, Frame.xWindowSize, Frame.yWindowSize);
        add(player);
    }
    public void updateTime(){
        miliSecondsForOutput += 10;
        timeLabel.setText("Time: " + (miliSecondsForOutput / 60000) + ":" + ((miliSecondsForOutput % 60000) / 1000) + ":" + (miliSecondsForOutput % 1000));
    }
    public void addPoints(int pointsAmount){
        score += (int)(pointsAmount * UpgradeObject.pointMultiplier);
        scoreLabel.setText("Score: " + score);
    }
    void createMap(int[][] mapReference, int xSize, int ySize){



        for (int y = 0; y < ySize; y++){
            for(int x = 0; x < xSize; x++) {
                if (mapReference[y][x] == 0) {
                    createCollectablePoint(x * Frame.voxelSize, y * Frame.voxelSize);
                }

                if (mapReference[y][x] == 1) {
                    textureMap[(2 * y)][(2 * x)] = 1;
                    textureMap[(2 * y) + 1][(2 * x)] = 1;
                    textureMap[(2 * y)][(2 * x) + 1] = 1;
                    textureMap[(2 * y) + 1][(2 * x) + 1] = 1;
                }

            }
        }

        for (int y = 0; y < ySize * 2; y++){
            for(int x = 0; x < xSize * 2; x++){
                if(textureMap[y][x] == 1){
                    int[][] environmentMap = {
                            {0, 0, 0},
                            {0, 1, 0},
                            {0, 0, 0}
                    };
                    for (int row = 0; row < 3; row++) {
                        for (int column = 0; column < 3; column++) {
                            int yCoordinate = y + row - 1;
                            int xCoordinate = x + column - 1;
                            if(yCoordinate < 0 || yCoordinate >= ySize * 2 || xCoordinate < 0 || xCoordinate >= xSize * 2){
                                environmentMap[row][column] = 1;
                            }else{
                                environmentMap[row][column] = textureMap[yCoordinate][xCoordinate];
                            }
                        }
                    }


                    boolean texturingDone = false;
                    ImageIcon wallIcon;
                    int smallVoxel = Frame.voxelSize / 2;
                    //-----------------------------------------------------------------------------------------------------------------------------------
                    //-------------------------Wall------------------------------
                    int[][] testMap = {
                            {1, 1, 1},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/Wall.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //-------------------------WallLeft------------------------------
                    testMap = new int[][]{
                            {0, 1, 1},
                            {0, 1, 1},
                            {0, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeft.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 1},
                            {0, 1, 1},
                            {0, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeft.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {0, 1, 1},
                            {0, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeft.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //-------------------------WallRight------------------------------
                    testMap = new int[][]{
                            {1, 1, 0},
                            {1, 1, 0},
                            {1, 1, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRight.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 0},
                            {1, 1, 0},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRight.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 1},
                            {1, 1, 0},
                            {1, 1, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRight.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //-------------------------WallLower------------------------------
                    testMap = new int[][]{
                            {1, 1, 1},
                            {1, 1, 1},
                            {0, 0, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLower.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 1},
                            {1, 1, 1},
                            {1, 0, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLower.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 1},
                            {1, 1, 1},
                            {0, 0, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLower.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //-------------------------WallUpper------------------------------
                    testMap = new int[][]{
                            {0, 0, 0},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallUpper.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 0, 0},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallUpper.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {0, 0, 1},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallUpper.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //-------------------------Walls-Inside------------------------------
                    testMap = new int[][]{
                            {1, 1, 0},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRightUpperInside.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {0, 1, 1},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeftUpperInside.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 1},
                            {1, 1, 1},
                            {0, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeftLowerInside.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 1},
                            {1, 1, 1},
                            {1, 1, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRightLowerInside.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //-------------------------Walls-Outside------------------------------
                    testMap = new int[][]{
                            {0, 0, 0},
                            {1, 1, 0},
                            {1, 1, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeftLower.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {0, 0, 0},
                            {0, 1, 1},
                            {0, 1, 1}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRightLower.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {1, 1, 0},
                            {1, 1, 0},
                            {0, 0, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallLeftUpper.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }

                    testMap = new int[][]{
                            {0, 1, 1},
                            {0, 1, 1},
                            {0, 0, 0}
                    };
                    if (Arrays.deepEquals(environmentMap, testMap)){
                        wallIcon = new ImageIcon("textures/WallRightUpper.png");
                        createBorderObject(x * smallVoxel,y * smallVoxel, smallVoxel, smallVoxel, wallIcon);
                        texturingDone = true;
                    }
                    //----------------------------------------------------------------------------------------------------------------------------------------------------
                }
            }
        }
    }

    public int findPointIndex(int xPointCoordinate, int yPointCoordinate){
        int ans = -1;
        for(int i = 0; i < pointList.size(); i++){
            if(pointList.get(i).xPosition == (xPointCoordinate + 11) && pointList.get(i).yPosition == (yPointCoordinate + 11)){
                ans = i;
                break;
            }
        }
        return ans;
    }

    int findUpgradeIndex(int xCoordinate, int yCoordinate){
        int ans = -1;
        for(int i = 0; i < upgradeList.size(); i++){
            if(upgradeList.get(i).xPosition == xCoordinate && upgradeList.get(i).yPosition == yCoordinate){
                ans = i;
                break;
            }
        }
        return ans;
    }

    public void deletePoint(int xPointCoordinate, int yPointCoordinate){
        int index = findPointIndex(xPointCoordinate,yPointCoordinate);
        if(index == -1){
            System.out.println("ERROR: point not found and cannot be deleted");
        }else {
            //System.out.println("Point collected!");
            map1.removePoint(pointList.get(index));
            remove(pointList.get(index));
            pointList.remove(index);
            revalidate();
            repaint();
        }
    }

    void deleteUpgrade(int xCoordinate, int yCoordinate){
        int index = findUpgradeIndex(xCoordinate, yCoordinate);
        if(index == -1){
            System.out.println("ERROR: point not found and cannot be deleted");
        }else{
            map1.removeUpgrade(upgradeList.get(index));
            remove(upgradeList.get(index));
            pointList.remove(index);
            revalidate();
            repaint();
        }
    }

    public void myRepaint(){
        repaint();
    }

    void createCollectablePoint(int xCoordinate, int yCoordinate){
        xCoordinate += 11;
        yCoordinate += 11;
        CollectablePoint point = new CollectablePoint(xCoordinate, yCoordinate);
        point.setBounds(xCoordinate, yCoordinate, CollectablePoint.xSize, CollectablePoint.ySize);
        add(point);
        map1.addPoint(xCoordinate, yCoordinate, CollectablePoint.xSize, CollectablePoint.ySize);
        point.setOpaque(true);
        point.setVisible(true);
        pointList.add(point);
        point.repaint();
        point.revalidate();
        repaint();
        revalidate();
    }

    void createUpgradeObject(int xCoordinate, int yCoordinate){
        UpgradeObject upgrade = new UpgradeObject(xCoordinate, yCoordinate, (int)((Math.random() * 6) + 1));
        upgrade.setBounds(xCoordinate, yCoordinate, Frame.voxelSize, Frame.voxelSize);
        add(upgrade);
        map1.addUpgrade(xCoordinate, yCoordinate);
        upgrade.setOpaque(true);
        upgrade.setVisible(true);
        upgradeList.add(upgrade);
        upgrade.repaint();
        upgrade.revalidate();
        repaint();
        revalidate();
    }

    void createBorderObject(int xCoordinate, int yCoordinate, int xSize, int ySize, ImageIcon wallIcon){
        BorderObject wall = new BorderObject(0,0, xSize, ySize, wallIcon);
        wall.setBounds(xCoordinate,yCoordinate,xSize,ySize);
        add(wall);
        map1.addWall(xCoordinate, yCoordinate, xSize, ySize);
    }

    public static MapAnalyzer getMap(){
        return map1;
    }


}
