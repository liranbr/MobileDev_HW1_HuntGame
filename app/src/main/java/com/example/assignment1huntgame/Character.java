package com.example.assignment1huntgame;

import android.icu.number.Scale;
import android.widget.ImageView;

public class Character {
    int x;
    int y;
    int spriteUp;
    int spriteDown;
    int spriteLeft; // right is a flipped Left
    int currentSprite;
    ImageView[][] grid;
    public enum Direction{UP, DOWN, LEFT, RIGHT}

    Character(ImageView[][] grid, int x, int y, int spriteUp, int spriteDown, int spriteLeft) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.spriteUp = spriteUp;
        this.spriteDown = spriteDown;
        this.spriteLeft = spriteLeft;
        this.currentSprite = spriteLeft; // facing left as default position
        grid[y][x].setImageResource(currentSprite);
    }

    void move(Direction dir) {
        grid[y][x].setImageResource(0); // [y][x] so that y = up/down and x = left/right, intuitive
        float xScale = grid[y][x].getScaleX(); // preserve sprite facing left/right
        switch (dir) {
            case UP:
                y = Math.max(y - 1, 0);
                break;
            case DOWN:
                y = Math.min(y + 1, grid.length - 1);
                break;
            case LEFT:
                x = Math.max(x - 1, 0);
                break;
            case RIGHT:
                x = Math.min(x + 1, grid[0].length - 1);
                break;
        }
        grid[y][x].setImageResource(currentSprite);
        grid[y][x].setScaleX(xScale);
    }

    void rotateSprite(Direction dir) {
        ImageView me = grid[y][x];
        switch (dir) {
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
}
