package com.test.medscanner.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.medscanner.R;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ClinicViewHolder> {
    private ArrayList<Clinic> mClinicsSet;
    private LayoutInflater inflater;

    private OnItemClickListener mListener;

    public SearchRecyclerAdapter(OnItemClickListener listener, LayoutInflater inflater) {
        this.inflater = inflater;
        mListener = listener;
        mClinicsSet = new ArrayList<>();
    }

    public void addAll(Collection<Clinic> mClinicsSet) {
        this.mClinicsSet.addAll(mClinicsSet);
        notifyDataSetChanged();
    }

    public void add(Clinic clinic) {
        this.mClinicsSet.add(clinic);
        notifyDataSetChanged();
    }

    public ArrayList<Clinic> getAll() {
        return mClinicsSet;
    }

    public void clear() {
        this.mClinicsSet.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.mClinicsSet.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClinicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search_layout, parent, false);
        return new ClinicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicViewHolder holder, int position) {
        holder.bind(mClinicsSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mClinicsSet.size();
    }

    class ClinicViewHolder extends RecyclerView.ViewHolder {
        private TextView resultName;
        private TextView resultsCount;
        private LinearLayout results;
        private ImageView favoriteIcon;
        private Context context;

        ClinicViewHolder(@NonNull View itemView) {
            super(itemView);
            resultName = itemView.findViewById(R.id.result_text);
            resultsCount = itemView.findViewById(R.id.results_count);
            results = itemView.findViewById(R.id.services);
            context = itemView.getContext();
        }

        void bind(Clinic clinic, int position) {
            View.OnClickListener listener = view -> mListener.onItemClick(clinic);
            resultName.setText(clinic.getName());
            resultName.setOnClickListener(listener);
            results.setOnClickListener(listener);
            results.removeAllViews();
            for (Service service : clinic.getServices()) {
                View view = inflater.inflate(R.layout.recycler_service_layout, results, false);
                TextView name = view.findViewById(R.id.result_text);
                name.setText(service.getName());
                TextView price = view.findViewById(R.id.results_count);
                price.setText(service.getPrice() + " руб.");

                ImageView starView = view.findViewById(R.id.favorite_icon);
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

                results.addView(view);
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Clinic clinic);

        void onStarClick(Service service);
    }
}
