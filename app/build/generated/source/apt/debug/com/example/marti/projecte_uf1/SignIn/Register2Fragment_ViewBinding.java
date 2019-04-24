// Generated code from Butter Knife. Do not modify!
package com.example.marti.projecte_uf1.SignIn;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.marti.projecte_uf1.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Register2Fragment_ViewBinding implements Unbinder {
  private Register2Fragment target;

  @UiThread
  public Register2Fragment_ViewBinding(Register2Fragment target, View source) {
    this.target = target;

    target.etPassword = Utils.findRequiredViewAsType(source, R.id.etPassword, "field 'etPassword'", TextInputEditText.class);
    target.etPassword2 = Utils.findRequiredViewAsType(source, R.id.etPassword2, "field 'etPassword2'", TextInputEditText.class);
    target.etQuestion = Utils.findRequiredViewAsType(source, R.id.etQuestion, "field 'etQuestion'", TextInputEditText.class);
    target.etAnswer = Utils.findRequiredViewAsType(source, R.id.etAnswer, "field 'etAnswer'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Register2Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etPassword = null;
    target.etPassword2 = null;
    target.etQuestion = null;
    target.etAnswer = null;
  }
}
