package in.cognitivo.erpapp.ApiInterface;

import java.util.List;

import in.cognitivo.erpapp.Entity.ExecutionDetail;
import in.cognitivo.erpapp.Entity.ProductionLine;
import in.cognitivo.erpapp.Entity.ProductionOrder;
import in.cognitivo.erpapp.Entity.ProductionOrderDetail;
import in.cognitivo.erpapp.Entity.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by cognitivo on 19/05/17.
 */

public interface ApiInterface {


    public static final String BASE_URL = "api/";

    @FormUrlEncoded
    @POST(BASE_URL+"auth/login")
    Call<User> logCheck(@Field("user") String user, @Field("password") String password);


    @GET(BASE_URL+"auth/logout")
    Call<String> logOut();

    @FormUrlEncoded
    @POST(BASE_URL+"production_line")
    Call<List<ProductionLine>> getProductionLine(@Field("id_user") String id_user);


    @GET(BASE_URL+"production_order/{id_production_line}")
    Call<List<ProductionOrder>> getProductionOrder(@Path("id_production_line") String id_production_line);

    @GET(BASE_URL+"production_order_detail/{id_production_order}")
    Call<List<ProductionOrderDetail>> getProductionOrderDetail(@Path("id_production_order") String id_production_order);

    @FormUrlEncoded
    @POST(BASE_URL+"update_execution/{id}")
    Call<String> updateExecution(@Path("id") String id_execution, @Field("quantity") String quantity);

    @GET(BASE_URL+"execution_detail/{id_order_detail}")
    Call<List<ExecutionDetail>> getExecutionDetail(@Path("id_order_detail") String id_order_detail);

    @GET(BASE_URL+"execution_detail_delete/{id_execution}")
    Call<String> deleteExecution(@Path("id_execution") String id_execution);

}
