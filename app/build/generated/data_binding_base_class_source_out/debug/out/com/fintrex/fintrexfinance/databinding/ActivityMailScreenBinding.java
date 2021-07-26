// Generated by data binding compiler. Do not edit!
package com.fintrex.fintrexfinance.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.fintrex.fintrexfinance.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMailScreenBinding extends ViewDataBinding {
  @NonNull
  public final ImageView mailScreenback;

  @NonNull
  public final MaterialToolbar mailScreentoolbar;

  @NonNull
  public final ImageView menu;

  @NonNull
  public final AppCompatImageButton newmsgFloatbtn;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final ViewPager viewPager;

  protected ActivityMailScreenBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView mailScreenback, MaterialToolbar mailScreentoolbar, ImageView menu,
      AppCompatImageButton newmsgFloatbtn, TabLayout tabLayout, ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.mailScreenback = mailScreenback;
    this.mailScreentoolbar = mailScreentoolbar;
    this.menu = menu;
    this.newmsgFloatbtn = newmsgFloatbtn;
    this.tabLayout = tabLayout;
    this.viewPager = viewPager;
  }

  @NonNull
  public static ActivityMailScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mail_screen, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMailScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMailScreenBinding>inflateInternal(inflater, R.layout.activity_mail_screen, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMailScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mail_screen, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMailScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMailScreenBinding>inflateInternal(inflater, R.layout.activity_mail_screen, null, false, component);
  }

  public static ActivityMailScreenBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityMailScreenBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMailScreenBinding)bind(component, view, R.layout.activity_mail_screen);
  }
}