package com.cloudminds.camera2.model.capture;

import android.content.Intent;

public class ModuleManager {
    private PhotoModule photoModule;
    private VideoModule videoModule;

     public CameraModule createModule(int modeIndex, Intent intent) {
         switch (modeIndex) {
             case 0:
                 if (photoModule == null) {
                     photoModule = new PhotoModule();
                 }
                 return photoModule;
             case 1:
                 if (videoModule == null) {
                     videoModule = new VideoModule();
                 }
                 return videoModule;
         }

         return null;
     }

}
