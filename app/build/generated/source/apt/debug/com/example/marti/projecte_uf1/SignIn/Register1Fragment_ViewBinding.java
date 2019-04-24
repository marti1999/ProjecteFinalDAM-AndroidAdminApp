// Generated code from Butter Knife. Do not modify!
package com.example.marti.projecte_uf1.SignIn;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.marti.projecte_uf1.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Register1Fragment_ViewBinding implements Unbinder {
  private Register1Fragment target;

  private View view2131296391;

  private View view2131296390;

  @UiThread
  public Register1Fragment_ViewBinding(final Register1Fragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ivMale, "field 'ivMale' and method 'onViewClicked'");
    target.ivMale = Utils.castView(view, R.id.ivMale, "field 'ivMale'", ImageView.class);
    view2131296391 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ivFemale, "field 'ivFemale' and method 'onViewClicked'");
    target.ivFemale = Utils.castView(view, R.id.ivFemale, "field 'ivFemale'", ImageView.class);
    view2131296390 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.etName = Utils.findRequiredViewAsType(source, R.id.etName, "field 'etName'", TextInputEditText.class);
    target.etLastName = Utils.findRequiredViewAsType(source, R.id.etLastName, "field 'etLastName'", TextInputEditText.class);
    target.etDNI = Utils.findRequiredViewAsType(source, R.id.etDNI, "field 'etDNI'", TextInputEditText.class);
    target.etMail = Utils.findRequiredViewAsType(source, R.id.etMail, "field 'etMail'", TextInputEditText.class);
    target.etBirth = Utils.findRequiredViewAsType(source, R.id.etBirth, "field 'etBirth'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Register1Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivMale = null;
    target.ivFemale = null;
    target.etName = null;
    target.etLastName = null;
    target.etDNI = null;
    target.etMail = null;
    target.etBirth = null;

    view2131296391.setOnClickListener(null);
    view2131296391 = null;
    view2131296390.setOnClickListener(null);
    view2131296390 = null;
  }
}
