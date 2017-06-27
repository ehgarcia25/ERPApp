package in.cognitivo.erpapp.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.cognitivo.erpapp.Entity.ProductionOrder;
import in.cognitivo.erpapp.Model.ProductionOrderModelAcces;
import in.cognitivo.erpapp.R;
import in.cognitivo.erpapp.Utility.ClickListener;
import in.cognitivo.erpapp.Utility.RecyclerTouchListener;
import in.cognitivo.erpapp.Utility.URL;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductionOrderFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String  id_production_line = "id_production_line";
    private String mParam1;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductionOrderFragment() {
    }

    public static ProductionOrderFragment newProductionOrder(String id) {
        ProductionOrderFragment fragment = new ProductionOrderFragment();
        Bundle args = new Bundle();
        args.putString(id_production_line, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(id_production_line);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productionorder_list, container, false);
        container.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
            ProductionOrderModelAcces productionOrderModelAcces = new ProductionOrderModelAcces(URL.BASE_URL);

            productionOrderModelAcces.callProductionOrder(mParam1,view,mListener,container);
            //recyclerView.setAdapter(new ProductionOrderAdapter(DummyContent.ITEMS, mListener));


            /**
             * RecyclerView: Implementing single item click and long press (Part-II)
             * */
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                    recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well

                    String id_order = ((TextView) view.findViewById(R.id.id)).getText().toString();

                    listOrderDetail(id_order);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ProductionOrder item);
    }


    public void listOrderDetail(String id_order){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
        fragmentTransaction.replace(R.id.content, orderDetailFragment.newOrderDetail(id_order));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
