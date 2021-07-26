// Generated by view binder compiler. Do not edit!
package com.fintrex.fintrexfinance.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.fintrex.fintrexfinance.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityBranches2Binding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView branchback;

  @NonNull
  public final MaterialToolbar branchtoolbar;

  @NonNull
  public final ImageView menu;

  private ActivityBranches2Binding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView branchback, @NonNull MaterialToolbar branchtoolbar,
      @NonNull ImageView menu) {
    this.rootView = rootView;
    this.branchback = branchback;
    this.branchtoolbar = branchtoolbar;
    this.menu = menu;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityBranches2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityBranches2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_branches2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityBranches2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.branchback;
      ImageView branchback = rootView.findViewById(id);
      if (branchback == null) {
        break missingId;
      }

      id = R.id.branchtoolbar;
      MaterialToolbar branchtoolbar = rootView.findViewById(id);
      if (branchtoolbar == null) {
        break missingId;
      }

      id = R.id.menu;
      ImageView menu = rootView.findViewById(id);
      if (menu == null) {
        break missingId;
      }

      return new ActivityBranches2Binding((ConstraintLayout) rootView, branchback, branchtoolbar,
          menu);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}