import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourGUI extends JFrame {
    private static final int ROWS = 6, COLUMNS = 7;
    private static final char EMPTY = '.', PLAYER_ONE = 'X', PLAYER_TWO = 'O';
    private static char[][] board = new char[ROWS][COLUMNS];
    private JButton[] buttons = new JButton[COLUMNS];
    private JLabel[][] cells = new JLabel[ROWS][COLUMNS];
    private boolean isPlayerOneTurn = true;

    public ConnectFourGUI() {
        initializeBoard();
        setTitle("Connect Four");
        setSize(700, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, COLUMNS));
        for (int col = 0; col < COLUMNS; col++) {
            buttons[col] = new JButton(String.valueOf(col + 1));
            final int column = col;
            buttons[col].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleMove(column);
                }
            });
            topPanel.add(buttons[col]);
        }

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLUMNS));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col] = new JLabel(String.valueOf(EMPTY), SwingConstants.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardPanel.add(cells[row][col]);
            }
        }

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY;
            }
        }
    }

    private void handleMove(int column) {
        if (dropDisc(isPlayerOneTurn ? PLAYER_ONE : PLAYER_TWO, column)) {
            updateBoard();
            if (checkForWin()) {
                JOptionPane.showMessageDialog(this, "Player " + (isPlayerOneTurn ? "1 (X)" : "2 (O)") + " wins!");
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetGame();
            } else {
                isPlayerOneTurn = !isPlayerOneTurn;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Column is full. Try a different column.");
        }
    }

    private boolean dropDisc(char disc, int column) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == EMPTY) {
                board[row][column] = disc;
                return true;
            }
        }
        return false;
    }

    private void updateBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }

    private boolean checkForWin() {
        // Check horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col] != EMPTY && board[row][col] == board[row][col + 1] && board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return true;
                }
            }
        }
        // Check vertical
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] != EMPTY && board[row][col] == board[row + 1][col] && board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return true;
                }
            }
        }
        // Check diagonal (bottom left to top right)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col] != EMPTY && board[row][col] == board[row - 1][col + 1] && board[row][col] == board[row - 2][col + 2] && board[row][col] == board[row - 3][col + 3]) {
                    return true;
                }
            }
        }
        // Check diagonal (top left to bottom right)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col] != EMPTY && board[row][col] == board[row + 1][col + 1] && board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int col = 0; col < COLUMNS; col++) {
            if (board[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        initializeBoard();
        updateBoard();
        isPlayerOneTurn = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnectFourGUI game = new ConnectFourGUI();
            game.setVisible(true);
        });
    }
}

    

