package in.cognitivo.erpapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
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

import in.cognitivo.erpapp.Entity.ExecutionDetail;
import in.cognitivo.erpapp.Model.ProductionOrderModelAcces;
import in.cognitivo.erpapp.R;
import in.cognitivo.erpapp.Utility.URL;
import in.cognitivo.erpapp.dummy.DummyContent;
import in.cognitivo.erpapp.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExecutionDetailFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String  id_order_detail = "id_order_detail";
    private String mParam1;

    private ExecutionDetailAdapter mAdapter;
    private  RecyclerView recycleview_aux;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExecutionDetailFragment() {
    }

    public static ExecutionDetailFragment newExecutionDetail(String id) {
        ExecutionDetailFragment fragment = new ExecutionDetailFragment();
        Bundle args = new Bundle();
        args.putString(id_order_detail, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(id_order_detail);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_executiondetail_list, container, false);
        container.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
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

            productionOrderModelAcces.callExecutionDetail(mParam1,view,mListener,container);
            //recyclerView.setAdapter(new ExecutionDetailAdapter(DummyContent.ITEMS, mListener));

            recycleview_aux = recyclerView;
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
        void onListFragmentInteraction(ExecutionDetail item);
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
                mAdapter = (ExecutionDetailAdapter) recycleview_aux.getAdapter();
                if (mAdapter != null)
                    mAdapter.getFilter().filter(newText);

                return true;
            }
        });
    }
}
