public class GameInfo{
    public int grid_check;          //To check if initial state of grid has been submitted
    public int info_check;          //To check if game info has been submitted
    public int dim;                 //Initial dimension of the grid
    public int delay;               //Delay between 2 generations
    public int display_dim;         //Dimension of grid to be displayed

    public void check(){
        grid_check = 0;
        info_check = 0;
        dim = 20;
        delay = 300;
        display_dim = 20;
    }
}