package ru.myitschool;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static boolean isLeft = false;
    public static boolean gameOver = false;
    public static boolean isRight = false;
    public static boolean isUp = false;
    public static boolean isDown = false;
    public static boolean staminaOn = false;
    public static boolean start = true;
    int gameNumber = 0;
    Game game;
    public static boolean attacked;

    Button leftButton;
    Button rightButton;
    Button upButton;
    Button downButton;
    Button boost;
    Button attack;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout gameLayout = (ConstraintLayout) findViewById(R.id.gameLayout);
        game = new Game(this);
        gameLayout.addView(game);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        boost = findViewById(R.id.boost);
        attack = findViewById(R.id.attack);
        startButton = findViewById(R.id.start);
        leftButton.setOnTouchListener(this);
        rightButton.setOnTouchListener(this);
        upButton.setOnTouchListener(this);
        downButton.setOnTouchListener(this);
        boost.setOnTouchListener(this);
        attack.setOnTouchListener(this);
        leftButton.setVisibility(View.INVISIBLE);
        rightButton.setVisibility(View.INVISIBLE);
        upButton.setVisibility(View.INVISIBLE);
        downButton.setVisibility(View.INVISIBLE);
        boost.setVisibility(View.INVISIBLE);
        attack.setVisibility(View.INVISIBLE);
        startButton.setOnClickListener(Listener);
    }

    void onPlay() {
        leftButton.setVisibility(View.VISIBLE);
        rightButton.setVisibility(View.VISIBLE);
        upButton.setVisibility(View.VISIBLE);
        downButton.setVisibility(View.VISIBLE);
        boost.setVisibility(View.VISIBLE);
        attack.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
    }

    OnClickListener Listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            start = false;
            onPlay();
        }
    };

    public boolean onTouch(View button, MotionEvent motion) {
        switch (button.getId()) {
            case R.id.leftButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!attacked)
                            isLeft = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!attacked)
                            isLeft = false;
                        break;
                }
                break;
            case R.id.rightButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isRight = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRight = false;
                        break;
                }
                break;
            case R.id.upButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isUp = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isUp = false;
                        break;
                }
                break;
            case R.id.downButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDown = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isDown = false;
                        break;
                }
                break;

            case R.id.boost:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (MainPerson.stamina >= 2) {
                            staminaOn = true;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        staminaOn = false;
                        MainPerson.speed = 5;
                        break;
                }
                break;
            case R.id.attack:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        attacked = true;
                        isRight = false;
                        isLeft = false;
                        isUp = false;
                        isDown = false;
                        break;

                    case MotionEvent.ACTION_UP:
                        attacked = false;
                        break;
                }
                break;
        }
        return true;
    }
}

