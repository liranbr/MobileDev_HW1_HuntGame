package com.example.assignment1huntgame;

import android.view.View;
import android.widget.ImageView;

public class Character {
    int row;
    int col;
    int spriteUp;
    int spriteDown;
    int spriteLeft; // right is a flipped Left
    int currentSprite;
    ImageView[][] grid;

    public enum Direction{UP, DOWN, LEFT, RIGHT}
    Direction direction;

    Character(ImageView[][] grid, int startRow, int startCol,
              int spriteUp,
              int spriteDown,
              int spriteLeft) {
        this.grid = grid;
        this.row = startRow;
        this.col = startCol;
        this.spriteUp = spriteUp;
        this.spriteDown = spriteDown;
        this.spriteLeft = spriteLeft;

        direction = Direction.LEFT; // left as default position
        this.currentSprite = spriteLeft;
        grid[row][col].setImageResource(currentSprite);
    }

    void move() {
        grid[row][col].setImageResource(0);
        float xScale = grid[row][col].getScaleX(); // preserve sprite facing left/right
        switch (direction) {
            case UP:
                row = Math.max(row - 1, 0);
                break;
            case DOWN:
                row = Math.min(row + 1, grid.length - 1);
                break;
            case LEFT:
                col = Math.max(col - 1, 0);
                break;
            case RIGHT:
                col = Math.min(col + 1, grid[0].length - 1);
                break;
        }
        grid[row][col].setImageResource(currentSprite);
        grid[row][col].setScaleX(xScale);
    }

    void setDirection(Direction direction) {
        this.direction = direction;
        ImageView me = grid[row][col];
        switch (this.direction) {
            case UP:
                currentSprite = spriteUp;
                break;
            case DOWN:
                currentSprite = spriteDown;
                break;
            case LEFT:
                currentSprite = spriteLeft;
                me.setScaleX(1);
                break;
            case RIGHT:
                currentSprite = spriteLeft;
                me.setScaleX(-1);
                break;
        }
        me.setImageResource(currentSprite);
    }

    public void setPosition(int newRow, int newCol) {
        grid[row][col].setImageResource(0);
        float xScale = grid[row][col].getScaleX(); // preserve sprite facing left/right
        row = newRow;
        col = newCol;
        grid[row][col].setImageResource(spriteLeft);
        grid[row][col].setScaleX(xScale);
    }
}
