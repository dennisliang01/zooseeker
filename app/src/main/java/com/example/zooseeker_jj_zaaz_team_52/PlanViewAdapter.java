package com.example.zooseeker_jj_zaaz_team_52;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * PlanViewAdapter Class: Single Responsibility - Tells Android how to display our plan list items
 */
public class PlanViewAdapter extends RecyclerView.Adapter<PlanViewAdapter.ViewHolder> {

    private List<PlanListItem> planItems = Collections.emptyList();
    TextView planCountText;

    public PlanViewAdapter(TextView planCountText) {
        this.planCountText = planCountText;
    }

    public void setPlanItems(List<PlanListItem> newPlanItems) {
        this.planItems.clear();
        this.planItems = newPlanItems;
        String planText = "(" + getItemCount() + ") Plan";
        this.planCountText.setText(planText);
        notifyDataSetChanged();
    }

    public void setPlanItems(ZooNavigator navigator) {
        this.planItems.clear();
        this.planItems = navigator.findOptimizedPath();
        String planText = "(" + getItemCount() + ") Plan";
        this.planCountText.setText(planText);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.plan_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPlanItem(planItems.get(position));
    }

    @Override
    public int getItemCount() {
        return planItems.size();
    }

    @Override
    public long getItemId(int position) {
        return planItems.get(position).id;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView exhibit_name;
        private final TextView location;
        private final TextView distance;
        private PlanListItem planItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exhibit_name = itemView.findViewById(R.id.plan_exhibit_name);
            this.location = itemView.findViewById(R.id.plan_location);
            this.distance = itemView.findViewById(R.id.plan_distance);
        }

        public PlanListItem getPlanItem() {
            return planItem;
        }

        public void setPlanItem(PlanListItem planItem) {
            this.planItem = planItem;
            this.exhibit_name.setText(planItem.exhibit_name);
            this.location.setText(planItem.loc);
            String distString = planItem.dist + "ft";
            this.distance.setText(distString);
        }
    }
}
