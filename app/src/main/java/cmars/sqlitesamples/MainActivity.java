package cmars.sqlitesamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cmars.sqlitesamples.adapter.BoxesAdapter;
import cmars.sqlitesamples.adapter.ItemsAdapter;
import cmars.sqlitesamples.db.StoreHelper;
import cmars.sqlitesamples.model.Box;
import cmars.sqlitesamples.model.Item;
import cmars.sqlitesamples.view.BoxItemClickListener;

public class MainActivity extends AppCompatActivity {

    StoreHelper storeHelper;

    RecyclerView topList;
    RecyclerView mainList;

    BoxesAdapter boxesAdapter;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topList= (RecyclerView) findViewById(R.id.topList);
        mainList= (RecyclerView) findViewById(R.id.mainList);

        initDb();
        setupLists();
        setupUpdateButton();
    }

    private void setupUpdateButton() {
        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initDb() {
        storeHelper = new StoreHelper(this);

        storeHelper.open();
        storeHelper.initDefaultValues();
    }

    private void setupLists() {
        topList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mainList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final List<Box> boxes = storeHelper.queryBoxes();

        boxesAdapter = new BoxesAdapter(boxes);
        topList.setAdapter(boxesAdapter);

        final List<Item> items = storeHelper.queryItems(1);

        itemsAdapter = new ItemsAdapter(items);
        mainList.setAdapter(itemsAdapter);

        topList.addOnItemTouchListener(new BoxItemClickListener(this, new BoxItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                long boxId = boxes.get(position).getId();
                List<Item> itemsForSelectedBox = storeHelper.queryItems(boxId);
                itemsAdapter.setAllItems(itemsForSelectedBox);
                itemsAdapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeHelper.open();
    }

    @Override
    protected void onPause() {
        storeHelper.close();
        super.onPause();
    }
}
