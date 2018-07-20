package com.cloudminds.camera2.ui.page;

import android.view.View;
import android.view.ViewGroup;
import com.cloudminds.camera2.ui.ViewInflater;


public class ModePage
        implements Page {
    private static final String TAG = "CAMERA3_" + Page.class.getSimpleName();
    private ViewInflater mInflater;
    private ViewGroup mLayout;
    public ModePage(ViewInflater viewInflater, int resID) {
        mInflater = viewInflater;
        mLayout = ((ViewGroup) mInflater.inflate(resID));
    }

    protected ViewGroup getView() {
        return mLayout;
    }

    @Override
    public void hide() {
        mLayout.setVisibility(View.INVISIBLE);
    }

    public View hide(int id) {
        View localView = mLayout.findViewById(id);
        if (localView == null) {
            return null;
        }
        localView.setVisibility(View.INVISIBLE);
        return localView;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        mLayout.setVisibility(View.VISIBLE);
    }


    public View show(int id) {
        View localView = mLayout.findViewById(id);
        if (localView == null) {
            return null;
        }
        localView.setVisibility(View.VISIBLE);
        return localView;
    }

}
