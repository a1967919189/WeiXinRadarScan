package com.tommorowsoft.weixinradarscan.entity;

import android.graphics.Paint;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Wave {
    public  int x,y;
    public int currentRadius,startRadius,maxRadius;
    public int currentColor;
    public Paint mpaint;

    public Wave(int x, int y, int currentRadius, int startRadius, int maxRadius, int currentColor) {
        this.x = x;
        this.y = y;
        this.currentRadius = currentRadius;
        this.startRadius = startRadius;
        this.maxRadius = maxRadius;
        this.currentColor = currentColor;

        mpaint=new Paint();
        mpaint.setColor(currentColor);
        mpaint.setStrokeWidth(3);
        mpaint.setAntiAlias(true);
        mpaint.setStyle(Paint.Style.FILL);
    }
}
