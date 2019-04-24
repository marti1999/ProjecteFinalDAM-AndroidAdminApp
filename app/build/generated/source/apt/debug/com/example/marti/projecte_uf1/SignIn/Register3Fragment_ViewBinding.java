// Generated code from Butter Knife. Do not modify!
package com.example.marti.projecte_uf1.SignIn;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.marti.projecte_uf1.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Register3Fragment_ViewBinding implements Unbinder {
  private Register3Fragment target;

  @UiThread
  public Register3Fragment_ViewBinding(Register3Fragment target, View source) {
    this.target = target;

    target.donorBt = Utils.findRequiredViewAsType(source, R.id.donorBt, "field 'donorBt'", ImageButton.class);
    target.requestorBt = Utils.findRequiredViewAsType(source, R.id.requestorBt, "field 'requestorBt'", ImageButton.class);
    target.questionLayout = Utils.findRequiredViewAsType(source, R.id.questionLayout, "field 'questionLayout'", LinearLayout.class);
    target.donorLayout = Utils.findRequiredViewAsType(source, R.id.donorLayout, "field 'donorLayout'", LinearLayout.class);
    target.register3layout = Utils.findRequiredViewAsType(source, R.id.register3layout, "field 'register3layout'", LinearLayout.class);
    target.spinnerMembers = Utils.findRequiredViewAsType(source, R.id.spinnerMembers, "field 'spinnerMembers'", Spinner.class);
    target.spinnerIncome = Utils.findRequiredViewAsType(source, R.id.spinnerIncome, "field 'spinnerIncome'", Spinner.class);
    target.requestorLayout = Utils.findRequiredViewAsType(source, R.id.requestorLayout, "field 'requestorLayout'", LinearLayout.class);
    target.saveRequestorBt = Utils.findRequiredViewAsType(source, R.id.saveRequestorBt, "field 'saveRequestorBt'", Button.class);
    target.errorLayout = Utils.findRequiredViewAsType(source, R.id.errorLayout, "field 'errorLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Register3Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.donorBt = null;
    target.requestorBt = null;
    target.questionLayout = null;
    target.donorLayout = null;
    target.register3layout = null;
    target.spinnerMembers = null;
    target.spinnerIncome = null;
    target.requestorLayout = null;
    target.saveRequestorBt = null;
    target.errorLayout = null;
  }
}
