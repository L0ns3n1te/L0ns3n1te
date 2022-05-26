package ru.myitschool;

import android.content.Context;

public class Slime extends Body{
    public float hp;
    public float damage;
    public boolean killed = false;
    boolean gotBonus = false;
    float xb,yb;
    public Slime (Context context, int x1,int y1){
        bitmapId =  R.drawable.slime;
        x = x1;
        y = y1;
        damage = 2;
        xb = x;
        yb = y;
        hp = 10;
        speed = 5;
        size = 100;
        init(context);
    }
    public void update() {

        if (MainActivity.isLeft && Game.otherObjX > 0 && Game.otherObjX < Game.levelX) {
            x += speed;
        }
        if (MainActivity.isRight && Game.otherObjX > 0 && Game.otherObjX < Game.levelX) {
            x -= speed;
        }
        if (MainActivity.isDown && Game.otherObjY < Game.levelY && Game.otherObjY > 0) {
            y -= speed;
        }
        if (MainActivity.isUp && Game.otherObjY < Game.levelY && Game.otherObjY > 0) {
            y += speed;
        }
        }
        public void attacked (){
            if (MainActivity.attacked && hp>0) {
                hp -= MainPerson.damage;
                MainActivity.attacked = false;
            }
        }
    public boolean isCollision(float mpX, float mpY, float mpSize) {
        return !((((x+80) < mpX)||(x > mpX+80))||((y+80) < mpY)||(y > (mpY+80)));
    }
    }


