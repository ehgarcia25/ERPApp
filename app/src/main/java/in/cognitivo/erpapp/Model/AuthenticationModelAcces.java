package in.cognitivo.erpapp.Model;

import android.content.Context;
import android.util.Log;

import in.cognitivo.erpapp.ApiInterface.LogInInterface;
import in.cognitivo.erpapp.Entity.User;
import in.cognitivo.erpapp.Utility.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 29/04/17.
 */

public class AuthenticationModelAcces extends ModelAcces {

    private LogInInterface service;
    private int logingOut = -1;
    private User user;
    private Context context_session;
    public AuthenticationModelAcces(String BASE_URL) {
        super(BASE_URL, LogInInterface.class);
        service = (LogInInterface) super.createService();
    }





    public void LogingOut() {
            callLogOut();
    }



    public void callLoginCheckHttp(String mEmail, String mPassword, Context context) {
        context_session = context;
        Call<User> logchcek = service.logCheck(mEmail, mPassword);
        Log.e("Peticion token", mEmail + mPassword);

        logchcek.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> logchcek, Response<User> response) {
                Log.e("Respuesta login check", response.toString());

                user = response.body();
                // Guardar usuario en preferencias
                SessionManager.get(context_session).saveUser(user);

            }

            @Override
            public void onFailure(Call<User> logchcek, Throwable throwable) {
                Log.e("Falla login check", throwable.getMessage().toString());

            }
        });

    }

    private void callLogOut() {
        Call<String> logchcek = service.logOut();
        logchcek.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> logchcek, Response<String> response) {
                Log.e("Respuesta log out", response.toString());
                logingOut =  response.body().equals("OK") ? 1 : 0;
                Log.e("log out boolean", logingOut + "");

            }

            @Override
            public void onFailure(Call<String> logchcek, Throwable throwable) {
                Log.e("Falla log out", throwable.getMessage().toString());

            }
        });

    }

    public User getUser(){
        return user;
    }


}
