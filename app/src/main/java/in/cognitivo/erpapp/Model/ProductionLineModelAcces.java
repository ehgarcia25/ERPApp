package in.cognitivo.erpapp.Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.cognitivo.erpapp.ApiInterface.ProductionLineInterface;
import in.cognitivo.erpapp.Entity.ProductionLine;
import in.cognitivo.erpapp.Fragment.ProductionLineAdapter;
import in.cognitivo.erpapp.Fragment.ProductionLineFragment;

import in.cognitivo.erpapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 29/04/17.
 */

public class ProductionLineModelAcces extends ModelAcces {

    private ProductionLineInterface service;
    private ArrayList<ProductionLine> productionLines;
    private RecyclerView recyclerView;
    private ProductionLineFragment.OnListFragmentInteractionListener mListener;

    public ProductionLineModelAcces(String BASE_URL) {
        super(BASE_URL, ProductionLineInterface.class);
        service = (ProductionLineInterface) super.createService();
    }



    public void callProductionLine(String id, final View view, ProductionLineFragment.OnListFragmentInteractionListener mListener1, final ViewGroup container) {

        Call<List<ProductionLine>> call = service.getProductionLine(id);


        recyclerView = (RecyclerView) view;

        mListener = mListener1;

        call.enqueue(new Callback<List<ProductionLine>>() {

            @Override
            public void onResponse(Call<List<ProductionLine>> call, Response<List<ProductionLine>> response) {
                Log.e("Respuesta linea", response.toString());


                if(response.body() != null){
                    productionLines = (ArrayList<ProductionLine>) response.body();
                    recyclerView.setAdapter(new ProductionLineAdapter(productionLines, mListener));
                }



                container.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ProductionLine>> call, Throwable throwable) {
                Log.e("Falla linea", throwable.getMessage().toString());
            }
        });



    }






}
