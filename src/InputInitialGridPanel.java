import javax.swing.*;
import java.awt.*;

public class InputInitialGridPanel extends JPanel {
    public JCheckBox [][]cb;
    private byte [][]grid;

    public InputInitialGridPanel(byte [][]grid, int dim){
        super();
        setLayout(new FlowLayout());

        this.grid = grid;
        cb = new JCheckBox[dim][dim];
        setLayout(new GridLayout(dim, dim, 0, 0));
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                cb[i][j] = new JCheckBox();
                //cb[i][j] = new JCheckBox(String.format("(%d, %d)", i+1, j+1));
                Icon unselected;
                Icon selected;
                if(dim <= 20){
                    unselected = new ImageIcon(getClass().getResource("unselected20.png"));
                    selected = new ImageIcon(getClass().getResource("selected20.png"));
                }
                else{
                    unselected = new ImageIcon(getClass().getResource("unselected15.png"));
                    selected = new ImageIcon(getClass().getResource("selected15.png"));
                }
                cb[i][j].setIcon(unselected);
                cb[i][j].setSelectedIcon(selected);
                add(cb[i][j]);
            }
        }

    }
}
