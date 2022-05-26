package ru.myitschool;

import android.content.Context;

public class MainPerson extends Body {
    public static float psevdoX = 500;
    public static float psevdoY = 690;
    public static float hp;
    public static float defaultHp;
    public static  boolean gameOver = false;
    public static float stamina;
    public static float damage;
    public static float defaultStamina;
 public MainPerson (Context context){
     bitmapId =  R.drawable.mainperson;
     x = 1000;
     y = 350;
     hp = 1000;
     damage = 1;
     defaultHp = hp;
     speed = 5;
     size = 120;
     defaultStamina = 300;
     stamina = defaultStamina;
     init(context);
 }
    public void update() {
        if(MainActivity.isLeft && psevdoX > 0){
            psevdoX -= speed;
        }
        if(MainActivity.isRight && psevdoX < Game.levelX){
            psevdoX += speed;
        }
        if(MainActivity.isDown && psevdoY > 0){
            psevdoY -= speed;
        }
        if(MainActivity.isUp && psevdoY < Game.levelY){
            psevdoY += speed;
        }
        if(hp<=0){
            gameOver = true;
        }
    }
}
