package in.cognitivo.erpapp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import in.cognitivo.erpapp.Entity.ExecutionDetail;
import in.cognitivo.erpapp.Model.ProductionOrderModelAcces;
import in.cognitivo.erpapp.R;
import in.cognitivo.erpapp.Utility.URL;


import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ExecutionDetail} and makes a call to the
 * specified {@link ExecutionDetailFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExecutionDetailAdapter extends RecyclerView.Adapter<ExecutionDetailAdapter.ViewHolder> implements Filterable{

    private final List<ExecutionDetail> mValues;
    private  List<ExecutionDetail> mValuesFilter;
    private final ExecutionDetailFragment.OnListFragmentInteractionListener mListener;

    public ExecutionDetailAdapter(List<ExecutionDetail> items, ExecutionDetailFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mValuesFilter = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_executiondetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValuesFilter.get(position);
        holder.mIdView.setText(mValuesFilter.get(position).getQuantity());
        holder.mContentView.setText(mValuesFilter.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_execution = mValuesFilter.get(position).getId();
                ProductionOrderModelAcces productionOrderModelAcces = new ProductionOrderModelAcces(URL.BASE_URL);
                productionOrderModelAcces.deleteExecution(id_execution);

                removeAt(position);
            }
        });
    }

    private void removeAt(int position) {
        mValuesFilter.remove(position);
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValuesFilter.size());
        notifyItemRangeChanged(position, mValues.size());
    }

    @Override
    public int getItemCount() {
        return mValuesFilter.size();
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

                    ArrayList<ExecutionDetail> filteredList = new ArrayList<>();

                    for (ExecutionDetail androidVersion : mValues) {

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
                mValuesFilter = (ArrayList<ExecutionDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ImageButton imgViewRemoveIcon;
        public ExecutionDetail mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            imgViewRemoveIcon = (ImageButton) view.findViewById(R.id.icon_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
