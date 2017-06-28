package in.cognitivo.erpapp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.cognitivo.erpapp.Entity.ProductionOrderDetail;
import in.cognitivo.erpapp.R;


/**
 * {@link RecyclerView.Adapter} that can display a {@link ProductionOrderDetail} and makes a call to the
 * specified {@link OrderDetailFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> implements Filterable{

    private final List<ProductionOrderDetail> mValues;
    private  List<ProductionOrderDetail> mValuesFilter;
    private final OrderDetailFragment.OnListFragmentInteractionListener mListener;

    public OrderDetailAdapter(List<ProductionOrderDetail> items, OrderDetailFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mValuesFilter = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orderdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValuesFilter.get(position);
        holder.mIdView.setText(mValuesFilter.get(position).getQuantity_real());
        holder.mContentView.setText(mValuesFilter.get(position).getName());
        holder.mIdDetailView.setText(mValuesFilter.get(position).getId());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   mListener.onListFragmentInteraction(holder.mItem);
                    //notifyItemChanged(position);
                    //notifyDataSetChanged();
                }
                //notifyItemChanged(position);

            }
        });
    }



    @Override
    public int getItemCount() {
        return mValuesFilter.size();
    }

    public List<ProductionOrderDetail> getmValues(){
        return mValues;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mIdDetailView;
       // public final EditText editText ;

        public ProductionOrderDetail mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mIdDetailView = (TextView) view.findViewById(R.id.id_detail);

           /* editText = (EditText) view.findViewById(R.id.editText);
            MyTextWatcher textWatcher = new MyTextWatcher(editText);
            editText.addTextChangedListener(textWatcher);*/
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }



    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int position = (int) editText.getTag();
            // Do whatever you want with position
        }

        @Override
        public void afterTextChanged(Editable s) {

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

                    ArrayList<ProductionOrderDetail> filteredList = new ArrayList<>();

                    for (ProductionOrderDetail androidVersion : mValues) {

                        if (androidVersion.getName() != null && androidVersion.getName().toLowerCase().contains(charString)) {

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
                mValuesFilter = (ArrayList<ProductionOrderDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
