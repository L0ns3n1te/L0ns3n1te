package ru.myitschool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Body {
    protected float x;
    protected float y;
    public static float size;
    public static float speed;
    protected int bitmapId;
    protected Bitmap bitmap;
    void init(Context context) {
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int) (size), (int) (size), false);
        cBitmap.recycle();
    }
    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }
    void drow(Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, paint);
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
    }


