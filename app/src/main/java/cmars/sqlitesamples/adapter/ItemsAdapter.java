package cmars.sqlitesamples.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cmars.sqlitesamples.R;
import cmars.sqlitesamples.model.Box;
import cmars.sqlitesamples.model.Item;

/**
 * Created by Constantine Mars on 1/19/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private List<Item> items;

    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;

        ViewHolder(View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.name);
            price= (TextView) itemView.findViewById(R.id.price);
        }
    }

    public void setAllItems(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
    }
}
