package org.example.utils;

import java.util.Random;

public class GameBoard {
    private final char[][] board;
    private final int rows;
    private final int cols;
    private int startX, startY, stopX, stopY;
    private static final char START = 'A';
    private static final char STOP = 'B';
    private static final char OBSTACLE = 'X';
    private static final char EMPTY = '.';

    public GameBoard(int rows, int cols) {
        if (rows < 5 || cols < 5) {
            throw new IllegalArgumentException("The board size must be at least 5x5.");
        }
        this.rows = rows;
        this.cols = cols;
        this.board = new char[rows][cols];
        initializeBoard();
        placeStartAndStop();
        placeObstacles();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void placeStartAndStop() {
        Random random = new Random();
        // Place START
        do {
            startX = random.nextInt(rows);
            startY = random.nextInt(2) == 0 ? 0 : cols - 1; // Left or right edge
        } while (board[startX][startY] != EMPTY);
        board[startX][startY] = START;

        // Place STOP
        do {
            stopX = random.nextInt(rows);
            stopY = random.nextInt(2) == 0 ? 0 : cols - 1; // Left or right edge
        } while ((stopX == startX && stopY == startY) || Math.abs(stopX - startX) + Math.abs(stopY - startY) <= 1);
        board[stopX][stopY] = STOP;
    }

    private void placeObstacles() {
        Random random = new Random();
        int obstacleCount = (rows * cols) / 5; // 20% of the board as obstacles
        while (obstacleCount > 0) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (board[x][y] == EMPTY) {
                board[x][y] = OBSTACLE;
                obstacleCount--;
            }
        }
    }

    public boolean canMove(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && board[x][y] != OBSTACLE;
    }

    public boolean moveUp(int[] position) {
        int newX = position[0] - 1;
        int newY = position[1];
        if (canMove(newX, newY)) {
            position[0] = newX;
            return true;
        }
        return false;
    }

    public boolean moveDown(int[] position) {
        int newX = position[0] + 1;
        int newY = position[1];
        if (canMove(newX, newY)) {
            position[0] = newX;
            return true;
        }
        return false;
    }

    public boolean moveLeft(int[] position) {
        int newX = position[0];
        int newY = position[1] - 1;
        if (canMove(newX, newY)) {
            position[1] = newY;
            return true;
        }
        return false;
    }

    public boolean moveRight(int[] position) {
        int newX = position[0];
        int newY = position[1] + 1;
        if (canMove(newX, newY)) {
            position[1] = newY;
            return true;
        }
        return false;
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(10, 10);
        gameBoard.printBoard();

        // Example testing movement
        int[] position = {gameBoard.startX, gameBoard.startY};
        System.out.println("Starting position: (" + position[0] + ", " + position[1] + ")");

        if (gameBoard.moveRight(position)) {
            System.out.println("Moved right to: (" + position[0] + ", " + position[1] + ")");
        } else {
            System.out.println("Cannot move right.");
        }
    }
}
