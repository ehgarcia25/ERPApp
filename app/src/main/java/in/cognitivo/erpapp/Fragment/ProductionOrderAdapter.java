package in.cognitivo.erpapp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.cognitivo.erpapp.Entity.ProductionOrder;
import in.cognitivo.erpapp.R;


/**
 * specified {@link ProductionOrderFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductionOrderAdapter extends RecyclerView.Adapter<ProductionOrderAdapter.ViewHolder>  implements Filterable {

    private final List<ProductionOrder> mValues;
    private  List<ProductionOrder> mValuesFilter;
    private final ProductionOrderFragment.OnListFragmentInteractionListener mListener;

    public ProductionOrderAdapter(List<ProductionOrder> items, ProductionOrderFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mValuesFilter = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_productionorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValuesFilter.get(position).getId());
        holder.mContentView.setText(mValuesFilter.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValuesFilter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        //public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mValuesFilter =  mValues;
                } else {

                    ArrayList<ProductionOrder> filteredList = new ArrayList<>();

                    for (ProductionOrder androidVersion : mValues) {

                        if (androidVersion.getName().toLowerCase().contains(charString) && androidVersion.getName() != null) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mValuesFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mValuesFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mValuesFilter = (ArrayList<ProductionOrder>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
