package bwie.com.exam_customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 13435 on 2017/10/8.
 */

public class MyCustomView extends View {

    private int typedArrayColor;
    private float typedArrayDimension;
    private Paint paint;
    private int currenCircleBoundColor;
    private int pivotY;
    private int pivotX;
    private float radius=130;//圆的半径
    private float currentDegree=0;
    private int currentSpeed=1;
    private boolean isPause=false;

    public MyCustomView(Context context) {
        super(context);
        initView(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context
                .obtainStyledAttributes(attrs, R.styleable.MyCustomCircleArrowView);
        for(int i=0;i<typedArray.getIndexCount();i++){
            int index = typedArray.getIndex(i);
            switch(index){
                case R.styleable.MyCustomCircleArrowView_circlr_bound_color:
                    typedArrayColor = typedArray.getColor(index, Color.RED);
                    currenCircleBoundColor = typedArrayColor;
                    break;
                case R.styleable.MyCustomCircleArrowView_circlr_bound_width:
                    typedArrayDimension = typedArray.getDimension(index, 3);
                    break;
                default:
                    break;
            }
        }
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        paint = new Paint();
    }
    public void setColor(int color){
        if(currenCircleBoundColor != color){
            currenCircleBoundColor = color;
        }else{
            currenCircleBoundColor = typedArrayColor;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(currenCircleBoundColor);//画笔颜色
        paint.setStrokeWidth(typedArrayDimension);//画笔的宽度
        paint.setStyle(Paint.Style.STROKE);//圆的样式
        pivotX = getWidth() / 2;//X轴圆点位置
        pivotY = getHeight() / 2;//Y轴圆点位置
        canvas.drawCircle(pivotX,pivotY,radius,paint);
        canvas.save();//保存画布
        canvas.rotate(currentDegree,pivotX,pivotY);
        Path path = new Path();
        //从哪开始画 从A开始画
        path.moveTo(pivotX+radius,pivotY);
        //从A点画一个直线到D点
        path.lineTo(pivotX+radius-20,pivotY-20);
        //从D点画一个直线到B点
        path.lineTo(pivotX+radius,pivotY+20);
        //从B点画一个直线到C点
        path.lineTo(pivotX+radius+20,pivotY-20);
        //闭合  --  从C点画一个直线到A点
        path.close();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path,paint);
        canvas.restore();
        //旋转的度数一个一个度数增加,  如果乘以一个速度的话,按一个速度速度增加
        currentDegree+=1*currentSpeed;
        if (!isPause){
            invalidate();
        }
    }
    public void speed(){
        ++currentSpeed;
        if (currentSpeed>=10){
            currentSpeed=10;
            Toast.makeText(getContext(),"晕死了",Toast.LENGTH_SHORT).show();
        }
    }
    public void slowDown(){
        --currentSpeed;
        if (currentSpeed<=1){
            currentSpeed=1;
        }
    }
    public void pauseOrStart(){
        //如果是开始状态的话去重新绘制
        if (isPause){
            isPause=!isPause;
            invalidate();
        }else {
            isPause=!isPause;
        }
    }
}
