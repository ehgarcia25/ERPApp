package in.cognitivo.erpapp.Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.cognitivo.erpapp.ApiInterface.ApiInterface;
import in.cognitivo.erpapp.Entity.ExecutionDetail;
import in.cognitivo.erpapp.Entity.ProductionOrder;
import in.cognitivo.erpapp.Entity.ProductionOrderDetail;
import in.cognitivo.erpapp.Fragment.ExecutionDetailAdapter;
import in.cognitivo.erpapp.Fragment.ExecutionDetailFragment;
import in.cognitivo.erpapp.Fragment.OrderDetailAdapter;
import in.cognitivo.erpapp.Fragment.OrderDetailFragment;
import in.cognitivo.erpapp.Fragment.ProductionOrderAdapter;
import in.cognitivo.erpapp.Fragment.ProductionOrderFragment;
import in.cognitivo.erpapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cognitivo on 19/05/17.
 */

public class ProductionOrderModelAcces extends ModelAcces {

    private ApiInterface service;
    private ArrayList<ProductionOrder> productionOrders;
    private ArrayList<ProductionOrderDetail> productionOrderDetails;
    private ArrayList<ExecutionDetail> executionDetails;
    private RecyclerView recyclerView;
    private ProductionOrderFragment.OnListFragmentInteractionListener mListener;
    private OrderDetailFragment.OnListFragmentInteractionListener mListener_detail;
    private ExecutionDetailFragment.OnListFragmentInteractionListener mListener_execution;

    private OrderDetailAdapter orderDetailAdapter;

    public ProductionOrderModelAcces(String BASE_URL) {

        super(BASE_URL, ApiInterface.class);
        service = (ApiInterface) super.createService();


    }


    //get list orders
    public void callProductionOrder(String id, View view, ProductionOrderFragment.OnListFragmentInteractionListener mListener1,final ViewGroup container) {

        Call<List<ProductionOrder>> call = service.getProductionOrder(id);


        recyclerView = (RecyclerView) view;

        mListener = mListener1;

        call.enqueue(new Callback<List<ProductionOrder>>() {

            @Override
            public void onResponse(Call<List<ProductionOrder>> call, Response<List<ProductionOrder>> response) {
                Log.e("Respuesta order", response.toString());

                if(response.body() != null){

                    productionOrders = (ArrayList<ProductionOrder>) response.body();

                    recyclerView.setAdapter(new ProductionOrderAdapter(productionOrders, mListener));
                }


                container.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ProductionOrder>> call, Throwable throwable) {
                Log.e("Falla order", throwable.getMessage().toString());
            }
        });

    }


    //get list details
    public void callProductionOrderDetail(String id, View view, OrderDetailFragment.OnListFragmentInteractionListener mListener1,final ViewGroup container) {

        Call<List<ProductionOrderDetail>> call = service.getProductionOrderDetail(id);


        recyclerView = (RecyclerView) view;

        mListener_detail = mListener1;

        call.enqueue(new Callback<List<ProductionOrderDetail>>() {

            @Override
            public void onResponse(Call<List<ProductionOrderDetail>> call, Response<List<ProductionOrderDetail>> response) {
                Log.e("Respuesta order", response.toString());

                productionOrderDetails = (ArrayList<ProductionOrderDetail>) response.body();

                if(response.body() != null){

                    orderDetailAdapter =  new OrderDetailAdapter(productionOrderDetails, mListener_detail);

                    recyclerView.setAdapter(orderDetailAdapter);

                }


                container.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                return;
            }

            @Override
            public void onFailure(Call<List<ProductionOrderDetail>> call, Throwable throwable) {
                Log.e("Falla order", throwable.getMessage().toString());
            }
        });

    }

    public void updateExecution(String id,String quantity){

        Call<String> call = service.updateExecution(id,quantity);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Respuesta order", response.toString());


            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Falla order detail", throwable.getMessage().toString());
            }
        });


    }

    public void deleteExecution(String id){

        Call<String> call = service.deleteExecution(id);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Respuesta order", response.toString());


            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Falla order detail", throwable.getMessage().toString());
            }
        });


    }

    //get list execution details
    public void callExecutionDetail(String id, View view, ExecutionDetailFragment.OnListFragmentInteractionListener mListener1, final ViewGroup container) {

        Call<List<ExecutionDetail>> call = service.getExecutionDetail(id);


        recyclerView = (RecyclerView) view;

        mListener_execution = mListener1;

        call.enqueue(new Callback<List<ExecutionDetail>>() {

            @Override
            public void onResponse(Call<List<ExecutionDetail>> call, Response<List<ExecutionDetail>> response) {
                Log.e("Respuesta execution", response.toString());

                executionDetails = (ArrayList<ExecutionDetail>) response.body();

                if(response.body() != null){

                    ExecutionDetailAdapter executionDetailAdapter =  new ExecutionDetailAdapter(executionDetails, mListener_execution);

                    recyclerView.setAdapter(executionDetailAdapter);

                }


                container.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                return;
            }

            @Override
            public void onFailure(Call<List<ExecutionDetail>> call, Throwable throwable) {
                Log.e("Falla execution", throwable.getMessage().toString());
            }
        });

    }



    public OrderDetailAdapter getOrderDetailAdpater(){
        return orderDetailAdapter;
    }


}
