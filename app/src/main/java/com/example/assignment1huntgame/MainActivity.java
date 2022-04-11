package com.example.assignment1huntgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private final int ROWS = 5, COLS = 3, MAX_HEALTH = 3;
    private final int PLAYER_START_ROW = 3, PLAYER_START_COL = 1;
    private final int ENEMY_START_ROW = 1, ENEMY_START_COL = 1;

    private ImageView[][] grid;
    private ImageView[] hearts;
    private Character player;
    private Character enemy;
    private int HP; // Heart Points
    private int score;
    private boolean gameOver, paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeGrid(ROWS, COLS);
        player = new Character(grid, PLAYER_START_ROW, PLAYER_START_COL,
                R.drawable.madeline_up,
                R.drawable.madeline_down,
                R.drawable.madeline_left);
        enemy = new Character(grid, ENEMY_START_ROW, ENEMY_START_COL,
                R.drawable.oshiro_up,
                R.drawable.oshiro_down,
                R.drawable.oshiro_left);
        MaterialButton[] controls = { // order matters - UDLR is 0123 in the Direction enum
                findViewById(R.id.buttonUp),
                findViewById(R.id.buttonDown),
                findViewById(R.id.buttonLeft),
                findViewById(R.id.buttonRight)};
        for (int i = 0; i < controls.length; i++) {
            Character.Direction buttonDirection = Character.Direction.values()[i];
            controls[i].setOnClickListener(view -> {
                paused = false;
                if (!gameOver)
                    player.setDirection(buttonDirection);
            });
        }

        fillHearts(MAX_HEALTH);
        HP = MAX_HEALTH;
        score = 0;
        writeScore(score);
        randomEnemyDirection();
        paused = true; // wait for first input

        // Start running game logic
        Handler clock = new Handler();
        clock.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!paused)
                    gameTick();
                if (!gameOver)
                    clock.postDelayed(this, 1000);
            }
        },1000);
    }

    void gameTick() {
        writeScore(++score);
        player.move();
        boolean collided = player.row == enemy.row && player.col == enemy.col;
        enemy.move();
        collided = collided || (player.row == enemy.row && player.col == enemy.col);
        if (collided) {
            HP--;
            hearts[HP].setVisibility(View.INVISIBLE);
            paused = true;
            if (HP > 0) {
                player.setPosition(PLAYER_START_ROW, PLAYER_START_COL);
                enemy.setPosition(ENEMY_START_ROW, ENEMY_START_COL);
            }
        }
        if (!gameOver && HP <= 0) {
            gameOver = true;
            MaterialButton quitButton = findViewById(R.id.buttonQuit);
            quitButton.setVisibility(View.VISIBLE);
            quitButton.setOnClickListener(view -> {
                finish();
                System.exit(0);
            });
        }
        randomEnemyDirection();
    }

    void randomEnemyDirection() {
        int enemyDirection = new Random().nextInt(4);
        enemy.setDirection(Character.Direction.values()[enemyDirection]);
    }

    void makeGrid(final int rows, final int cols) {
        LinearLayout gridLayout = findViewById(R.id.main_LLO_grid);
        gridLayout.removeAllViews();
        grid = new ImageView[rows][cols];
        LinearLayout[] rowLayouts = new LinearLayout[rows];

        LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        LayoutParams cellParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < rows; i++) {
            rowLayouts[i] = new LinearLayout(this);
            rowLayouts[i].setLayoutParams(rowParams);
            rowLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            gridLayout.addView(rowLayouts[i]);
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new ImageView(this);
                grid[i][j].setLayoutParams(cellParams);
                grid[i][j].setBackgroundResource(R.drawable.grid_cell_background);
                rowLayouts[i].addView(grid[i][j]);
            }
        }
    }

    void fillHearts(final int maxHealth) {
        LinearLayout healthbar = findViewById(R.id.main_LLO_healthbar);
        healthbar.removeAllViews();
        hearts = new ImageView[maxHealth];
        for (int i = 0; i < maxHealth; i++) {
            hearts[i] = new ImageView(this);
            hearts[i].setImageResource(R.drawable.crystal_heart);
            hearts[i].setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
            int pad = 20 - 3 * maxHealth; // to make it look ok for a maxHealth up to ~8
            hearts[i].setPadding(pad, pad, pad, pad);
            healthbar.addView(hearts[i]);
        }
    }

    void writeScore(int score) {
        TextView scoreTXT = findViewById(R.id.main_TXT_score);
        String s = "SCORE: " + score;
        scoreTXT.setText(s);
    }
}