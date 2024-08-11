import javax.swing.*;

public class MainMenu extends JPanel {
    public MainMenu(){
        setLayout(null);

        this.setBackground(Frame.backgroundColor);

        ImageIcon PlayButtonIcon = new ImageIcon("textures/PlayButton.png");
        ImageIcon BackButtonIcon = new ImageIcon("textures/BackButton.png");
        ImageIcon EmptyLevelIcon = new ImageIcon("textures/EmptyLevelButton.png");
        ImageIcon Level1Icon = new ImageIcon("textures/Level1Button.png");
        ImageIcon ExitIcon = new ImageIcon("textures/MenuExitButton.png");
        ImageIcon ScoresIcon = new ImageIcon("textures/ScoresButton.png");

        JButton back = new JButton();
        back .setBounds(40,10,160,80);

        JButton back2 = new JButton();
        back2.setBounds(40,10,160,80);

        JButton startGame1 = new JButton();
        startGame1.setBounds(15,200,250,150);

        JButton startGame2 = new JButton();
        startGame2.setBounds(280,200,250,150);

        JButton startGame3 = new JButton();
        startGame3.setBounds(545,200,250,150);

        JButton startGame4 = new JButton();
        startGame4.setBounds(145,400,250,150);

        JButton startGame5 = new JButton();
        startGame5.setBounds(415,400,250,150);

        JButton play = new JButton();
        play.setBounds(250,150,310,100);

        JButton scores = new JButton();
        scores.setBounds(250,270,310,100);

        JButton exit = new JButton();
        exit.setBounds(250,390,310,100);

        back2.setIcon(BackButtonIcon);
        play.setIcon(PlayButtonIcon);
        back.setIcon(BackButtonIcon);
        exit.setIcon(ExitIcon);
        scores.setIcon(ScoresIcon);
        startGame1.setIcon(Level1Icon);
        startGame2.setIcon(EmptyLevelIcon);
        startGame3.setIcon(EmptyLevelIcon);
        startGame4.setIcon(EmptyLevelIcon);
        startGame5.setIcon(EmptyLevelIcon);

        JList<String> scoresList = new JList<>(Frame.scores);
        scoresList.setBounds(200, 150, 410, 350);
        JScrollPane scrollPane = new JScrollPane(scoresList);
        scrollPane.setBounds(610, 150, 20, 350);

        Frame.addToTheScores("gamer 1", 10000, 1245260);
        Frame.addToTheScores("gamer 2", 30000, 246280);
        Frame.addToTheScores("gamer 3", 1000, 33520);

        scores.addActionListener(e -> {
            remove(play);
            remove(scores);
            remove(exit);
            add(scoresList);
            add(back2);
            add(scrollPane);
            repaint();
        });

        back2.addActionListener(e -> {
            remove(scoresList);
            remove(scrollPane);
            remove(back2);
            add(play);
            add(scores);
            add(exit);
            repaint();
        });

        exit.addActionListener(e -> {
            System.exit(0);
        });
        play.addActionListener(e -> {
            remove(play);
            remove(scores);
            remove(exit);
            add(startGame2);
            add(startGame3);
            add(startGame1);
            add(startGame4);
            add(startGame5);
            add(back);
            repaint();
        });

        back.addActionListener(e -> {
            remove(startGame1);
            remove(startGame2);
            remove(startGame3);
            remove(startGame4);
            remove(startGame5);
            remove(back);
            add(play);
            add(scores);
            add(exit);
            repaint();
        });

        startGame1.addActionListener(e -> {
            Frame frame2 = new Frame(2);
            frame2.setVisible(true);
            Frame.mainMenuFrame.setVisible(false);
        });

        startGame2.addActionListener(e -> {
            Frame frame2 = new Frame(3);
            frame2.setVisible(true);
            Frame.mainMenuFrame.setVisible(false);
        });

        startGame3.addActionListener(e -> {
            Frame frame2 = new Frame(4);
            frame2.setVisible(true);
            Frame.mainMenuFrame.setVisible(false);
        });
        startGame4.addActionListener(e -> {
            Frame frame2 = new Frame(5);
            frame2.setVisible(true);
            Frame.mainMenuFrame.setVisible(false);
        });
        startGame5.addActionListener(e -> {
            Frame frame2 = new Frame(6);
            frame2.setVisible(true);
            Frame.mainMenuFrame.setVisible(false);
        });

        add(play);
        add(scores);
        add(exit);

    }


}
