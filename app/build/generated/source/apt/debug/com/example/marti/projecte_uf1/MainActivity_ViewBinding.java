// Generated code from Butter Knife. Do not modify!
package com.example.marti.projecte_uf1;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131296570;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.tvSignIn = Utils.findRequiredViewAsType(source, R.id.tvSignIn, "field 'tvSignIn'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvForgotPassword, "field 'tvForgotPassword' and method 'onViewClicked'");
    target.tvForgotPassword = Utils.castView(view, R.id.tvForgotPassword, "field 'tvForgotPassword'", TextView.class);
    view2131296570 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSignIn = null;
    target.tvForgotPassword = null;

    view2131296570.setOnClickListener(null);
    view2131296570 = null;
  }
}
