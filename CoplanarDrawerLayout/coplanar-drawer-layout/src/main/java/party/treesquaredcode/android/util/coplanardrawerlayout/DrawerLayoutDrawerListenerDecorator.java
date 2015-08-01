package party.treesquaredcode.android.util.coplanardrawerlayout;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by rht on 7/31/15.
 */
abstract class DrawerLayoutDrawerListenerDecorator implements DrawerLayout.DrawerListener {
    protected DrawerLayout.DrawerListener drawerListener;

    public DrawerLayoutDrawerListenerDecorator() {
        this(null);
    }

    public DrawerLayoutDrawerListenerDecorator(DrawerLayout.DrawerListener drawerListener) {
        this.drawerListener = drawerListener;
    }

    public void setDrawerListener(DrawerLayout.DrawerListener drawerListener) {
        this.drawerListener = drawerListener;
    }

    public void onDrawerSlide(View view, float v) {
        if (drawerListener != null) {
            drawerListener.onDrawerSlide(view, v);
        }
    }

    @Override
    public void onDrawerOpened(View view) {
        if (drawerListener != null) {
            drawerListener.onDrawerOpened(view);
        }
    }

    @Override
    public void onDrawerClosed(View view) {
        if (drawerListener != null) {
            drawerListener.onDrawerClosed(view);
        }
    }

    @Override
    public void onDrawerStateChanged(int i) {
        if (drawerListener != null) {
            drawerListener.onDrawerStateChanged(i);
        }
    }
}
