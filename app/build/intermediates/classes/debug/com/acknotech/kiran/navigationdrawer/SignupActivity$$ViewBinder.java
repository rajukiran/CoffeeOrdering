// Generated code from Butter Knife. Do not modify!
package com.acknotech.kiran.navigationdrawer;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SignupActivity$$ViewBinder<T extends com.acknotech.kiran.navigationdrawer.SignupActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558514, "field '_firstnameText'");
    target._firstnameText = finder.castView(view, 2131558514, "field '_firstnameText'");
    view = finder.findRequiredView(source, 2131558515, "field '_lastnameText'");
    target._lastnameText = finder.castView(view, 2131558515, "field '_lastnameText'");
    view = finder.findRequiredView(source, 2131558508, "field '_emailText'");
    target._emailText = finder.castView(view, 2131558508, "field '_emailText'");
    view = finder.findRequiredView(source, 2131558523, "field '_signupButton'");
    target._signupButton = finder.castView(view, 2131558523, "field '_signupButton'");
    view = finder.findRequiredView(source, 2131558524, "field '_loginLink'");
    target._loginLink = finder.castView(view, 2131558524, "field '_loginLink'");
    view = finder.findRequiredView(source, 2131558516, "field '_phone_number'");
    target._phone_number = finder.castView(view, 2131558516, "field '_phone_number'");
  }

  @Override public void unbind(T target) {
    target._firstnameText = null;
    target._lastnameText = null;
    target._emailText = null;
    target._signupButton = null;
    target._loginLink = null;
    target._phone_number = null;
  }
}
