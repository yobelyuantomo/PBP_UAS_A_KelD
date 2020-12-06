package com.kelompokd.pbp_uas_a_keld.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;
    private LoginPresenter presenter;
    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void WhenValidEmailPass() throws Exception
    {
        System.out.println("Test 1 - Valid Email & Pass");

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email       : " + view.getEmail());

        when(view.getPassword()).thenReturn("123456");
        System.out.println("Password    : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Result       : " + service.getValid(view, view.getEmail(), view.getPassword()));
        presenter.onLoginClicked();
    }

    @Test
    public void WhenInvalidEmail() throws Exception
    {
        System.out.println("Test 2 - Invalid Email");

        when(view.getEmail()).thenReturn("yobelyuantomo");
        System.out.println("Email       : " + view.getEmail());

        when(view.getPassword()).thenReturn("123456");
        System.out.println("Password    : " + view.getPassword());
        presenter.onLoginClicked();
        verify(view).showEmailError("Invalid Email");
    }

    @Test
    public void WhenInvalidPassword() throws Exception
    {
        System.out.println("Test 3 - Invalid Password 6 Character");

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email       : " + view.getEmail());

        when(view.getPassword()).thenReturn("213");
        System.out.println("Password    : " + view.getPassword());

        presenter.onLoginClicked();
        verify(view).showPasswordError("Invalid Password 6 Character");
    }

    @Test
    public void WhenValidLogin() throws Exception
    {
        System.out.println("Test 4 - Valid Login");

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email       : " + view.getEmail());

        when(view.getPassword()).thenReturn("11111111");
        System.out.println("Password    : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Result       : " + service.getValid(view, view.getEmail(), view.getPassword()));
        presenter.onLoginClicked();
    }

    @Test
    public void WhenInvalidLogin() throws Exception
    {
        System.out.println("Test 5 - Invalid Login");

        when(view.getEmail()).thenReturn("yobelyuantomo@gmail.com");
        System.out.println("Email       : " + view.getEmail());

        when(view.getPassword()).thenReturn("1234543545345");
        System.out.println("Password    : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(false);
        System.out.println("Result       : " + service.getValid(view, view.getEmail(), view.getPassword()));
        presenter.onLoginClicked();
    }

    @Test
    public void WhenInvalidLogin2() throws Exception
    {
        System.out.println("Test 6 - Invalid Login");

        when(view.getEmail()).thenReturn("yoblack888@gmail.com");
        System.out.println("Email       : " + view.getEmail());

        when(view.getPassword()).thenReturn("11111111");
        System.out.println("Password    : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(false);
        System.out.println("Result       : " + service.getValid(view, view.getEmail(), view.getPassword()));
        presenter.onLoginClicked();
    }
}