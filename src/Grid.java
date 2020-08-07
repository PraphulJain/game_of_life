public class Grid {
    byte [][]grid;

    //constructor
    public Grid(byte [][]g){
        grid = g;
        Add();
    }

    //Method to be called to return the next generation of cells
    public byte[][] NextGen(){
        //Check if we need to add a layer and add if yes
        for(int i=2; i<grid.length-2; i++){
            if((grid[2][i]==1 && grid[2][i-1]==1 && grid[2][i+1]==1)                                                //For first row
                    || (grid[grid.length-2][i]==1 && grid[grid.length-2][i-1]==1 && grid[grid.length-2][i+1]==1)    //For last row
                    || (grid[i][2]==1 && grid[i-1][2]==1 && grid[i+1][2]==1)                                        //For first column
                    || (grid[i][grid.length-2]==1 && grid[i-1][grid.length-2]==1 && grid[i+1][grid.length-2]==1)){  //For last column
                Add();
                break;
            }
        }


        //iterate through the list and find next gen cells
        byte [][]clone = new byte[grid.length][grid.length];
        for(int i=1; i<grid.length-1; i++){
            for(int j=1; j<grid.length-1; j++){
                int neighbours = LiveNeighbours(i, j);
                //The rules
                if(grid[i][j] == 0 && neighbours == 3) clone[i][j] = 1;
                else if(grid[i][j] == 1 && (neighbours < 2 || neighbours > 3)) clone[i][j] = 0;
                else clone[i][j] = grid[i][j];
            }
        }
        grid = clone;
        return grid;
    }

    //adding one layer at the edges
    private void Add(){
        byte [][]clone = new byte[grid.length+2][grid.length+2];
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid.length; j++){
                clone[i+1][j+1] = grid[i][j];
            }
        }
        grid = clone;
    }

    //Number of neighbours
    private int LiveNeighbours(int i, int j){
        return(grid[i-1][j-1] + grid[i-1][j] + grid[i-1][j+1] +
                grid[i][j-1]        +          grid[i][j+1] +
                grid[i+1][j-1] + grid[i+1][j] + grid[i+1][j+1]);
    }
}