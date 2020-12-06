package com.kelompokd.pbp_uas_a_keld.UnitTestLogin;

public class LoginPresenter {

    private LoginView view;
    private LoginService service;
    private LoginCallback callback;

    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked() {
        ///////////////////////////////////////////////////////////////

        if(!view.getEmail().contains("@") || !view.getEmail().contains("."))
        {
            view.showEmailError("Invalid Email");
            return;
        }
        else if(view.getPassword().length()<6)
        {
            view.showPasswordError("Invalid Password 6 Character");
            return;
        }
        else{
            service.login(view, view.getEmail(), view.getPassword(), new
                    LoginCallback() {
                        @Override
                        public void onSuccess(boolean value) {
                            view.startMainActivity();
                        }
                        @Override
                        public void onError() {
                        }
                    });
            return;
        }
    }
}

