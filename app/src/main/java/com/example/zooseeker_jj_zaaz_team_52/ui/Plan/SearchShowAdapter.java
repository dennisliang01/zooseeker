package com.example.zooseeker_jj_zaaz_team_52.ui.Plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItem;
import com.example.zooseeker_jj_zaaz_team_52.R;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * SearchShowAdapter Class: Single Responsibility - Tells Android how to display selected exhibits
 * in list format on UI of SearchActivity
 */
public class SearchShowAdapter extends RecyclerView.Adapter<SearchShowAdapter.ViewHolder> {
    private List<PlanListItem> planItems = Collections.emptyList();
    private Consumer<PlanListItem> onDeleteClicked;
    TextView selectCountText;

    public SearchShowAdapter(TextView selectCountText) {
        this.selectCountText = selectCountText;
    }

    public void setPlanItems(List<PlanListItem> newPlanItems) {
        this.planItems.clear();
        this.planItems = newPlanItems;
        String planText = "[ " + getItemCount() + " ]";
        selectCountText.setText(planText);
        notifyDataSetChanged();
    }

    public void setOnDeletedClickedHandler(Consumer<PlanListItem> onDeleteClicked) {
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_show_item_new, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView exhibit_name;
        private final TextView exhibit_loc;
        private final ImageView animal_search_image;
        private final TextView cancel_btn;
        private PlanListItem planItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exhibit_name = itemView.findViewById(R.id.display_exhibit_name);
            this.exhibit_loc = itemView.findViewById(R.id.display_exhibit_loc);
            this.animal_search_image = itemView.findViewById(R.id.animal_search_image);
            this.cancel_btn = itemView.findViewById(R.id.delete_btn);

            this.cancel_btn.setOnClickListener(view -> {
                if (onDeleteClicked == null) return;
                onDeleteClicked.accept(planItem);
            });
        }


        public PlanListItem getPlanItem() {
            return planItem;
        }

        public void setPlanItem(PlanListItem planItem) {
            this.planItem = planItem;
            this.exhibit_name.setText(planItem.exhibit_name);
            //this.exhibit_loc.setText(planItem.loc);
            setAnimalImage(planItem.exhibit_id);
        }

        public void setAnimalImage(String exhibitName){
            String resourceName = exhibitName.toLowerCase().replace(" ", "_").replace("-", "_");
            int imageResId = itemView.getContext().getResources().getIdentifier(resourceName, "drawable", itemView.getContext().getPackageName());

            // Use Glide to load the image or set a default image if the resource ID is not found
            if (imageResId != 0) {
                Glide.with(itemView.getContext())
                        .load(imageResId)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(animal_search_image);
            } else {
                animal_search_image.setImageResource(R.drawable.not_avaliable); // Fallback if no resource found
            }

        }
    }
}
