package com.cloudminds.camera2.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.cloudminds.camera2.R;

public class ViewInflater
{
  Context mContext;
  ViewGroup mRootView;
  
  public ViewInflater(Context paramContext)
  {
    mContext = paramContext;
    mRootView = ((ViewGroup)((Activity)paramContext).findViewById(R.id.container));
  }
  
  public View inflate(int paramInt)
  {
    View.inflate(mContext, paramInt, mRootView);
    return mRootView.getChildAt(mRootView.getChildCount() - 1);
  }
  
  public View inflate(ViewGroup paramViewGroup, int paramInt)
  {
    View.inflate(mContext, paramInt, paramViewGroup);
    return paramViewGroup.getChildAt(paramViewGroup.getChildCount() - 1);
  }
  
  public void remove(View paramView)
  {
    mRootView.removeView(paramView);
  }
  
  public void remove(ViewGroup paramViewGroup, View paramView)
  {
    paramViewGroup.removeView(paramView);
  }
}
