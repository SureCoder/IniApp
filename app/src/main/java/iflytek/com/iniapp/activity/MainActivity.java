package iflytek.com.iniapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nikhaldimann.inieditor.IniEditor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import iflytek.com.iniapp.R;
import iflytek.com.iniapp.StringUtils;
import iflytek.com.iniapp.adapter.ViewPagerAdapter;
import iflytek.com.iniapp.view.FlowLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    Map<String, String[]> map = new HashMap<>();
    private List<String> tabList = new ArrayList<>();
    private List<String> tabList2 = new ArrayList<>();
    private ViewPagerAdapter adapter;
    private List<View> viewList;
    private List<String> list1 = new ArrayList<>();//第一组字符
    private List<String> list2 = new ArrayList<>();//第二组字符
    int nowState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAllTitle();
        findView();
        initToolBar();
        getIniData("emoticon.ini", list1);
        initTab(tabList, list1, "emoticon.ini");
    }

    private void initAllTitle() {
        File sdCardDir = Environment.getExternalStorageDirectory();
        String file = sdCardDir + File.separator + "emoticon.ini";
        IniEditor editor = new IniEditor();
        try {
            editor.load(file);
            String s = editor.get("Emoticon", "CONTENT");
            String[] strings = s.split(",");
            for (int i = 0; i < strings.length; i++) {
                list1.add(strings[i]);
                tabList.add(editor.get(strings[i], "NAME"));
            }
            editor.load(sdCardDir + File.separator + "symbol.ini");
            String s1 = editor.get("Symbol", "CONTENT");
            String[] stringss = s1.split(",");
            for (int i = 0; i < stringss.length; i++) {
                list2.add(stringss[i]);
                tabList2.add(editor.get(stringss[i], "NAME"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.menu_add:
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.customview, null);
                        final TextInputEditText editText = (TextInputEditText) view.findViewById(R.id.edittext);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("增加").setView(view).setNegativeButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String str = editText.getText().toString();
                                        if (nowState == 1) {
                                            addData(list1, "emoticon.ini", str);
                                            getIniData("emoticon.ini", list1);
                                            initTab(tabList, list1, "emoticon.ini");
                                        } else {
                                            addData(list2, "symbol.ini", str);
                                            getIniData("symbol.ini", list2);
                                            initTab(tabList, list2, "symbol.ini");
                                        }
                                    }
                                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                        break;
                    case R.id.menu_switch:
                        if (nowState == 1) {
                            nowState = 2;
                            getIniData("symbol.ini", list2);
                            initTab(tabList2, list2, "symbol.ini");
                        } else {
                            nowState = 1;
                            getIniData("emoticon.ini", list1);
                            initTab(tabList, list1, "emoticon.ini");
                        }

                        break;
                    case R.id.menu_nextActivity:
                        Intent intent = new Intent(MainActivity.this, ItemTouchActivity.class);
                        if (nowState == 1) {
                            getIniData("emoticon.ini", list1);
                            intent.putExtra("data", map.get(list1.get(viewPager.getCurrentItem())));
                        }
                        if (nowState == 2) {
                            getIniData("symbol.ini", list2);
                            intent.putExtra("data", map.get(list2.get(viewPager.getCurrentItem())));
                        }
                        startActivityForResult(intent, 1);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        ArrayList<String> list = bundle.getStringArrayList("result");
        IniEditor editor = new IniEditor();
        String newData = "";
        if (requestCode == 1 && resultCode == 100) {
            if (nowState == 1) {
                try {
                    editor.load(Environment.getExternalStorageDirectory() + File.separator + "emoticon.ini");
                    for (int i = 0; i < list.size(); i++) {
                        if (i != list.size()-1){
                            newData += list.get(i)+",";
                        }else{
                            newData += list.get(i);
                        }
                    }
                    editor.set(list1.get(viewPager.getCurrentItem()), "CONTENT", newData);
                    editor.save(Environment.getExternalStorageDirectory() + File.separator + "emoticon.ini");
                    getIniData("emoticon.ini", list1);
                    initTab(tabList, list1, "emoticon.ini");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (nowState == 2) {
                try {
                    editor.load(Environment.getExternalStorageDirectory() + File.separator + "symbol.ini");
                    for (int i = 0; i < list.size(); i++) {
                        if (i != list.size()-1){
                            newData += list.get(i)+",";
                        }else{
                            newData += list.get(i);
                        }
                    }
                    editor.set(list2.get(viewPager.getCurrentItem()), "CONTENT", newData);
                    editor.save(Environment.getExternalStorageDirectory() + File.separator + "symbol.ini");
                    getIniData("symbol.ini", list2);
                    initTab(tabList2, list2, "symbol.ini");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void findView() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    private void initTab(final List<String> tabList, final List<String> list, final String fileName) {

        viewList = new ArrayList<>();
        //加入切换的流式布局
        int halfWidth;
        int fourWidth;
        List<Integer> widthList = new ArrayList<>();
        List<List<Integer>> widthlists = new ArrayList<>();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        halfWidth = metrics.widthPixels / 2;
        fourWidth = metrics.widthPixels / 4;
        int matchwidth = metrics.widthPixels;

        TextView textView = new TextView(this);


        textView.setTextSize(18);
        int width = 0;
        for (int i = 0; i < tabList.size(); i++) {
            String[] st = map.get(list.get(i));
            for (int j = 0; j < st.length; j++) {
                width = (int) textView.getPaint().measureText(st[j]);
                if (width >= halfWidth) {
                    width = matchwidth;
                } else if (width >= fourWidth) {
                    width = halfWidth;
                } else {
                    width = fourWidth;
                }
                widthList.add(width);

            }
            widthlists.add(widthList);
            widthList = new ArrayList<>();
        }
        //开始排序

        for (int i = 0; i < widthlists.size(); i++) {
            widthList = widthlists.get(i);
            int count = widthList.size();//还有多少个控件没有被正确摆放
            int index = 0;//记录当前换行后开始算起的下标
            for (int j = 0; j < widthList.size(); ) {
                if (count >= 4) { //是否有大于等于4个控件等待摆放
                    if (widthList.get(j) >= halfWidth || widthList.get(j + 1) >= halfWidth ||
                            widthList.get(j + 2) >= halfWidth || widthList.get(j + 3) >= halfWidth) {
                        //表示4个控件摆不了一行的情况
                        //前2个
                        if (widthList.get(j) > halfWidth || widthList.get(j + 1) > halfWidth) {
                            widthList.set(j, matchwidth);
                            widthList.set(j + 1, matchwidth);

                        } else {
                            widthList.set(j, halfWidth);
                            widthList.set(j + 1, halfWidth);

                        }

                        j += 2;
                        count -= 2;
                    } else {
                        j += 4;
                        count -= 4;
                    }

                }
                if (count >= 2 && count < 4) {
                    if (widthList.get(j) >= halfWidth || widthList.get(j + 1) >= halfWidth
                            ) {

                        //前2个
                        if (widthList.get(j) > halfWidth || widthList.get(j + 1) > halfWidth) {
                            widthList.set(j, matchwidth);
                            widthList.set(j + 1, matchwidth);

                        } else {
                            widthList.set(j, halfWidth);
                            widthList.set(j + 1, halfWidth);

                        }


                    } else {
                        widthList.set(j, halfWidth);
                        widthList.set(j + 1, halfWidth);

                    }
                    j += 2;
                    count -= 2;
                }
                if (count == 1) {
                    widthList.set(j, matchwidth);
                    j += 1;
                    count -= 1;
                }
            }


        }


        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
            ScrollView scrollView = new ScrollView(this);
            FlowLayout flowLayout = new FlowLayout(this);
            String[] strs = map.get(list.get(i));
            for (int x = 0; x < strs.length; x++) {
                final TextView tv = new TextView(this);
                tv.setTextSize(15);
                tv.setTag(x);
                tv.setTextColor(Color.parseColor("#0F0F0F"));
                TextPaint paint = tv.getPaint();
                int textWidth = (int) paint.measureText(strs[x]);
                ViewGroup.MarginLayoutParams params;
                params = new ViewGroup.MarginLayoutParams
                        (widthlists.get(i).get(x), 70);
//                params.setMargins(2, 5, 2, 5);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.textview_shape);
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.customview, null);
                        textInputEditText = (TextInputEditText) view.findViewById(R.id.edittext);
                        textInputLayout = (TextInputLayout) view.findViewById(R.id.inputlayout);
                        textInputLayout.setHint("原文：" + tv.getText());
                        textInputEditText.setText(tv.getText());
                        builder.setTitle("修改").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Editable editable = textInputEditText.getText();
                                String str = editable.toString();
                                tv.setText(str);
                                IniEditor editor = new IniEditor(true);
                                try {
                                    editor.load(Environment.getExternalStorageDirectory() + File.separator + fileName);
                                    String newStr = "";
                                    String[] oldStr = map.get(list.get(viewPager.getCurrentItem()));
                                    for (int i = 0; i < oldStr.length; i++) {
                                        if ((Integer) tv.getTag() == i) {

                                            newStr = newStr + str;

                                        } else {

                                            newStr = newStr + oldStr[i];

                                        }
                                        if (i != oldStr.length - 1) {
                                            newStr = newStr + ",";
                                        }
                                    }
                                    editor.set(list.get(viewPager.getCurrentItem()), "CONTENT", newStr);
                                    editor.save(Environment.getExternalStorageDirectory() + File.separator + fileName);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNeutralButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                IniEditor editor = new IniEditor();
                                try {
                                    editor.load(Environment.getExternalStorageDirectory() + File.separator + fileName);
                                    String newStr = "";
                                    String[] oldStr = map.get(list.get(viewPager.getCurrentItem()));
                                    for (int i = 0; i < oldStr.length; i++) {
                                        if ((Integer) tv.getTag() == i) {


                                        } else {

                                            newStr = newStr + oldStr[i];

                                        }
                                        if (i != oldStr.length - 1 && (Integer) tv.getTag() != i) {
                                            newStr = newStr + ",";
                                        }
                                    }
                                    editor.set(list.get(viewPager.getCurrentItem()), "CONTENT", newStr);
                                    editor.save(Environment.getExternalStorageDirectory() + File.separator + fileName);

                                    getIniData(fileName, list);
                                    initTab(tabList, list, fileName);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).create().show();
                        return true;
                    }
                });
                tv.setLayoutParams(params);
                tv.setText(strs[x]);
                flowLayout.addView(tv);
            }
            scrollView.addView(flowLayout);
            viewList.add(scrollView);

        }
        adapter = new ViewPagerAdapter(viewList, tabList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void getIniData(String fileName, List<String> list) {
        File sdCardDir = Environment.getExternalStorageDirectory();
        String file = sdCardDir + File.separator + fileName;
        try {

            IniEditor editor = new IniEditor();
            editor.load(file);
            for (int i = 0; i < list.size(); i++) {
                String content = editor.get(list.get(i), "CONTENT");
                String[] contents = StringUtils.splitString(content, ',');
                map.put(list.get(i), contents);

            }
            editor.load(sdCardDir + File.separator + "symbol.ini");
            for (int i = 0; i < list2.size(); i++) {
                String content = editor.get(list2.get(i), "CONTENT");
                String[] contents = StringUtils.splitString(content, ',');
                map.put(list2.get(i), contents);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addData(List<String> list, String fileName, String newData) {
        IniEditor editor = new IniEditor(true);
        try {
            editor.load(Environment.getExternalStorageDirectory() + File.separator + fileName);

            String newStr = newData;
            String[] oldStr = map.get(list.get(viewPager.getCurrentItem()));
            for (int i = 0; i < oldStr.length; i++) {
                newStr = newStr + "," + oldStr[i];

            }
            editor.set(list.get(viewPager.getCurrentItem()), "CONTENT", newStr);
            editor.save(Environment.getExternalStorageDirectory() + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
