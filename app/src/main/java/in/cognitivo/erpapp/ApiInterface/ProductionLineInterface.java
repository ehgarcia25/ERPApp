package in.cognitivo.erpapp.ApiInterface;

import java.util.List;

import in.cognitivo.erpapp.Entity.ProductionLine;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cognitivo on 18/05/17.
 */

public interface ProductionLineInterface {

    public static final String BASE_URL = "api/";

    @FormUrlEncoded
    @POST(BASE_URL+"production_line")
    Call<List<ProductionLine>> getProductionLine(@Field("id_user") String id_user);

}
