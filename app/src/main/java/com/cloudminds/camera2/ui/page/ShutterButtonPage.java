package com.cloudminds.camera2.ui.page;

import android.view.View;

import com.cloudminds.camera2.R;
import com.cloudminds.camera2.ui.ViewInflater;
import com.cloudminds.camera2.ui.widget.RotateImageView;

public class ShutterButtonPage
  extends ModePage
{
  private RotateImageView mNavigation = (RotateImageView)getView().findViewById(R.id.navigation_spot);
  private RotateImageView mShutterButton = (RotateImageView)getView().findViewById(R.id.shutter_button);
  
  public ShutterButtonPage(ViewInflater paramViewInflater, int paramInt)
  {
    super(paramViewInflater, paramInt);
    getView().setVisibility(View.INVISIBLE);
  }
  
  public void hide()
  {
    hide(R.id.navigation_spot);
    superHide();
  }
  
  public boolean onBackPressed()
  {
    return  true;//mShutterButton.onBackPressed();
  }

  
  public void onColorTextAction(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      mNavigation.setVisibility(View.INVISIBLE);
      return;
    }
    mNavigation.setVisibility(View.VISIBLE);
  }

  
  public void pause() {}
  
  public void resume() {}
  
  public void show()
  {
    show(R.id.navigation_spot);
    superShow();
  }
  
  public void superHide()
  {
    super.hide();
  }
  
  public void superShow()
  {
    super.show();
  }
}
