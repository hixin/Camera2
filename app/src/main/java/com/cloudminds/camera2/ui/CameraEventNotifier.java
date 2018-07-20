package com.cloudminds.camera2.ui;

import com.cloudminds.camera2.model.parameter.Parameter;


public abstract interface CameraEventNotifier
{
  public abstract void onParameterChanged(Parameter parameter);
}
