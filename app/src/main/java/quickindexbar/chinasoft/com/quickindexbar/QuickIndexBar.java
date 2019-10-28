package quickindexbar.chinasoft.com.quickindexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class QuickIndexBar extends View {
    private Paint paint;
    private int width;
    private int height;
    private float cellHeight;
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        cellHeight = height * 1.0f / indexArr.length;
        Log.e("QuickIndexBar: ", "onSizeChanged: " + width);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(26);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            float x = width / 2;
            float y = cellHeight / 2 + getTextHeight(paint, indexArr[i]) / 2 + i * cellHeight;
            paint.setColor(lastIndex==i?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i], x, y, paint);
        }
    }

    private int lastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / cellHeight);
                if (index < 0) index = 0;
                if (index > 25) index = 25;
                if (lastIndex != index) {
                    if (letterListener != null) {
                        letterListener.onTouchLetter(indexArr[index]);
                    }
                }
                lastIndex = index;

                break;

            case MotionEvent.ACTION_UP:
                letterListener.onTouchMoveUp(indexArr[lastIndex]);
                lastIndex = -1;
                break;
        }
        //重绘
        invalidate();
        return true;
    }


    private float getTextHeight(Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    private OnTouchLetterListener letterListener;

    public void setOnTouchListener(OnTouchLetterListener letterListener) {
        this.letterListener = letterListener;
    }

    public interface OnTouchLetterListener {
        void onTouchLetter(String letter);
        void onTouchMoveUp(String letter);
    }
}
