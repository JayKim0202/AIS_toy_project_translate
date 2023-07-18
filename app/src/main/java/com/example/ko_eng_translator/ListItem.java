package com.example.ko_eng_translator;

public class ListItem {
    private String language;

    ListItem(String language){
        this.language = language;
    }

    public void add (String language){
        this.language = language;
    };

    public String get(){
        return language;
    }

    public String getLanguage(){
        return this.language;
    }
}
