import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame extends JFrame {

    public static DefaultListModel scores = new DefaultListModel();

    CardLayout cardLayout;
    JPanel mainPanel;
    public static int xWindowSize = 810;
    public static int yWindowSize = 660;
    public static int voxelSize = 30;
    public static int xError = 10;
    public static int yError = 38;
    public static Frame mainMenuFrame;
    public static Color backgroundColor = new Color(28, 28, 28);

    public void showGameFrame(){
        cardLayout.show(mainPanel, "GameFrame");

    }

    public void showMainMenu(){
        cardLayout.show(mainPanel, "MainMenu");
    }

    public Frame(int choice){
        setTitle("PacMan");
        setSize(xWindowSize + xError,yWindowSize + yError);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(backgroundColor);
        this.setBackground(backgroundColor);

        if(choice == 1){
            Frame.mainMenuFrame = this;
        }

        switch (choice){
            case 1:
                MainMenu mainMenu = new MainMenu();
                Frame.mainMenuFrame.mainPanel.add(mainMenu, "MainMenu");
                break;
            case 2:
                GameFrame1 gameFrame1 = new GameFrame1(this);
                mainPanel.add(gameFrame1, "GameFrame");
                break;
            case  3:
                GameFrame2 gameFrame2 = new GameFrame2(this);
                mainPanel.add(gameFrame2, "GameFrame2");
                break;
            case 4:
                GameFrame3 gameFrame3 = new GameFrame3(this);
                mainPanel.add(gameFrame3, "GameFrame3");
                break;
            case 5:
                GameFrame4 gameFrame4 = new GameFrame4(this);
                mainPanel.add(gameFrame4, "GameFrame4");
                break;
            case 6:
                GameFrame5 gameFrame5 = new GameFrame5(this);
                mainPanel.add(gameFrame5, "GameFrame5");
                break;
        }


        add(mainPanel);

        switch (choice){
            case 1:

                showMainMenu();
                break;
            case 2:

                showGameFrame();
                break;
            case 3:
                cardLayout.show(mainPanel, "GameFrame2");
            case 4:
                cardLayout.show(mainPanel, "GameFrame3");
            case 5:
                cardLayout.show(mainPanel, "GameFrame4");
            case 6:
                cardLayout.show(mainPanel, "GameFrame5");
        }




    }
    public static void addToTheScores(String Name, int score, int time){
        String ans = "";
        ans = ans + Name;
        for(int i = Name.length(); i <= 25; i++) ans = ans + " ";
        ans += ":     " + score + "     " + "Time: " + (time / 60000) + ":" + ((time % 60000) / 1000) + ":" + (time % 1000);
        scores.addElement(ans);
    }


}
