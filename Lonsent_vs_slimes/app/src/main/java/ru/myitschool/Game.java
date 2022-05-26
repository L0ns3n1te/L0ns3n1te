package ru.myitschool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;;
import java.io.FileNotFoundException;
import java.util.Random;

public class Game extends SurfaceView implements Runnable  {

    public Game(Context context) {
        super(context);
        levelX = 2030;
        levelY = 1800;
        surfaceHolder = getHolder();
        paint = new Paint();
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        greenPaint = new Paint();
        greenPaint.setARGB(160,76,97,37);
        hugePaint = new Paint();
        hugePaint.setTextSize(50);
        gameThread = new Thread(this);
        try {
            bestScore = Files.input("bestScore.txt");
        } catch (FileNotFoundException e) {}
        gameThread.start();
    }
        public MainPerson mainPerson = new MainPerson(getContext());
    public static float levelX;
    boolean spawn = true;
    boolean [] check = new boolean[30];
    long enemyAttackingTimeVar = 0;
    long mpAttackingTimeVar = 0;
    public static float levelY;
    public int score = 0;
    Random random = new Random();
    int bestScore;
    int xr = 0;
    int yr = 0;
    int xpr = 600;
    int ypr = -2550;
    Slime[] enemies = new Slime[30];
        boolean start = true;
        Background background = new Background(getContext());
        public boolean gameRunning = true;
        boolean aimedAtRight = true;
        public float mprX = mainPerson.x;
        public float mprY = mainPerson.y;
        public static float otherObjX = 0;
        public static float otherObjY;
        private Thread gameThread = null;
        private Paint paint;
        Paint hugePaint;
        boolean spriteChanged = false;
        int numberOfEnemies = 9;
        Paint bluePaint;
        Paint greenPaint;
        int hpBonus = 100;
        Paint redPaint;
        private Canvas canvas;
        private SurfaceHolder surfaceHolder;
        public void run() {
            while (gameRunning) {
                enemySpawn();
                try {
                    draw();
                }catch (Exception e){}
                update();
                mainPersonsStamina();
            }
        }
        private void update() {
            start = false;
                if(!start) {
                    checkCollision();
                    mainPerson.update();
                    background.update();
                    mprX = mainPerson.x;
                    mprY = mainPerson.x;
                    otherObjX = mainPerson.psevdoX;
                    otherObjY = mainPerson.psevdoY;
                }
            for (int i = 0; i < numberOfEnemies; i++) {
                enemies[i].update();
                if (mainPerson.x - enemies[i].x < 190 && mainPerson.x - enemies[i].x > 0 && Math.abs(mainPerson.y - enemies[i].y) < 100 && !aimedAtRight)
                    enemies[i].attacked();
                else if (enemies[i].x - mainPerson.x < 190 && enemies[i].x - mainPerson.x > 0 && Math.abs(mainPerson.y - enemies[i].y) < 100 && aimedAtRight)
                    enemies[i].attacked();
            }
            for (int i = 0; i<numberOfEnemies; i++) {
                if (check[i]) {
                    MainPerson.hp -= 5;
                }
            }
            }
        private void draw() {
            enemySpawn();
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                if (!MainActivity.gameOver) {
                    background.drow(paint, canvas);
                    if (MainActivity.isRight && !aimedAtRight && !MainActivity.attacked) {
                        aimedAtRight = true;
                        mainPerson.bitmapId = R.drawable.mainperson;
                        mainPerson.size = 120;
                        mainPerson.init(getContext());
                    } else if (!MainActivity.isRight && aimedAtRight && MainActivity.isLeft && !MainActivity.attacked) {
                        aimedAtRight = false;
                        mainPerson.bitmapId = R.drawable.mainpersoni;
                        mainPerson.size = 120;
                        mainPerson.init(getContext());
                    }
                    for (int i = 0; i < numberOfEnemies; i++) {
                        if (enemies[i].hp > 0) {
                            canvas.drawOval(enemies[i].x + 200, enemies[i].y, enemies[i].x - 100, enemies[i].y + 150, greenPaint);
                        }
                    }
                    drawMainPersonAttack();
                    drawMainPerson();
                    drawEnemy();
                    enemyAttack();
                    canvas.drawText("Score: " + score, 0, 100, hugePaint);
                } else {
                    canvas.drawColor(Color.BLACK);
                    hugePaint.setColor(Color.WHITE);
                    canvas.drawText("Score: " + score, 0, 100, hugePaint);
                    if (bestScore < score) {
                        bestScore = score;
                        try {
                            Files.output("bestScore.txt", bestScore);
                        }catch (Exception e){}
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    canvas.drawText("Best: " + bestScore, 0, 200, hugePaint);
                }
                while (MainActivity.start){
                    canvas.drawColor(Color.BLACK);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
            void enemySpawn () {
                    for (int i = 0; i < numberOfEnemies; i++) {
                            if (spawn) {
                                xr = random.nextInt(100);
                                yr = random.nextInt(100);
                                enemies[i] = new Slime(getContext(), xpr + xr, ypr + yr);
                                xpr += 680;
                            if((i+1)%3 == 0)
                                xpr = 1000;
                                if((i+1)%3 == 0)
                                ypr += 650;
                        }
                    }
                }
            void drawEnemy (){
                for (int i = 0; i < numberOfEnemies; i++) {
                    if(enemies[i].hp > 0) {
                        spawn = false;
                        enemies[i].drow(paint, canvas);
                        canvas.drawRect(enemies[i].x + 10, enemies[i].y - 29, enemies[i].x + 10 * enemies[i].hp, enemies[i].y - 20, redPaint);
                    }
                    else {
                        if (!enemies[i].gotBonus){
                            MainPerson.hp+=hpBonus;
                            if(MainPerson.stamina< MainPerson.defaultStamina)
                                MainPerson.stamina+=50;
                            score += 1;
                        }
                        enemies[i].gotBonus = true;
                        enemies[i].killed = true;
                        if (Math.abs(mainPerson.x-enemies[i].x) > 1000 || Math.abs(mainPerson.y-enemies[i].y) > 500) {
                            enemies[i].hp = 10;
                            enemies[i].damage *=1.25;
                            enemies[i].killed = false;
                            enemies[i].gotBonus = false;
                        }
                    }
                }
            }
            void drawMainPersonAttack(){
                if(aimedAtRight && MainActivity.attacked && MainPerson.hp > 0) {
                    MainPerson.speed = 0;
                    mainPerson.bitmapId = R.drawable.mainpersona;
                    mainPerson.init(getContext());
                    long finalTime1= System.nanoTime();
                    long startTime1= System.nanoTime();
                    mpAttackingTimeVar = 0;
                    while (finalTime1 - startTime1 < 100000000) {
                        mpAttackingTimeVar = 0;
                        while (mpAttackingTimeVar < 100000) {
                            mpAttackingTimeVar += 1;
                        }
                        finalTime1 = System.nanoTime();
                    }
                    MainPerson.size = 120;
                    mainPerson.init(getContext());
                    spriteChanged = true;
                }
                else if(aimedAtRight && !MainActivity.attacked && MainPerson.hp > 0 && spriteChanged){
                    spriteChanged = false;
                    mainPerson.bitmapId = R.drawable.mainperson;
                    MainPerson.size = 120;
                    mainPerson.init(getContext());
                    MainPerson.speed = 5;
                }
                if(!aimedAtRight && MainActivity.attacked && MainPerson.hp > 0) {
                    MainPerson.speed = 0;
                    mainPerson.bitmapId = R.drawable.mainpersonai;
                    mainPerson.init(getContext());
                    long finalTime1= System.nanoTime();
                    long startTime1= System.nanoTime();
                    mpAttackingTimeVar = 0;
                    while (finalTime1 - startTime1 < 100000000) {
                        mpAttackingTimeVar = 0;
                        while (mpAttackingTimeVar < 100000) {
                            mpAttackingTimeVar += 1;
                        }
                        finalTime1 = System.nanoTime();
                    }
                    MainPerson.size = 120;
                    mainPerson.init(getContext());
                    spriteChanged = true;
                }
                else if(!aimedAtRight && !MainActivity.attacked && MainPerson.hp > 0 && spriteChanged){
                    spriteChanged = false;
                    mainPerson.bitmapId = R.drawable.mainpersoni;
                    MainPerson.size = 120;
                    mainPerson.init(getContext());
                    MainPerson.speed = 5;
                }
            }
            void drawMainPerson(){
                if(MainPerson.hp>0) {
                    mainPerson.drow(paint, canvas);
                     if(MainPerson.stamina>0 )
                    canvas.drawRect(mainPerson.x,mainPerson.y -40, mainPerson.x +10* MainPerson.stamina/30, mainPerson.y - 31,bluePaint);
                    canvas.drawRect(mainPerson.x , mainPerson.y - 29, mainPerson.x + 10 * MainPerson.hp / 100, mainPerson.y - 20, redPaint);
                }
                else if(MainPerson.hp<0) {
                    MainActivity.gameOver = true;
                }
            }
           void enemyAttack(){
               for (int i = 0; i < numberOfEnemies; i++) {
                   if (Math.abs(enemies[i].x - mainPerson.x) < 200 && Math.abs(enemies[i].y - mainPerson.y) < 200 && !enemies[i].killed){
                       long startTime1 = System.nanoTime();
                       long finalTime1 = System.nanoTime();
                       long attackTimeVar = 0;
                           while (finalTime1 - startTime1 < 10000000) {
                               while (attackTimeVar < 100000) {
                                   attackTimeVar += 1;
                               }
                               finalTime1 = System.nanoTime();
                           }
                           MainPerson.hp -= enemies[i].damage;
                   }
               }
           }
         void mainPersonsStamina(){
            if(MainActivity.staminaOn){
         long startTime1 = System.nanoTime();
         long finalTime1 = System.nanoTime();
         long staminaTimeVar = 0;
         if (MainActivity.isDown || MainActivity.isUp || MainActivity.isLeft || MainActivity.isRight) {
             while (finalTime1 - startTime1 < 10000000) {
                 while (staminaTimeVar < 100000) {
                     staminaTimeVar += 1;
                 }
                 finalTime1 = System.nanoTime();
             }
             if(MainPerson.stamina>=2) {
                 MainPerson.stamina -= 2;
                 MainPerson.speed = 10;
             }
             else
                 MainPerson.speed = 5;
             startTime1 = System.nanoTime();
         }
     }
     else if(!MainActivity.staminaOn){
         MainPerson.speed = 5;
                    if (MainPerson.stamina < MainPerson.defaultStamina) {
                        long startTime1 = System.nanoTime();
                        long finalTime1 = System.nanoTime();
                        int staminaTimeVar = 0;
                        while (finalTime1 - startTime1 < 10000000) {
                            while (staminaTimeVar < 100000) {
                                staminaTimeVar += 1;
                            }
                            finalTime1 = System.nanoTime();
                        }
                        MainPerson.stamina += 0.5;
                    }
                }
                }
    private void checkCollision(){
        for (int i = 0; i<numberOfEnemies; i++) {
            if(enemies[i].isCollision(mainPerson.x, mainPerson.y, MainPerson.size)&&enemies[i].hp>0){
                check[i] = true;
            }
            else {
                check[i] = false;
            }
        }
    }
 }



