package com.kelompokd.pbp_uas_a_keld.UnitTestLogin;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class LoginService
{
    public void login(final LoginView view, String email, String password, final LoginCallback callback)
    {
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest stringRequest = new StringRequest(POST, MorentAPI.URL_LOGIN, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response);
                    System.out.println(obj.getString("message"));

                    if(obj.getString("message").equals("Login Success"))
                    {
                        callback.onSuccess(true);
                    }
                    else
                    {
                        callback.onError();
                        view.showLoginError(obj.getString("message"));
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        queue.add(stringRequest);
    }

    public Boolean getValid(final LoginView view, String email, String password)
    {
        final Boolean[] bool = new Boolean[1];
        login(view, email, password, new LoginCallback()
        {
            @Override
            public void onSuccess(boolean value)
            {
                bool[0] = true;
            }

            @Override
            public void onError()
            {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
