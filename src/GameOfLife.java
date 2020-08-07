import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
    public static void main(String []args) throws InterruptedException {
        Scanner pj = new Scanner(System.in);
        //Creating an object for info of game
        GameInfo info = new GameInfo();
        //Taking and Updating the game info
        InputGameInfo(info);

        //Taking initial state of the grid
        byte [][]state = InputGrid(info);

        //Passing the state to the iterating and displaying method
        DisplayLoop(state, info);
    }

    //Method to take input the game info
    private static void InputGameInfo(GameInfo info){
        JFrame f = new JFrame("Game of life");

        //Label and field for dimension of the initial grid
        JLabel dim_label = new JLabel("Enter the dimension of the initial state of the grid (enter max of rows or columns if have rectangular initial state): ");
        dim_label.setBounds(30, 30, 800, 20);
        f.add(dim_label);
        JTextField dim_str = new JTextField();
        dim_str.setBounds(30, 60, 100, 20);
        f.add(dim_str);

        //Label and field for delay
        JLabel delay_label = new JLabel("Enter the time delay between each state (in milliseconds): ");
        delay_label.setBounds(30, 100, 800, 20);
        f.add(delay_label);
        JTextField delay_str = new JTextField();
        delay_str.setBounds(30, 130, 100, 20);
        f.add(delay_str);

        //Label and field for dimension of display frame
        JLabel display_dim_label = new JLabel("Enter the dimension of grid to be displayed: ");
        display_dim_label.setBounds(30, 170, 800, 20);
        f.add(display_dim_label);
        JTextField display_dim_str = new JTextField();
        display_dim_str.setBounds(30, 200, 100, 20);
        f.add(display_dim_str);

        JButton submit = new JButton("Next");
        submit.setBounds(30, 240, 80, 30);
        InfoListener ls = new InfoListener(info, f, dim_str, delay_str, display_dim_str);
        submit.addActionListener(ls);
        f.add(submit);

        f.setSize(850, 800);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        //Waiting for user to click submit
        while(info.info_check == 0){
            System.out.print("");
        }
    }


    //Action listener class for info input submit button
    private static class InfoListener implements ActionListener{
        GameInfo info;
        JFrame f;
        JTextField dim_str, delay_str, display_dim_str;

        public InfoListener(GameInfo info, JFrame f, JTextField dim_str, JTextField delay_str, JTextField display_dim_str){
            this.f = f;
            this.dim_str = dim_str;
            this.delay_str = delay_str;
            this.display_dim_str = display_dim_str;
            this.info = info;
        }

        public void actionPerformed(ActionEvent e) {
            info.dim = Integer.parseInt(dim_str.getText());
            info.delay = Integer.parseInt(delay_str.getText());
            info.display_dim = Integer.parseInt(display_dim_str.getText());

            f.removeAll();
            f.setVisible(false);
            f.dispose();
            info.info_check = 1;
        }
    }


    //Method to take the initial grid as input
    private static byte[][] InputGrid(GameInfo info){
        byte [][]state;
        if(info.display_dim > info.dim)
            state = new byte[info.display_dim][info.display_dim];
        else
            state = new byte[info.dim][info.dim];

        JFrame f = new JFrame("Game of life");

        JLabel text = new JLabel("Select the cells which are alive and press start");
        text.setBounds(50, 50, 600, 20);
        f.add(text);

        //Creating panel and enabling scrolling
        InputInitialGridPanel fm = new InputInitialGridPanel(state, info.dim);
        //fm.setSize(info.dim * 5, info.dim * 5);
        JScrollPane scroll = new JScrollPane(fm);
        if(info.dim <= 20)
            scroll.setBounds(50, 90, info.dim * 30, info.dim * 30);
        else if(info.dim < 24)
            scroll.setBounds(50, 90, info.dim * 24, info.dim * 24);
        else
            scroll.setBounds(50, 90, 600, 600 );
        f.add(scroll);

        JButton submit = new JButton("Start");
        if(info.dim <= 20)
            submit.setBounds(50, 110 + (30 * info.dim), 80, 30);
        else if(info.dim < 24)
            submit.setBounds(50, 110 + (24 * info.dim), 80, 30);
        else
            submit.setBounds(50, 700, 80, 30);
        GridListener ls = new GridListener(f, state, fm, info);
        submit.addActionListener(ls);
        f.add(submit);

        f.setSize(800, 800);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        //Waiting for user to press start
        while(info.grid_check == 0){
            System.out.print("");
        }

        return state;
    }


    //Action listener for Start button
    private static class GridListener implements ActionListener{
        JFrame f;
        byte [][]state;
        InputInitialGridPanel panel;
        GameInfo info;

        public GridListener(JFrame f, byte [][]state, InputInitialGridPanel panel, GameInfo info){
            this.f = f;
            this.state = state;
            this.panel = panel;
            this.info = info;
        }

        public void actionPerformed(ActionEvent e) {
            int init = (int)(state.length / 2) - (int)(panel.cb.length / 2);
            for(int i=0; i<panel.cb.length; i++){
                for(int j=0; j<panel.cb.length; j++){
                    if(panel.cb[i][j].isSelected()){
                        state[init + i][init + j] = 1;
                    }
                }
            }
            f.removeAll();
            f.setVisible(false);
            f.dispose();
            info.grid_check = 1;
        }
    }


    //Method to iterate and display the grids
    private static void DisplayLoop(byte[][] state, GameInfo info) throws InterruptedException {
        //Display initial state
        JFrame frame_of_life = new JFrame("Game of life");
        frame_of_life.setLayout(new GridLayout(1, 1));
        OutputPanel p = new OutputPanel(state, info.display_dim);
        frame_of_life.add(p);
        frame_of_life.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_of_life.setSize(800, 800);
        frame_of_life.setVisible(true);

        Grid grid_object = new Grid(state);
        for(;;){
            state = grid_object.NextGen();

            TimeUnit.MILLISECONDS.sleep(info.delay);

            p.RePaint(state, info.display_dim);

            /*for(int i=0; i<state.length; i++){
                for(int j=0; j<state.length; j++){
                    System.out.print(state[i][j]);
                }
                System.out.println("");
            }
            System.out.println("---------------------------------------------------------------------------");*/
        }
    }
}