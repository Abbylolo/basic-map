package com.example.location_baidumap;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * 该类绘制带边框的文本框组件
 * @author Abby
 */
public class BorderTextView extends androidx.appcompat.widget.AppCompatTextView {

    // 定义自定义绘图
    public BorderTextView(Context context) {
        super(context);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int srokeWidth = 1;

    // 绘制函数
    @Override
    protected void onDraw(Canvas canvas) {
        // 创建画笔对象
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // 设置画笔颜色（边框颜色）
        paint.setStrokeWidth(20);
        //paint.setStyle(Paint.Style.FILL);

        // 画TextView的4个边
        //canvas.drawRect(this.getHeight(),this.getWidth(),this.getHeight(),this.getWidth(),paint);
        canvas.drawLine(0, 0, this.getWidth() - srokeWidth, 0, paint); //上边框
        canvas.drawLine(0, 0, 0, this.getHeight() - srokeWidth, paint); //左边框
        canvas.drawLine(this.getWidth() - srokeWidth, 0, this.getWidth() - srokeWidth, this.getHeight() - srokeWidth, paint); //右边框
        canvas.drawLine(0, this.getHeight() - srokeWidth, this.getWidth() - srokeWidth, this.getHeight() - srokeWidth, paint); //下边框
        super.onDraw(canvas);
    }
}
