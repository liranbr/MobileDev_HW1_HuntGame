package com.example.assignment1huntgame;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.graphics.Matrix;

public class Character {
    int x;
    int y;
    int spriteUp;
    int spriteDown;
    int spriteLeft; // right is a flipped Left
    int image;
    ImageView[][] grid;

    Character(ImageView[][] grid, int x, int y, int spriteUp, int spriteDown, int spriteLeft) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.spriteUp = spriteUp;
        this.spriteDown = spriteDown;
        this.spriteLeft = spriteLeft;
        this.image = spriteLeft; // facing left as default position
        grid[y][x].setImageResource(image);
    }

    void move(int xMovement, int yMovement) {
        grid[y][x].setImageResource(0); // [y][x] so that y = up/down and x = left/right, intuitive
        y = Math.max(0, Math.min(grid.length-1, y + yMovement)); // stay within grid limits
        x = Math.max(0, Math.min(grid[0].length-1, x + xMovement));
        grid[y][x].setImageResource(image);
    }

    void spriteDirection(int xDirection, int yDirection) {
        ImageView me = grid[y][x];
        if (xDirection != 0) {
            me.setImageResource(spriteLeft);
            me.setScaleX(xDirection);
        }
        if (yDirection == 1)
            me.setImageResource(spriteUp);
        else if (yDirection == -1)
            me.setImageResource(spriteDown);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
