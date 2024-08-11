import javax.swing.*;
import java.awt.*;

public class CollectablePoint extends JPanel {
    static int xSize = 8;
    static int ySize = 8;
    JLabel pointLabel;
    public int xPosition;
    public int yPosition;

    public CollectablePoint(int xPosition, int yPosition){

        setLayout(null);

        this.xPosition = xPosition;
        this.yPosition = yPosition;

        pointLabel = new JLabel();
        pointLabel.setOpaque(true);
        pointLabel.setBackground(Color.yellow);
        pointLabel.setBounds(0,0, xSize, ySize);
        add(pointLabel);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Frame.xWindowSize, Frame.yWindowSize);
    }
}
