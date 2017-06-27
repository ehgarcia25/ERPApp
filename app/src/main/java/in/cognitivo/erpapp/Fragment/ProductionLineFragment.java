package in.cognitivo.erpapp.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.cognitivo.erpapp.Entity.ProductionLine;
import in.cognitivo.erpapp.Model.ProductionLineModelAcces;
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
public class ProductionLineFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String  id_user = "id_user";
    private String mParam1;

    private ProductionLineAdapter mAdapter;

    private OnListFragmentInteractionListener mListener;

    private  RecyclerView recycleview_aux;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductionLineFragment() {
    }

    public static ProductionLineFragment newProductionLine(String id) {
        ProductionLineFragment fragment = new ProductionLineFragment();
        Bundle args = new Bundle();
        args.putString(id_user, id);
        fragment.setArguments(args);
        return fragment;
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(id_user);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productionline_list, container, false);
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


            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

           // recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

            ProductionLineModelAcces productionLineModelAcces = new ProductionLineModelAcces(URL.BASE_URL);

            productionLineModelAcces.callProductionLine(mParam1,view,mListener,container);

            recycleview_aux = recyclerView;
            /**
             * RecyclerView: Implementing single item click and long press (Part-II)
             * */
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                    recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well

                    String id_line = ((TextView) view.findViewById(R.id.id)).getText().toString();

                    listProductionOrder(id_line);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


        }


        //container.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

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

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        //search(searchView);
    }

  /*  private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (mAdapter != null)
            mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
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
        void onListFragmentInteraction(ProductionLine item);
    }






    public void listProductionOrder(String id_production_line){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProductionOrderFragment productionOrderFragment = new ProductionOrderFragment();
        fragmentTransaction.replace(R.id.content, productionOrderFragment.newProductionOrder(id_production_line));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        
    }


}
