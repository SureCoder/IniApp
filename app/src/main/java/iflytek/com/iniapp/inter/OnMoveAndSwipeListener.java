package iflytek.com.iniapp.inter;

/**
 * 描述：
 * 创建人：shuo
 * 创建时间：2016/10/13 16:58
 */
public interface OnMoveAndSwipeListener {

    public boolean onItemMove(int fromPosition, int toPosition);

    public void onItemDismiss(int position);
}
