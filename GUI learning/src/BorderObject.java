import javax.swing.*;
import java.awt.*;

public class BorderObject extends JPanel {
    JLabel rectangleLabel;
    int xPosition;
    int yPosition;
    int xSize;
    int ySize;

    public BorderObject(int xPosition, int yPosition, int sizeX, int sizeY, ImageIcon wallIcon){

        setLayout(null);

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        xSize = sizeX;
        ySize = sizeY;



        rectangleLabel = new JLabel(wallIcon);
        rectangleLabel.setOpaque(true);
        rectangleLabel.setBounds(this.xPosition, this.yPosition, xSize, ySize);
        add(rectangleLabel);



    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Frame.xWindowSize, Frame.yWindowSize);
    }
}