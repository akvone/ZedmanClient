package com.kivi.zedman.android.controller;

/**
 * Created by Kirill on 18.03.2016.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

import com.kivi.zedman.system.ActionResolver;

public class ActionResolverAndroid implements ActionResolver{
    Handler handler;
    Context context;

    float x;
    float y;
    String sDown;
    String sMove;
    String sUp;
    String information = "start";

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
        TouchListener touchListener = new TouchListener();
    }

    @Override
    public String getSomeInformation() {
        return information;
    }

    private class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    }
}
