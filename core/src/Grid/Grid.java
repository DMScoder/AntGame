package Grid;

/**
 * Created by Immortan on 1/21/2016.
 */
public class Grid {

    public final int size;
    public Cell[][] cells;

    public Grid(int size)
    {
        this.size=size;
        createGrid();
    }

    private void createGrid()
    {
        cells = new Cell[size][size];
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                cells[i][j] = new Cell(i,j);
    }
}
