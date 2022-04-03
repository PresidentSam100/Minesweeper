import java.util.Arrays;

public class Board
{
    private String[][] board;
    private boolean[][] showing, down, flagged, question;
    private int rows, cols, mines;

    public Board(int rows, int cols, int mines)
    {
        newBoard(rows, cols, mines);
    }

    public void newBoard(int rows, int cols, int mines)
    {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        board = new String[rows][cols];
        showing = new boolean[rows][cols];
        down = new boolean[rows][cols];
        flagged = new boolean[rows][cols];
        question = new boolean[rows][cols];
        for (String[] array : board)
            Arrays.fill(array,"0");
        while (mines > 0)
        {
            int row = (int)(Math.random() * rows);
            int col = (int)(Math.random() * cols);
            if (!board[row][col].equals("M"))
            {
                mines--;
                board[row][col] = "M";
                if (row > 0 && col > 0 && !board[row - 1][col - 1].equals("M"))
                    board[row - 1][col - 1] = Integer.toString(Integer.parseInt(board[row - 1][col - 1]) + 1);
                if (row > 0 && !board[row - 1][col].equals("M"))
                    board[row - 1][col] = Integer.toString(Integer.parseInt(board[row - 1][col]) + 1);
                if (row > 0 && col < cols - 1 && !board[row - 1][col + 1].equals("M"))
                    board[row - 1][col + 1] = Integer.toString(Integer.parseInt(board[row - 1][col + 1]) + 1);
                if (col < cols - 1 && !board[row][col + 1].equals("M"))
                    board[row][col + 1] = Integer.toString(Integer.parseInt(board[row][col + 1]) + 1);
                if (row < rows - 1 && col < cols - 1 && !board[row + 1][col + 1].equals("M"))
                    board[row + 1][col + 1] = Integer.toString(Integer.parseInt(board[row + 1][col + 1]) + 1);
                if (row < rows - 1 && !board[row + 1][col].equals("M"))
                    board[row + 1][col] = Integer.toString(Integer.parseInt(board[row + 1][col]) + 1);
                if (row < rows - 1 && col > 0 && !board[row + 1][col - 1].equals("M"))
                    board[row + 1][col - 1] = Integer.toString(Integer.parseInt(board[row + 1][col - 1]) + 1);
                if (col > 0 && !board[row][col - 1].equals("M"))
                    board[row][col - 1] = Integer.toString(Integer.parseInt(board[row][col - 1]) + 1);
            }
        }
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public int getMines()
    {
        return mines;
    }

    public boolean isMine(int row, int col)
    {
        return board[row][col].equals("M");
    }

    public boolean isShowing(int row, int col)
    {
        return showing[row][col];
    }

    public void setShowing(int row, int col, boolean showing)
    {
        this.showing[row][col] = showing;
        if (board[row][col].equals("0"))
        {
            if (row > 0 && col > 0 && allFalse(row - 1, col - 1))
                setShowing(row - 1, col - 1, showing);
            if (row > 0 && allFalse(row - 1, col))
                setShowing(row - 1, col, showing);
            if (row > 0 && col < cols - 1 && allFalse(row - 1, col + 1))
                setShowing(row - 1, col + 1, showing);
            if (col < cols - 1 && allFalse(row, col + 1))
                setShowing(row, col + 1, showing);
            if (row < rows - 1 && col < cols - 1 && allFalse(row + 1, col + 1))
                setShowing(row + 1, col + 1, showing);
            if (row < rows - 1 && allFalse(row + 1, col))
                setShowing(row + 1, col, showing);
            if (row < rows - 1 && col > 0 && allFalse(row + 1, col - 1))
                setShowing(row + 1, col - 1, showing);
            if (col > 0 && allFalse(row, col - 1))
                setShowing(row, col - 1, showing);
        }
    }

    public boolean isDown(int row, int col)
    {
        return down[row][col];
    }

    public void setDown(int row, int col, boolean down)
    {
        this.down[row][col] = down;
    }

    public boolean isFlagged(int row, int col)
    {
        return flagged[row][col];
    }

    public void setFlagged(int row, int col, boolean flagged)
    {
        this.flagged[row][col] = flagged;
    }

    public int getNumFlags()
    {
        int flags = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (flagged[r][c])
                    flags++;
        return flags;
    }

    public boolean isQuestion(int row, int col)
    {
        return question[row][col];
    }

    public void setQuestion(int row, int col, boolean question)
    {
        this.question[row][col] = question;
    }

    public boolean win()
    {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!isMine(r, c) && !isShowing(r, c))
                    return false;
        return true;
    }

    public int getNumOfNearMines(int row, int col)
    {
        int mines = 0;
        if (row > 0 && col > 0 && board[row - 1][col - 1].equals("M"))
            mines++;
        if (row > 0 && board[row - 1][col].equals("M"))
            mines++;
        if (row > 0 && col < cols - 1 && board[row - 1][col + 1].equals("M"))
            mines++;
        if (col < cols - 1 && board[row][col + 1].equals("M"))
            mines++;
        if (row < rows - 1 && col < cols - 1 && board[row + 1][col + 1].equals("M"))
            mines++;
        if (row < rows - 1 && board[row + 1][col].equals("M"))
            mines++;
        if (row < rows - 1 && col > 0 && board[row + 1][col - 1].equals("M"))
            mines++;
        if (col > 0 && board[row][col - 1].equals("M"))
            mines++;
        return mines;
    }

    public boolean allFalse(int row, int col)
    {
        return !isShowing(row,col) && !isFlagged(row,col) && !isQuestion(row,col);
    }

    public void flagAll()
    {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (isMine(r, c) && !isFlagged(r, c))
                    flagged[r][c] = true;
    }

    public String toString()
    {
        String s = "";
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                s += board[r][c] + " ";
            }
            s += "\n";
        }
        return s;
    }
}