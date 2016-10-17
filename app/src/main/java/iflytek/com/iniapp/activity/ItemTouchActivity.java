package iflytek.com.iniapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import iflytek.com.iniapp.R;
import iflytek.com.iniapp.adapter.MyAdapter;
import iflytek.com.iniapp.inter.MyItemTouchHelperCallback;
import iflytek.com.iniapp.inter.OnRecylerViewItemClickListener;

public class ItemTouchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch);
        list.addAll(Arrays.asList(getIntent().getStringArrayExtra("data")));
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new MyAdapter(list, this);
        recyclerView.setAdapter(adapter);
        MyItemTouchHelperCallback callback = new MyItemTouchHelperCallback(adapter);
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new OnRecylerViewItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(ItemTouchActivity.this,vh.getAdapterPosition()+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition()!=list.size()-1) {
                    helper.startDrag(vh);
                }
                Toast.makeText(ItemTouchActivity.this,vh.getAdapterPosition()+"buke",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        Log.e("list",list.toString());
        intent.putStringArrayListExtra("result", list);
        setResult(100,intent);
        finish();
    }
}
