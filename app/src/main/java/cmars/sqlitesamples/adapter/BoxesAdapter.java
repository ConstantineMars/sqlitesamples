package cmars.sqlitesamples.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cmars.sqlitesamples.R;
import cmars.sqlitesamples.model.Box;

/**
 * Created by Constantine Mars on 1/19/17.
 */

public class BoxesAdapter extends RecyclerView.Adapter<BoxesAdapter.ViewHolder> {
    private List<Box> boxes;

    public BoxesAdapter(List<Box> boxes) {
        this.boxes = boxes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.box_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(boxes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return boxes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public void setAllBoxes(List<Box> boxes) {
        this.boxes.clear();
        this.boxes.addAll(boxes);
    }
}
