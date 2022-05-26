package ru.myitschool;

import android.content.Context;

public class Background extends Body {
 public Background(Context context){
     bitmapId =  R.drawable.background1;
     x = 90;
     y = -1300;
     speed = 5;
     size = 3000;
     init(context);
 }
}
