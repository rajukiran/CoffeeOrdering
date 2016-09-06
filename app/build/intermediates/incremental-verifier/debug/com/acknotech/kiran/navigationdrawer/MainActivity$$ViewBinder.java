// Generated code from Butter Knife. Do not modify!
package com.acknotech.kiran.navigationdrawer;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.acknotech.kiran.navigationdrawer.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558559, "field '_logoutButton'");
    target._logoutButton = finder.castView(view, 2131558559, "field '_logoutButton'");
  }

  @Override public void unbind(T target) {
    target._logoutButton = null;
  }
}
