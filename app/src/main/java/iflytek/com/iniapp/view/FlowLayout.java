package iflytek.com.iniapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 创建人：shuo
 * 创建时间：2016/9/18 15:31
 */
public class FlowLayout extends ViewGroup {

    private List<List<View>> allViewList = new ArrayList<>();
    private List<Integer> allHeightList = new ArrayList<>();

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    public FlowLayout(Context context) {
        super(context);
        scroller = new Scroller(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    //测量每个子view并设置自己的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;//wrap_content下的宽高，需要计算
        int lineWidth = 0; //每行的宽度
        int lineHeight = 0;//每行的高度

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin; //每个子View占据的宽度
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin; //每个子View占据的高度
            if (childWidth + lineWidth > widthSize) {
                //需要换行
                width = Math.max(lineWidth, childWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //不需要换行
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);

            }
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width
                , heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    //摆放child
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allHeightList.clear();
        allViewList.clear();
        int width = getWidth();
        int lineHeight = 0, lineWidth = 0;
        int childCount = getChildCount();
        List<View> list = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            TextView child = (TextView) getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (childWidth + lineWidth + params.leftMargin + params.rightMargin > width ) {
                allHeightList.add(lineHeight);
                allViewList.add(list);
                list = new ArrayList<>();
                lineWidth = 0;
                lineHeight = 0;

            }
            lineWidth += childWidth + params.leftMargin + params.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + params.topMargin
                    + params.bottomMargin);
            list.add(child);
        }
        allViewList.add(list);
        allHeightList.add(lineHeight);

        int left = 0;
        int top = 0;

        int lineNums = allViewList.size();
        for (int i = 0; i < lineNums; i++) {
            list = allViewList.get(i);
            lineHeight = allHeightList.get(i);

            for (int j = 0; j < list.size(); j++) {
                View child = list.get(j);
                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

                int cl = left + params.leftMargin;
                int ct = top + params.topMargin;
                int cr = cl + child.getMeasuredWidth();
                int cb = ct + child.getMeasuredHeight();

                child.layout(cl, ct, cr, cb);

                left = cr;

            }
            left = 0;
            top += allHeightList.get(i);
        }
    }

    Scroller scroller;
    int startX,startY;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                startX = (int) event.getX();
//                startY = (int) event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = (int) (event.getX()-startX);
//                int offsetY = (int) (event.getY()-startY);
//                scroller.startScroll(scroller.getFinalX(),scroller.getFinalY(),0,-offsetY);
//                invalidate();
//                startX = (int) event.getX();
//                startY = (int) event.getY();
//                break;
//
//        }
//        return true;
//    }

//    @Override
//    public void computeScroll() {
//        if (scroller.computeScrollOffset()){
//            scrollTo(scroller.getCurrX(),scroller.getCurrY());
//            invalidate();
//        }
//    }
}

