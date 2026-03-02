import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame implements ActionListener {

    String player = "X";
    int moveCnt = 0;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;
    private static final int ROW = 3;
    private static final int COL = 3;
    private String[][] board = new String[ROW][COL];
    private TicTacToeTile[][] tiles = new TicTacToeTile[3][3];

    public TicTacToeFrame(){
        clearBoard();
        setLayout(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(3,3));


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                tiles[row][col] = new TicTacToeTile(row, col);
                tiles[row][col].setText(" ");
                tiles[row][col].addActionListener(this);
                boardPanel.add(tiles[row][col]);
            }
        }


        JPanel btnPanel = new JPanel();
        JButton quitBtn = new JButton("Quit");
        quitBtn.addActionListener(e -> System.exit(0));
        btnPanel.add(quitBtn);

        add(boardPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        }
    @Override
    public void actionPerformed(ActionEvent e){
        TicTacToeTile tile = (TicTacToeTile) e.getSource();
        int row = tile.getRow();
        int col = tile.getCol();

        if(isValidMove(row, col)){
            board[row][col] = player;
            tiles[row][col].setText(player);
            moveCnt++;

            if(moveCnt >= MOVES_FOR_WIN) {
                if(isWin(player)){
                    int response = JOptionPane.showConfirmDialog(this,"Player " + player + " wins! \n play again?");
                    if(response == JOptionPane.YES_OPTION)
                        resetBoard();
                    else
                        System.exit(0);
                }
            }
            if(moveCnt >= MOVES_FOR_TIE){
                if(isTie()){
                   int response = JOptionPane.showConfirmDialog(this,"It's a tie! \n Play again?");
                   if (response == JOptionPane.YES_OPTION)
                       resetBoard();
                   else
                       System.exit(0);
                }
            }
            if(player.equals("X"))
                player = "O";
            else
                player = "X";
        }else{
            JOptionPane.showMessageDialog(this, "Invalid move, try again!");
        }
    }

    private boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player) )
        {
            return true;
        }
        if(board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player) )
        {
            return true;
        }
        return false;
    }
    private boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private  boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private void resetBoard(){
        for(int row = 0; row < ROW; row++){
            for(int col = 0; col < COL; col++){
                board[row][col] = " ";
                tiles[row][col].setText(" ");
            }
        }
        moveCnt = 0;
        player = "X";
    }
        private boolean isValidMove(int row, int col) {
            boolean retVal = false;
            if (board[row][col].equals(" "))
                retVal = true;

            return retVal;
        }

    private boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }

    private boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals("X") ||
                    board[row][1].equals("X") ||
                    board[row][2].equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].equals("O") ||
                    board[row][1].equals("O") ||
                    board[row][2].equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals("X") ||
                    board[1][col].equals("X") ||
                    board[2][col].equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].equals("O") ||
                    board[1][col].equals("O") ||
                    board[2][col].equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].equals("X") ||
                board[1][1].equals("X") ||
                board[2][2].equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].equals("O") ||
                board[1][1].equals("O") ||
                board[2][2].equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].equals("X") ||
                board[1][1].equals("X") ||
                board[2][0].equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].equals("O") ||
                board[1][1].equals("O") ||
                board[2][0].equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }
    private void clearBoard()
    {
        // sets all the board elements to a space
        for(int row=0; row < ROW; row++)
        {
            for(int col=0; col < COL; col++)
            {
                board[row][col] = " ";
            }
        }
    }


        public static void main(String[] args) {
                TicTacToeFrame frame = new TicTacToeFrame();
    }
}


