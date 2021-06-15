package com.example.auraia.Dimming;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ScreenFIlterService extends Service {

    public static int STATE_ACTIVE = 0;
    public static int STATE_INACTIVE =1;
    public static int STATE;

    static {
        STATE = STATE_INACTIVE;
    }

    private View mView;
    private SharedMemory mSharedMemory;

    public ScreenFIlterService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        mSharedMemory = new SharedMemory(this);
        mView = new LinearLayout(this);
        mView.setBackgroundColor(mSharedMemory.getColour());

        int LAYOUT_FLAG;
        LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        assert windowManager!= null;
        windowManager.addView(mView,layoutParams);

        STATE = STATE_ACTIVE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        assert windowManager!= null;
        windowManager.removeView(mView);
        STATE = STATE_INACTIVE;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mView.setBackgroundColor(mSharedMemory.getColour());
        return super.onStartCommand(intent, flags, startId);

    }
}
