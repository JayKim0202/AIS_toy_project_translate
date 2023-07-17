package com.example.ko_eng_translator;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.GnssAntennaInfo.Listener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class SourceLangDialog extends Dialog {

    public SourceLangDialog(@NonNull Context context, SourceLangDialogListener sourceLangeDialogListener) {
        super(context);
        this.sourceLangeDialogListener = sourceLangeDialogListener;
    }

    private SourceLangDialogListener sourceLangeDialogListener;

    public interface SourceLangDialogListener{
        void setSourceLang(String sourceLangCode, String sourceLangKo, String spareLangCode, String spaterLangKo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_lang_dialog);

        ListView scourceLangList = (ListView)findViewById(R.id.sourceLangList);
        ArrayList<String> items = new ArrayList<>();
        items.add("언어 감지");
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


        ArrayAdapter<String> adpater = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        scourceLangList.setAdapter(adpater);

        scourceLangList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sourceLang = (String) adapterView.getItemAtPosition(i);
                try{
                    sourceLangeDialogListener.setSourceLang((String)code.get(i), sourceLang, (String)code.get(i+1), (String)adapterView.getItemAtPosition(i+1));
                    System.out.println("try-catch i+1: " + code.get(i+1) + " " + adapterView.getItemAtPosition(i+1) );
                }catch (IndexOutOfBoundsException e) {
                    sourceLangeDialogListener.setSourceLang((String)code.get(i), sourceLang, (String)code.get(i-1), (String)adapterView.getItemAtPosition(i-1));
                    System.out.println("try-catch i-1: " + code.get(i-1) + " " + adapterView.getItemAtPosition(i-1) );
                }

                dismiss();
            }
        });

    }


}

