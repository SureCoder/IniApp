package iflytek.com.iniapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 描述：
 * 创建人：shuo
 * 创建时间：2016/9/18 14:55
 */
public class ViewPagerAdapter extends PagerAdapter {

    List<View> list;
    List<String> tabString ;

    public ViewPagerAdapter(List<View> list, List<String> tabString) {
        this.list = list;
        this.tabString = tabString;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabString.get(position);
    }
}
