package com.cloudminds.camera2.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.cloudminds.camera2.R;

public class Support {
  private final String contentDescription;
  private final int defaultSupport;
  private final int defaultType;
  private final int entry;
  private final int icon;
  private final String menuPreferenceKey;
  private final String message;
  private final boolean showImageInParent;
  private final int supportedCamera;
  private final String title;
  private int titleResourceId;
  private final String value;
  private final boolean visible;

  public Support(Context context, AttributeSet attrs) {
    super();
    TypedArray aray = context.obtainStyledAttributes(attrs, R.styleable.MenuCommon, 0, 0);
    this.titleResourceId = aray.getResourceId(R.styleable.MenuCommon_titleResourceId, 0);
    this.title = aray.getString(R.styleable.MenuCommon_title);
    this.value = aray.getString(R.styleable.MenuCommon_value);
    this.icon = aray.getResourceId(R.styleable.MenuCommon_icon, 0);
    this.defaultType = aray.getInt(R.styleable.MenuCommon_defaultType, 0);
    this.entry = aray.getInt(R.styleable.MenuCommon_entry, 7);
    this.supportedCamera = aray.getInt(R.styleable.MenuCommon_supportedCamera, 3);
    this.defaultSupport = aray.getInt(R.styleable.MenuCommon_defaultSupport, 1);
    this.contentDescription = aray.getString(R.styleable.MenuCommon_contentDescription);
    this.message = aray.getString(R.styleable.MenuCommon_message);
    this.visible = aray.getBoolean(R.styleable.MenuCommon_visible, true);
    this.menuPreferenceKey = aray.getString(R.styleable.MenuCommon_menuPreferenceKey);
    this.showImageInParent = aray.getBoolean(R.styleable.MenuCommon_showImageInParent, false);
    aray.recycle();
  }

  public Support(String title, String value, int icon, int defaultType, int entry, int supportedCamera,
                 int defaultSupport) {
    super();
    this.title = title;
    this.value = value;
    this.icon = icon;
    this.defaultType = defaultType;
    this.entry = entry;
    this.supportedCamera = supportedCamera;
    this.defaultSupport = defaultSupport;
    this.contentDescription = null;
    this.message = null;
    this.visible = true;
    this.menuPreferenceKey = null;
    this.showImageInParent = false;
  }

  public String getContentDescription() {
    return this.contentDescription;
  }

  public int getDefaultSupport() {
    return this.defaultSupport;
  }

  public int getEntry() {
    return this.entry;
  }

  public int getIcon() {
    return this.icon;
  }

  public String getMenuPreferenceKey() {
    return this.menuPreferenceKey;
  }

  public String getMessage() {
    return this.message;
  }

  public int getSupportedCamera() {
    return this.supportedCamera;
  }

  public String getTitle() {
    return this.title;
  }

  public int getTitleResourceId() {
    return this.titleResourceId;
  }

  public String getValue() {
    return this.value;
  }

  public boolean isBackDefault() {
    boolean result = (this.defaultType & 1) != 0 ? true : false;
    return result;
  }

  public boolean isFrontDefault() {
    boolean result = (this.defaultType & 2) != 0 ? true : false;
    return result;
  }

  public boolean isShowImageInParent() {
    return this.showImageInParent;
  }

  public boolean isVisible() {
    return this.visible;
  }
}

