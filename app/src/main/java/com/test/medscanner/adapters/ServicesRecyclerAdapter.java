package com.test.medscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.medscanner.R;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.Service;

import java.util.ArrayList;
import java.util.Collection;

public class ServicesRecyclerAdapter extends RecyclerView.Adapter<ServicesRecyclerAdapter.ServiceViewHolder> {
    private ArrayList<Service> mServicesSet;

    private OnItemClickListener mListener;
    public ServicesRecyclerAdapter(OnItemClickListener listener) {
        mListener = listener;
        mServicesSet = new ArrayList<>();
    }

    public void addAll(Collection<Service> mServicesSet) {
        this.mServicesSet.addAll(mServicesSet);
        notifyDataSetChanged();
    }

    public void add(Service service) {
        this.mServicesSet.add(service);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mServicesSet.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.mServicesSet.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_service_layout, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        holder.bind(mServicesSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mServicesSet.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private TextView resultName;
        private TextView resultsPrice;
        private ImageView starView;
        private Context context;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            resultName = itemView.findViewById(R.id.result_text);
            resultsPrice = itemView.findViewById(R.id.results_count);
            starView = itemView.findViewById(R.id.favorite_icon);
            context = itemView.getContext();
        }

        void bind(Service service, int position) {
            View.OnClickListener listener = view -> mListener.onItemClick(service);

            resultName.setText(service.getName());
            resultsPrice.setText(service.getPrice() + " руб.");

            if (service.isStarred()) {
                starView.setImageDrawable(context.getDrawable(R.drawable.round_star_black_36));
            } else {
                starView.setImageDrawable(context.getDrawable(R.drawable.round_star_outline_black_36));
            }
            View.OnClickListener starListener = l -> {
                if (service.isStarred()) {
                    service.setStarred(false);
                    starView.setImageDrawable(context.getDrawable(R.drawable.round_star_outline_black_36));
                } else {
                    service.setStarred(true);
                    starView.setImageDrawable(context.getDrawable(R.drawable.round_star_black_36));
                }
                mListener.onStarClick(service);
            };

            starView.setOnClickListener(starListener);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Service service);
        void onStarClick(Service service);
    }
}
