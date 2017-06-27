package in.cognitivo.erpapp.ApiInterface;

import in.cognitivo.erpapp.Entity.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by root on 30/03/17.
 */

public interface LogInInterface {

    public static final String BASE_URL = "api/";

    @FormUrlEncoded
    @POST(BASE_URL+"auth/login")
    Call<User> logCheck(@Field("user") String user, @Field("password") String password);


    @GET(BASE_URL+"auth/logout")
    Call<String> logOut();
}
