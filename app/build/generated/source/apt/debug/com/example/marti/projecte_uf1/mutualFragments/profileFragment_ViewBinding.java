// Generated code from Butter Knife. Do not modify!
package com.example.marti.projecte_uf1.mutualFragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.marti.projecte_uf1.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class profileFragment_ViewBinding implements Unbinder {
  private profileFragment target;

  private View view2131296399;

  @UiThread
  public profileFragment_ViewBinding(final profileFragment target, View source) {
    this.target = target;

    View view;
    target.type = Utils.findRequiredViewAsType(source, R.id.type, "field 'type'", TextView.class);
    target.pointsLabel = Utils.findRequiredViewAsType(source, R.id.pointsLabel, "field 'pointsLabel'", TextView.class);
    target.points = Utils.findRequiredViewAsType(source, R.id.points, "field 'points'", TextView.class);
    target.amountLabel = Utils.findRequiredViewAsType(source, R.id.amountLabel, "field 'amountLabel'", TextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'amount'", TextView.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.email, "field 'email'", TextView.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", TextView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.dni = Utils.findRequiredViewAsType(source, R.id.dni, "field 'dni'", TextView.class);
    view = Utils.findRequiredView(source, R.id.image, "field 'image' and method 'onViewClicked'");
    target.image = Utils.castView(view, R.id.image, "field 'image'", CircleImageView.class);
    view2131296399 = view;
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
    profileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.type = null;
    target.pointsLabel = null;
    target.points = null;
    target.amountLabel = null;
    target.amount = null;
    target.email = null;
    target.password = null;
    target.name = null;
    target.dni = null;
    target.image = null;

    view2131296399.setOnClickListener(null);
    view2131296399 = null;
  }
}
