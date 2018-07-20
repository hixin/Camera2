package com.cloudminds.camera2.ui.page;

public abstract interface Page
{
  public abstract void hide();
  
  public abstract boolean onBackPressed();
  
  public abstract void pause();
  
  public abstract void resume();
  
  public abstract void show();
}
