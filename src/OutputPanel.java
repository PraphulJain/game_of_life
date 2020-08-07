import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class OutputPanel extends JPanel{
    //Border blackline;
    public OutputPanel(byte [][]grid, int win_dim){
        super();
        setLayout(new GridLayout(win_dim, win_dim));
        //blackline = BorderFactory.createLineBorder(Color.black);

        int init = (int)(grid.length / 2) - (int)(win_dim / 2);
        for(int i=init; i<init+win_dim; i++){
            for(int j=init; j<init+win_dim; j++){
                JPanel p = new JPanel();
                //p.setBorder(blackline);
                p.setBackground(grid[i][j]==1?Color.black:Color.white);
                add(p);
            }
        }
    }

    //Method to update the frame after a generation
    public void RePaint(byte [][]grid, int win_dim){
        int init = (int)(grid.length / 2) - (int)(win_dim / 2);
        removeAll();
        for(int i=init; i<init+win_dim; i++){
            for(int j=init; j<init+win_dim; j++){
                JPanel p = new JPanel();
                //p.setBorder(blackline);
                p.setBackground(grid[i][j]==1?Color.black:Color.white);
                add(p);
            }
        }

        revalidate();
        repaint();
    }
}
