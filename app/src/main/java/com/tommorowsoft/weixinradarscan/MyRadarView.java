package com.tommorowsoft.weixinradarscan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.tommorowsoft.weixinradarscan.entity.Wave;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: document your custom view class.
 */
public class MyRadarView extends View {
    private Paint mPaintNormal;    // 绘制普通圆圈和线的画笔
    private Paint mPaintCircle;// 绘制渐变圆
    private int w, h;    // 手机屏幕的宽高，雷达视图父容器的宽高
    private Matrix matrix;
    private Handler handler = new Handler();
    private int start;
    Bitmap mbitmap;

    int image_halfWidth;//头像半径

    private Runnable r = new Runnable() {

        @Override
        public void run() {
            // 执行循环旋转动画,并且刷新UI
            start = start + 2;
            matrix = new Matrix();
            matrix.postRotate(start, w / 2, h / 2);// 设置画布旋转
            MyRadarView.this.invalidate();    // 刷新UI
            handler.postDelayed(r, 20);
        }
    };

    public MyRadarView(Context context, Paint mPaintNormal) {
        super(context);
        this.mPaintNormal = mPaintNormal;
    }

    public MyRadarView(Context context, AttributeSet attrs, int defStyleAttr, Paint mPaintNormal) {
        super(context, attrs, defStyleAttr);
        this.mPaintNormal = mPaintNormal;
    }

    public MyRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        initPaint();
//        setBackgroundResource(R.drawable.bg);
        setBackgroundResource(R.drawable.radar_bg);


        // 获取手机屏幕宽高

        this.w = context.getResources().getDisplayMetrics().widthPixels;
        this.h = context.getResources().getDisplayMetrics().heightPixels;
        handler.post(r);
    }

    /**
     * @author Xubin   Single QQ:215298766
     */
    private void initPaint() {
        mPaintNormal = new Paint();// 创建画笔
        mPaintNormal.setColor(Color.parseColor("#A1A1A1"));
        mPaintNormal.setStrokeWidth(3);// 设置线条
        mPaintNormal.setAntiAlias(true);// 设置抗锯齿
        mPaintNormal.setStyle(Paint.Style.STROKE);

        // 绘制渐变圆
        mPaintCircle = new Paint();
        mPaintCircle.setColor(0x9D00ff00);// 16进制
        mPaintCircle.setAntiAlias(true);


        //获取头像
//        mbitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.photo1))
//                .getBitmap();
////        image_halfWidth = 4 * (w/6)/5;
//        float sx = (float) 2 * image_halfWidth / mbitmap.getWidth();
//        float sy = (float) 2 * image_halfWidth / mbitmap.getHeight();
//        //缩小图片
//        Matrix m = new Matrix();
//        m.setScale(sx,sy);
//        mbitmap = Bitmap.createBitmap(mbitmap, 0, 0, 10,
//                10, m, false);


    }

    /**
     * 测量控件的宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置此视图的宽高
        setMeasuredDimension(w, h);
    }

    /**
     * 确定控件的视图
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 屏幕中心，x:w/2,y:h/2
        canvas.drawCircle(w / 2, h / 2, w / 6, mPaintNormal);        // 绘制小圆
        canvas.drawCircle(w / 2, h / 2, 2 * w / 6, mPaintNormal);    // 绘制中圆
        canvas.drawCircle(w / 2, h / 2, 11 * w / 20, mPaintNormal);    // 绘制中大圆
        canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPaintNormal);    // 绘制大圆

        //绘制头像
//        canvas.drawBitmap(mbitmap, w / 2, h / 2, null);
        // 绘制渐变色的圆


        Shader shader = new SweepGradient(w / 2, h / 2, Color.TRANSPARENT, Color.parseColor("#AAAAAAAA"));
        mPaintCircle.setShader(shader);
        canvas.concat(matrix);
        canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPaintCircle);    //绘制渐变圆
        //波浪效果
        if (waves!=null&&waves.keySet().size()>0){
           for (String n:waves.keySet()){
               if (waves.get(n)!=null){
                   canvas.drawCircle(waves.get(n).x,waves.get(n).y,
                           waves.get(n).currentRadius,waves.get(n).mpaint);
               }
           }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            launchSingleWave();
        }
        return super.onTouchEvent(event);
    }

    Map<String,Wave>waves;
    private void launchSingleWave() {

            if (waves==null){
                waves=new HashMap<>();
            }

        final Wave tempm=new Wave(w/2,h/2,0,0,7*w/6,Color.parseColor("#22ffffff"));
        final String tag=""+System.currentTimeMillis();
        waves.put(tag,tempm);

        Runnable mRun=new Runnable() {
            @Override
            public void run() {
                if (tempm.currentRadius<tempm.maxRadius){
                    tempm.currentRadius+=7;
                    int diff=tempm.maxRadius-tempm.startRadius;
                        //设置透明比例
                    tempm.mpaint.setAlpha((tempm.maxRadius-tempm.currentRadius)*60/diff);
                       handler.postDelayed(this,5);
                }else{
                    waves.put(tag,null);
                }
            }
        };
        mRun.run();

    }
}
