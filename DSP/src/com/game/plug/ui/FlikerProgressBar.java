package com.game.plug.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class FlikerProgressBar extends View implements Runnable{
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    private int DEFAULT_HEIGHT_DP = 50;

    private int borderWidth;

    private float maxProgress = 100f;

    private Paint textPaint;

    private Paint bgPaint;

    private Paint pgPaint;

    private String progressText;

    private Rect textRect;

    private RectF bgRectf;

    private Bitmap flikerBitmap;

    private float flickerLeft;
    
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    private float progress;

    private boolean isFinish;

    private boolean isStop;

    private int loadingColor;

    private int stopColor;

    private int progressColor;

    private int textSize;

    private int radius;

    private Thread thread;

    BitmapShader bitmapShader;

    public FlikerProgressBar(Context context,Bitmap bitmap) {
        this(context, null, 0);
        this.flikerBitmap=bitmap;
    }

    public FlikerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    public static final int[] FlikerProgressBar = {
        0x7f010000, 0x7f010001, 0x7f010002, 0x7f010003,
        0x7f010004};
    public static final int FlikerProgressBar_textSize = 0;
    public static final int FlikerProgressBar_loadingColor = 1;
    public static final int FlikerProgressBar_stopColor = 2;
    public static final int FlikerProgressBar_radius = 3;
    public static final int FlikerProgressBar_borderWidth = 4;
    private void initAttrs(AttributeSet attrs) {
        
        TypedArray ta = getContext().obtainStyledAttributes(attrs, FlikerProgressBar);
        try {
            textSize = (int) ta.getDimension(FlikerProgressBar_textSize, 40);
            loadingColor = ta.getColor(FlikerProgressBar_loadingColor, Color.parseColor("#40c4ff"));
            stopColor = ta.getColor(FlikerProgressBar_stopColor, Color.parseColor("#ff9800"));
            radius = (int) ta.getDimension(FlikerProgressBar_radius, 25);
            borderWidth = (int) ta.getDimension(FlikerProgressBar_borderWidth, 4);
        } finally {
            ta.recycle();
        }
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);

        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);

        textRect = new Rect();
        bgRectf = new RectF(borderWidth, borderWidth, getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth);

        if(isStop){
            progressColor = stopColor;
        } else{
            progressColor = loadingColor;
        }

        if(flikerBitmap!=null){
            flickerLeft = -flikerBitmap.getWidth();
        }
        initPgBimap();
    }

    private void initPgBimap() {
        pgBitmap = Bitmap.createBitmap(getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth, Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);
        thread = new Thread(this);
        thread.start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightSpecMode){
            case MeasureSpec.AT_MOST:
                height = dp2px(DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                height = heightSpecSize;
                height = dp2px(DEFAULT_HEIGHT_DP);
                break;
        }
        setMeasuredDimension(widthSpecSize, height);

        if(pgBitmap == null){
            init();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackGround(canvas);

        drawProgress(canvas);

        drawProgressText(canvas);

        drawColorProgressText(canvas);
    }

    private void drawBackGround(Canvas canvas) {
        bgPaint.setColor(progressColor);
        canvas.drawRoundRect(bgRectf, radius, radius, bgPaint);
    }

    private void drawProgress(Canvas canvas) {
    	try {
    		pgPaint.setColor(progressColor);

	        float right = (progress / maxProgress) * getMeasuredWidth();
	        pgCanvas.save(Canvas.CLIP_SAVE_FLAG);
	        pgCanvas.clipRect(0, 0, right, getMeasuredHeight());
	        pgCanvas.drawColor(progressColor);
	        pgCanvas.restore();

	        if(!isStop){
	            pgPaint.setXfermode(xfermode);
	            if(pgCanvas!=null){
	            	 pgCanvas.drawBitmap(flikerBitmap, flickerLeft, 0, pgPaint);
	    	            pgPaint.setXfermode(null);
	            }
	           
	        }

	        bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
	        pgPaint.setShader(bitmapShader);
	        canvas.drawRoundRect(bgRectf, radius, radius, pgPaint);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
    }

    private void drawProgressText(Canvas canvas) {
        textPaint.setColor(progressColor);
        progressText = getProgressText();
        textPaint.getTextBounds(progressText, 0, progressText.length(), textRect);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
    }

    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        float progressWidth = (progress / maxProgress) * getMeasuredWidth();
        if(progressWidth > xCoordinate){
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            float right = Math.min(progressWidth, xCoordinate + tWidth * 1.1f);
            canvas.clipRect(xCoordinate, 0, right, getMeasuredHeight());
            canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
            canvas.restore();
        }
    }

    public void setProgress(float progress){
        if(!isStop){
            if(progress < maxProgress){
                this.progress = progress;
            } else {
                this.progress = maxProgress;
                finishLoad();
            }
            invalidate();
        }
    }

    public void setStop(boolean stop) {
        isStop = stop;
        if(isStop){
            progressColor = stopColor;
            thread.interrupt();
        } else {
            progressColor = loadingColor;
            thread = new Thread(this);
            thread.start();
        }
        invalidate();
    }

    public void finishLoad() {
        isFinish = true;
        setStop(true);
    }

    public void toggle(){
        if(!isFinish){
            if(isStop){
                setStop(false);
            } else {
                setStop(true);
            }
        }
    }

    @Override
    public void run() {
        int width = flikerBitmap.getWidth();
        try {
            while (!isStop && !thread.isInterrupted()){
                flickerLeft += dp2px(5);
                float progressWidth = (progress / maxProgress) * getMeasuredWidth();
                if(flickerLeft >= progressWidth){
                    flickerLeft = -width;
                }
                postInvalidate();
                Thread.sleep(20);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reset(){
        setStop(true);
        progress = 0;
        isFinish = false;
        isStop = false;
        progressColor = loadingColor;
        progressText = "";
        flickerLeft = -flikerBitmap.getWidth();
        initPgBimap();
    }

    public float getProgress() {
        return progress;
    }

    public boolean isStop() {
        return isStop;
    }

    public boolean isFinish() {
        return isFinish;
    }

    private String getProgressText() {
        String text= "";
        if(!isFinish){
            if(!isStop){
                text = "正在下载" + progress + "%";
            } else {
                text = "暂停";
            }
        } else{
            text = "下载完成";
        }

        return text;
    }

    private int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
