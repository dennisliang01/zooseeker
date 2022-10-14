package com.example.zooseeker_jj_zaaz_team_52;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * ZooDataAdapter Class: Single Responsibility - Tells Android how to display information regarding
 * selectable exhibits in drop down search bar in SearchActivity
 */
public class ZooDataAdapter extends RecyclerView.Adapter<ZooDataAdapter.ViewHolder> {

    private static List<ZooData.VertexInfo> exhibits = Collections.emptyList();
    private BiConsumer<View, ZooData.VertexInfo> selectedConsumer;
    public PlanListItemDao dataBase;

    public void setExhibits(List<ZooData.VertexInfo> exhibits) {
        ZooDataAdapter.exhibits.clear();
        ZooDataAdapter.exhibits = exhibits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return exhibits.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setExhibit(exhibits.get(position));
        holder.getExhibitView().setText(holder.getExhibit().kind.toString().toLowerCase());
        holder.getAnimalView().setText(holder.getExhibit().name);

        for (PlanListItem selected : dataBase.getAll()) {
            if (selected.exhibit_name.equals(holder.getExhibit().name)) {
                holder.getExhibit().isSelected = true;
                break;
            }
        }

        if (exhibits.get(position).isSelected) {
            holder.animalView.setBackgroundColor(Color.GRAY);
        } else {
            holder.animalView.setBackgroundColor(Color.WHITE);
        }

    }

    public static void unselectFromPlan(PlanListItem item) {
        for (ZooData.VertexInfo exhibit : exhibits) {
            if (exhibit.name.equals(item.exhibit_name)) {
                exhibit.isSelected = false;
            }
        }
    }

    public void setSelectedConsumer(BiConsumer<View, ZooData.VertexInfo> selectedConsumer) {
        this.selectedConsumer = selectedConsumer;
    }

    public void setDataBase(PlanListItemDao db) {
        dataBase = db;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ZooData.VertexInfo exhibit;
        private TextView exhibitView;
        private TextView animalView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exhibitView = itemView.findViewById(R.id.exhibit_type);
            animalView = itemView.findViewById(R.id.animal_name);
            this.animalView.setOnClickListener(view -> {
                if (selectedConsumer == null) return;
                selectedConsumer.accept(animalView, exhibit);
                notifyDataSetChanged();
            });
        }

        public ZooData.VertexInfo getExhibit() {
            return exhibit;
        }

        public TextView getAnimalView() {
            return animalView;
        }

        public TextView getExhibitView() {
            return exhibitView;
        }

        public void setExhibitView(TextView exhibitView) {
            this.exhibitView = exhibitView;
        }

        public void setAnimalView(TextView animalView) {
            this.animalView = animalView;
        }

        public void setExhibit(ZooData.VertexInfo exhibit) {
            this.exhibit = exhibit;
        }
    }
}
