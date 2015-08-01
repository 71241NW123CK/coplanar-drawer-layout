package party.treesquaredcode.android.util.coplanardrawerlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by rht on 7/31/15.
 */
public class CoplanarDrawerLayout extends DrawerLayout {
    protected boolean leftDrawerCoplanar = true;
    protected boolean rightDrawerCoplanar = false;

    protected int leftDrawerVisibleWidth = 0;
    protected int rightDrawerVisibleWidth = 0;

    protected DrawerListenerDecorator drawerListenerDecorator = new DrawerListenerDecorator();

    private static final int DEFAULT_REMAINDER_MARGIN_DP = 64;

    public CoplanarDrawerLayout(Context context) {
        this(context, null);
    }

    public CoplanarDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoplanarDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        decorateDrawerListener();
    }

    public boolean isLeftDrawerCoplanar() {
        return leftDrawerCoplanar;
    }

    public void setLeftDrawerCoplanar(boolean leftDrawerCoplanar) {
        this.leftDrawerCoplanar = leftDrawerCoplanar;
        adjustMargins();
    }

    public boolean isRightDrawerCoplanar() {
        return rightDrawerCoplanar;
    }

    public void setRightDrawerCoplanar(boolean rightDrawerCoplanar) {
        this.rightDrawerCoplanar = rightDrawerCoplanar;
        adjustMargins();
    }

    @Override
    public void setDrawerListener(DrawerListener listener) {
        drawerListenerDecorator.setDrawerListener(listener);
    }

    public void setLeftDrawerWidthPixels(int widthPixels) {
        View leftDrawerView = getLeftDrawerView();
        if (leftDrawerView == null) {
            return;
        }
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) leftDrawerView.getLayoutParams();
        layoutParams.width = widthPixels;
        layoutParams.setMargins(0, 0, 0, 0);
        leftDrawerView.setLayoutParams(layoutParams);
    }

    public void setRightDrawerWidthPixels(int widthPixels) {
        View rightDrawerView = getRightDrawerView();
        if (rightDrawerView == null) {
            return;
        }
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) rightDrawerView.getLayoutParams();
        layoutParams.width = widthPixels;
        layoutParams.setMargins(0, 0, 0, 0);
        rightDrawerView.setLayoutParams(layoutParams);
    }

    public void setLeftDrawerWidthRemainderPixels(int widthRemainderPixels) {
        View leftDrawerView = getLeftDrawerView();
        if (leftDrawerView == null) {
            return;
        }
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) leftDrawerView.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.setMargins(0, 0, widthRemainderPixels - getDefaultRemainderMarginPixels(), 0);
        leftDrawerView.setLayoutParams(layoutParams);
    }

    public void setRightDrawerWidthRemainderPixels(int widthRemainderPixels) {
        View rightDrawerView = getRightDrawerView();
        if (rightDrawerView == null) {
            return;
        }
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) rightDrawerView.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.setMargins(widthRemainderPixels - getDefaultRemainderMarginPixels(), 0, 0, 0);
        rightDrawerView.setLayoutParams(layoutParams);
    }

    private void decorateDrawerListener() {
        super.setDrawerListener(drawerListenerDecorator);
    }

    @Nullable
    private View getLeftDrawerView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (isLeftDrawerView(childView)) {
                return childView;
            }
        }
        return null;
    }

    @Nullable
    private View getRightDrawerView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (isRightDrawerView(childView)) {
                return childView;
            }
        }
        return null;
    }

    private boolean isLeftDrawerView(View drawerView) {
        return ((LayoutParams) drawerView.getLayoutParams()).gravity == Gravity.LEFT;
    }

    private boolean isRightDrawerView(View drawerView) {
        return ((LayoutParams) drawerView.getLayoutParams()).gravity == Gravity.RIGHT;
    }

    private void adjustMargins() {
        View contentView = getChildAt(0);
        int leftMargin = leftDrawerCoplanar ? leftDrawerVisibleWidth : rightDrawerCoplanar ? -rightDrawerVisibleWidth : 0;
        int rightMargin = rightDrawerCoplanar ? rightDrawerVisibleWidth : leftDrawerCoplanar ? -leftDrawerVisibleWidth : 0;
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) contentView.getLayoutParams();
        layoutParams.setMargins(leftMargin, 0, rightMargin, 0);
        contentView.setLayoutParams(layoutParams);
    }

    private int getDefaultRemainderMarginPixels() {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (DEFAULT_REMAINDER_MARGIN_DP * scale + 0.5f);
    }

    class DrawerListenerDecorator extends DrawerLayoutDrawerListenerDecorator {
        @Override
        public void onDrawerSlide(View view, float v) {
            super.onDrawerSlide(view, v);
            if (isLeftDrawerView(view)) {
                leftDrawerVisibleWidth = (int) view.getX() + view.getWidth();
            }
            if (isRightDrawerView(view)) {
                rightDrawerVisibleWidth = getWidth() - (int) view.getX();
            }
            adjustMargins();
        }
    }
}
