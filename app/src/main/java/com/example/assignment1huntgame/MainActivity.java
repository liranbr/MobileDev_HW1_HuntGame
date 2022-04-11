package com.example.assignment1huntgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int rows = 5, cols = 3, maxHealth = 3;
        int HP = maxHealth; // Heart Points
        ImageView[][] grid = makeGrid(rows, cols);
        ImageView[] hearts = fillHearts(maxHealth);
        int score = 0;
        writeScore(score);
        Character player = new Character(grid, 1, 3,
                R.drawable.madeline_up,
                R.drawable.madeline_down,
                R.drawable.madeline_left);
        Character enemy = new Character(grid, 1, 1,
                R.drawable.oshiro_up,
                R.drawable.oshiro_down,
                R.drawable.oshiro_left);

        MaterialButton[] controls = { // order matters - UDLR is 0123 in the Direction enum
                findViewById(R.id.buttonUp),
                findViewById(R.id.buttonDown),
                findViewById(R.id.buttonLeft),
                findViewById(R.id.buttonRight)};

        for (int i = 0; i < controls.length; i++) {
            Character.Direction dir = Character.Direction.values()[i];
            controls[i].setOnClickListener(view -> {
                player.rotateSprite(dir);
                player.move(dir);
                player.rotateSprite(dir);
            });
        }


        player.move(Character.Direction.RIGHT);
        player.rotateSprite(Character.Direction.RIGHT);
//        HP = takeDamage(hearts, HP, 1);
    }

    ImageView[][] makeGrid(final int rows, final int cols) {
        LinearLayout gridLayout = findViewById(R.id.main_LLO_grid);
        gridLayout.removeAllViews();
        ImageView[][] grid = new ImageView[rows][cols];
        LinearLayout[] rowLayouts = new LinearLayout[rows];

        LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        for (int i = 0; i < rows; i++) {
            rowLayouts[i] = new LinearLayout(this);
            rowLayouts[i].setLayoutParams(rowParams);
            rowLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            gridLayout.addView(rowLayouts[i]);
        }
        LayoutParams cellParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new ImageView(this);
                grid[i][j].setLayoutParams(cellParams);
                grid[i][j].setBackgroundResource(R.drawable.grid_cell_background);
                rowLayouts[i].addView(grid[i][j]);
            }
        }
        return grid;
    }

    ImageView[] fillHearts(final int maxHealth) {
        LinearLayout healthbar = findViewById(R.id.main_LLO_healthbar);
        healthbar.removeAllViews();
        ImageView[] hearts = new ImageView[maxHealth];
        for (int i = 0; i < maxHealth; i++) {
            hearts[i] = new ImageView(this);
            hearts[i].setImageResource(R.drawable.crystal_heart);
            hearts[i].setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
            int pad = 20 - 3 * maxHealth; // to make it look ok for a maxHealth up to ~8
            hearts[i].setPadding(pad, pad, pad, pad);
            healthbar.addView(hearts[i]);
        }
        return hearts;
    }

    int takeDamage(ImageView[] hearts, int HP, int damage) {
        for (int i = HP - damage; i < HP; i++)
            hearts[i].setImageResource(0);
        return HP - damage;
    }

    void writeScore(int score) {
        TextView scoreTXT = findViewById(R.id.main_TXT_score);
        String s = "SCORE: " + score;
        scoreTXT.setText(s);
    }
}