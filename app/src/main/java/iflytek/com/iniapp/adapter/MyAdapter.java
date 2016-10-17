package iflytek.com.iniapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikhaldimann.inieditor.IniEditor;

import java.util.Collections;
import java.util.List;

import iflytek.com.iniapp.R;
import iflytek.com.iniapp.inter.OnMoveAndSwipeListener;

/**
 * 描述：
 * 创建人：shuo
 * 创建时间：2016/10/13 16:00
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> implements OnMoveAndSwipeListener {

    private List<String> list ;
    private Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_recyler,parent,false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv.setGravity(Gravity.CENTER);
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            //往下移动时,做数据交换
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list,i,i+1);
            }
        }else{
            //往上移动,做数据交换
            for (int i = fromPosition; i >toPosition ; i--) {
                Collections.swap(list,i,i-1);
            }
        }
        notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        //此例没有对侧滑做操作，如果需要就在此模仿onItemMove方法，删除对应位置的数据，并且刷新界面就可以了
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
