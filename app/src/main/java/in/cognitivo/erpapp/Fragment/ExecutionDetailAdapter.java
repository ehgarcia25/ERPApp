package in.cognitivo.erpapp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import in.cognitivo.erpapp.Entity.ExecutionDetail;
import in.cognitivo.erpapp.Model.ProductionOrderModelAcces;
import in.cognitivo.erpapp.R;
import in.cognitivo.erpapp.Utility.URL;
import in.cognitivo.erpapp.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ExecutionDetailFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExecutionDetailAdapter extends RecyclerView.Adapter<ExecutionDetailAdapter.ViewHolder> {

    private final List<ExecutionDetail> mValues;
    private final ExecutionDetailFragment.OnListFragmentInteractionListener mListener;

    public ExecutionDetailAdapter(List<ExecutionDetail> items, ExecutionDetailFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getQuantity());
        holder.mContentView.setText(mValues.get(position).getName());

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
                String id_execution = mValues.get(position).getId();
                ProductionOrderModelAcces productionOrderModelAcces = new ProductionOrderModelAcces(URL.BASE_URL);
                productionOrderModelAcces.deleteExecution(id_execution);

                removeAt(position);
            }
        });
    }

    public void removeAt(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
