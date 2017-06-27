package in.cognitivo.erpapp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import in.cognitivo.erpapp.Entity.ProductionOrderDetail;
import in.cognitivo.erpapp.R;
import in.cognitivo.erpapp.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OrderDetailFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private final List<ProductionOrderDetail> mValues;
    private final OrderDetailFragment.OnListFragmentInteractionListener mListener;

    public OrderDetailAdapter(List<ProductionOrderDetail> items, OrderDetailFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getQuantity_real());
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mIdDetailView.setText(mValues.get(position).getId());
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
        return mValues.size();
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


}
