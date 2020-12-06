package com.kelompokd.pbp_uas_a_keld.UnitTest;

import android.content.Context;

public interface LoginView {
    String getEmail();
    void showEmailError(String message);
    String getPassword();
    void showPasswordError(String message);
    void startMainActivity();
    void showLoginError(String message);
    Context getContext();
}