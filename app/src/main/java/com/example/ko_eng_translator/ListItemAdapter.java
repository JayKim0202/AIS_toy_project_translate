package com.example.ko_eng_translator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter{
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;

    LinearLayout language_layout;
    TextView language;

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public void addItem(ListItem item){
        items.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_custom, parent, false);
        }

        ArrayList<String> code = new ArrayList<>();
        code.add("detect");
        code.add("ko");
        code.add("ja");
        code.add("en");
        code.add("zh-CN");
        code.add("zh-TW");
        code.add("vi");
        code.add("id");
        code.add("th");
        code.add("de");
        code.add("ru");
        code.add("es");
        code.add("it");
        code.add("fr");

        language_layout = convertView.findViewById(R.id.language_layout);
        language = convertView.findViewById(R.id.language);

        language.setText(listItem.get());



        return convertView;
    }

}
