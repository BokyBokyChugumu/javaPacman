import javax.swing.*;

public class UpgradeObject extends JPanel {
    JLabel upgradeLabel;
    public int xPosition, yPosition;
    int upgradeType;
    public static double playerSpeedMultiplier = 1, ghostSpeedMultiplier = 1, pointMultiplier = 1;
    public static boolean invulnerability = false;
    public static String upgradeDescription = "";
    public UpgradeObject(int xPosition, int yPosition, int upgradeType){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.upgradeType = upgradeType;
        System.out.println("create upgrade");

        ImageIcon upgradeIcon = new ImageIcon("textures/upgrade1.png");
        switch (upgradeType){
            case 1 -> upgradeIcon = new ImageIcon("textures/upgrade1.png");
            case 2 -> upgradeIcon = new ImageIcon("textures/upgrade2.png");
            case 3 -> upgradeIcon = new ImageIcon("textures/upgrade3.png");
            case 4 -> upgradeIcon = new ImageIcon("textures/upgrade4.png");
            case 5 -> upgradeIcon = new ImageIcon("textures/upgrade5.png");
            case 6 -> upgradeIcon = new ImageIcon("textures/upgrade6.png");
        }
        upgradeLabel = new JLabel(upgradeIcon);
        upgradeLabel.setOpaque(true);
        upgradeLabel.setVisible(true);
        upgradeLabel.setBounds(0, 0, Frame.voxelSize, Frame.voxelSize);
        add(upgradeLabel);

        setBounds(this.xPosition, this.yPosition, Frame.voxelSize, Frame.voxelSize);

        this.setBackground(Frame.backgroundColor);
    }

    public void makeUpgrade(){
        switch(upgradeType){
            case 1:
                Thread thread = new Thread(() -> {
                    playerSpeedMultiplier = 1.5;
                    upgradeDescription = "player speed x1.5 for 10 sec";
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: Thread.sleep InterruptedException in Upgrade");
                    }
                    playerSpeedMultiplier = 1;
                    upgradeDescription = "";
                });
                thread.start();
                break;
            case 2:
                Thread thread3 = new Thread(() -> {
                    pointMultiplier = 1.5;
                    upgradeDescription = "point multiplier x1.5 for 10 sec";
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: Thread.sleep InterruptedException in Upgrade");
                    }
                    pointMultiplier = 1;
                    upgradeDescription = "";
                });
                thread3.start();
                break;
            case 3:
                Thread thread2 = new Thread(() -> {
                    playerSpeedMultiplier = 1.5;
                    upgradeDescription = "player speed x1.5 for 5 sec";
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: Thread.sleep InterruptedException in Upgrade");
                    }
                    playerSpeedMultiplier = 1;
                    upgradeDescription = "";
                });
                thread2.start();
                break;
            case 4:
                Thread thread5 = new Thread(() -> {
                    ghostSpeedMultiplier= 0.5;
                    upgradeDescription = "ghost speed x0.5 for 5 sec";
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: Thread.sleep InterruptedException in Upgrade");
                    }
                    ghostSpeedMultiplier = 1;
                    upgradeDescription = "";
                });
                thread5.start();
                break;
            case 5:
                Thread thread4 = new Thread(() -> {
                    pointMultiplier = 2;
                    upgradeDescription = "point multiplier x2 for 5 sec";
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: Thread.sleep InterruptedException in Upgrade");
                    }
                    pointMultiplier = 1;
                    upgradeDescription = "";
                });
                thread4.start();
                break;
            case 6:
                Thread thread6 = new Thread(() -> {
                    invulnerability = true;
                    upgradeDescription = "invulnerability for 5 sec";
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: Thread.sleep InterruptedException in Upgrade");
                    }
                    invulnerability = false;
                    upgradeDescription = "";
                });
                thread6.start();
                break;
        }
    }
}
