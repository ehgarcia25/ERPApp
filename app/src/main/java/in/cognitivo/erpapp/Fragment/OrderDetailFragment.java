package in.cognitivo.erpapp.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import in.cognitivo.erpapp.Entity.ProductionOrderDetail;
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
public class OrderDetailFragment extends Fragment implements NumberPicker.OnValueChangeListener{

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String  id_production_order = "id_production_order";
    private String mParam1;

    private TextView tv;
    private String id_execution;
    static Dialog d ;

    private OrderDetailAdapter mAdapter;
    private  RecyclerView recycleview_aux;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderDetailFragment() {
    }

    public static OrderDetailFragment newOrderDetail(String id) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(id_production_order, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(id_production_order);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderdetail_list, container, false);

        container.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

            ProductionOrderModelAcces productionOrderModelAcces = new ProductionOrderModelAcces(URL.BASE_URL);

            productionOrderModelAcces.callProductionOrderDetail(mParam1,view,mListener,container);

            recycleview_aux = recyclerView;

            /**
             * RecyclerView: Implementing single item click and long press (Part-II)
             * */
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                    recyclerView, new ClickListener() {


                @Override
                public void onClick(View view,int position) {
                    //Values are passing to activity & to fragment as well

                    id_execution = ((TextView) view.findViewById(R.id.id_detail)).getText().toString();
                    tv = (TextView) view.findViewById(R.id.id);
                    show(view,recyclerView,position);



                }

                @Override
                public void onLongClick(View view, int position) {
                    String id_order_detail = ((TextView) view.findViewById(R.id.id_detail)).getText().toString();
                    listExecutionDetail(id_order_detail);
                }
            }));


            //recyclerView.setAdapter(new OrderDetailAdapter(DummyContent.ITEMS, mListener));
        }
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

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
        void onListFragmentInteraction(ProductionOrderDetail item);
    }


    public void show(View view, final RecyclerView recyclerView, final int position)
    {

        final Dialog d = new Dialog(view.getContext());
        d.setTitle("Adicionar");
        d.setContentView(R.layout.dialog_quantity);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(1000); // max value 100
        np.setMinValue(0);   // min value 0
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue())); //set the value to textview

                ProductionOrderModelAcces productionOrderModelAcces = new ProductionOrderModelAcces(URL.BASE_URL);
                productionOrderModelAcces.updateExecution(id_execution,String.valueOf(np.getValue()));

                //update adapter
               // productionOrderModelAccesDetail.getOrderDetailAdpater().notifyChanged(position);

                OrderDetailAdapter orderDetailAdapter = (OrderDetailAdapter) recyclerView.getAdapter();
                int current_value = Integer.parseInt(orderDetailAdapter.getmValues().get(position).getQuantity_real());
                orderDetailAdapter.getmValues().get(position).setQuantity_real(String.valueOf(np.getValue() + current_value));
                recyclerView.setAdapter(orderDetailAdapter);
                recyclerView.getAdapter().notifyItemChanged(position);
                d.dismiss();

            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();


    }

    public void listExecutionDetail(String id_order_detail){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ExecutionDetailFragment executionDetailFragment = new ExecutionDetailFragment();
        fragmentTransaction.replace(R.id.content, executionDetailFragment.newExecutionDetail(id_order_detail));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        searchView.setQueryHint("Search");
        search(searchView);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter = (OrderDetailAdapter) recycleview_aux.getAdapter();
                if (mAdapter != null)
                    mAdapter.getFilter().filter(newText);

                return true;
            }
        });
    }
}
