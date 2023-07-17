package com.example.ko_eng_translator;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TargetLangDialog extends Dialog {

    public TargetLangDialog(@NonNull Context context, TargetLangDialogListener targetLangeDialogListener) {
        super(context);
        this.targetLangeDialogListener = targetLangeDialogListener;
    }

    private TargetLangDialogListener targetLangeDialogListener;

    public interface TargetLangDialogListener{
        void setTargetLang(String targetLangCode, String targetLangKo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_lang_dialog);

        ListView targetLangList = (ListView)findViewById(R.id.targetLangList);
        ArrayList<String> items = new ArrayList<>();
        items.add("한국어");
        items.add("일본어");
        items.add("영어");
        items.add("중국어 간체");
        items.add("중국어 번체");
        items.add("베트남어");
        items.add("인도네시아어");
        items.add("태국어");
        items.add("독일어");
        items.add("러시아어");
        items.add("스페인어");
        items.add("이탈리아어");
        items.add("프랑스어");

        ArrayList<String> code = new ArrayList<>();
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


        ArrayAdapter<String> adpater = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        targetLangList.setAdapter(adpater);

        targetLangList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String targetLang = (String) adapterView.getItemAtPosition(i);

                targetLangeDialogListener.setTargetLang((String)code.get(i), targetLang);

                dismiss();
            }
        });

    }


}

