package iflytek.com.iniapp.inter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import iflytek.com.iniapp.adapter.MyAdapter;

/**
 * 描述：
 * 创建人：shuo
 * 创建时间：2016/10/13 16:47
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    MyAdapter adapter;

    public MyItemTouchHelperCallback(MyAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //复写此方法用来设置我需要处理哪些操作
        //我要处理上下拖动，就设置up down，如果是gridview类型的recylerview，up,down,left,right都可以处理
        final int moveMode = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //本例没有处理侧滑，如果需要处理侧滑，就把注释取消掉
        //final int swipeMode = ItemTouchHelper.START | ItemTouchHelper.END;
        Log.e("getMovementFlags","getMovementFlags");
        return makeMovementFlags(moveMode, 0);//不处理什么，就传0
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //当你拖动时就会触发此回调，然后调用在adapter中复写的onItemMove方法，去交换数据
        return adapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //如果你要处理侧滑就取消注释，逻辑和上面的onMove一样
        // adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //此方法在你选中一个item拖动时触发，所以你可以在这里给选中的item设置一个背景色，用于高亮提示你选中了这个背景色
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState!= ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //因为你设置了背景色，所以在你不选中的时候，要取消掉这个背景色。
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);

    }



}
