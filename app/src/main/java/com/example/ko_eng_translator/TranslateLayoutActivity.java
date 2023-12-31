package com.example.ko_eng_translator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.concurrent.ExecutionException;

public class TranslateLayoutActivity extends AppCompatActivity{

    public String sourceLanguageCode = "detect";
    public String targetLanguageCode = "en";
    public String sourceText;
    public String targetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.horizon_enter, R.anim.none);

        setContentView(R.layout.activity_translate_layout);

        LinearLayout tutorialLayout = findViewById(R.id.tutorialLayout);

        tutorialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tutorialLayout.setVisibility(View.GONE);
            }
        });



        AppCompatButton sourceLangBtn = findViewById(R.id.sourceLangBtn);
        AppCompatButton targetLangBtn = findViewById(R.id.targetLangBtn);
        // 원본 언어 텍스트 설정
        EditText sourceLangTxt = findViewById(R.id.sourceLangTxt);

        // 타겟 언어 텍스트 설정
        TextView targetLangTxt = findViewById(R.id.targetLangTxt);

        // 원본 언어 설정
        sourceLangBtn.setText("언어 감지");

        sourceLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SourceLangDialog sourceLangDialog = new SourceLangDialog(TranslateLayoutActivity.this, new SourceLangDialog.SourceLangDialogListener() {
                    @Override
                    public void setSourceLang(String sourceLangCode, String sourceLangKo, String spareLangCode, String spareLangKo) {

                        if (sourceLangCode.equals(targetLanguageCode)){
                            if (sourceLanguageCode.equals("detect")){
                                sourceLanguageCode = spareLangCode;
                                sourceLangBtn.setText(spareLangKo);
                            }

                            String tmp = sourceLanguageCode;
                            sourceLanguageCode = targetLanguageCode;
                            targetLanguageCode = tmp;

                            tmp = sourceLangBtn.getText().toString();
                            sourceLangBtn.setText(targetLangBtn.getText().toString());
                            targetLangBtn.setText(tmp);

                            tmp = sourceLangTxt.getText().toString();
                            sourceLangTxt.setText(targetLangTxt.getText().toString());
                            targetLangTxt.setText(tmp);
                        }else{
                            sourceLangBtn.setText(sourceLangKo);
                            sourceLanguageCode = sourceLangCode;
                        }
                        System.out.println("source lang Code: " + sourceLanguageCode);
                        System.out.println("target lang Code: " + targetLanguageCode);
                    }
                });
                sourceLangDialog.show();

                sourceLangDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sourceLangDialog.setCanceledOnTouchOutside(true);
            }
        });


        // 타겟 언어 설정
        targetLangBtn.setText("영어");

        targetLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TargetLangDialog targetLangDialog = new TargetLangDialog(TranslateLayoutActivity.this, new TargetLangDialog.TargetLangDialogListener() {
                    @Override
                    public void setTargetLang(String targetLangCode, String targetLangKo) {
                        if (targetLangCode.equals(sourceLanguageCode)){

                            String tmp = sourceLanguageCode;
                            sourceLanguageCode = targetLanguageCode;
                            targetLanguageCode = tmp;

                            tmp = sourceLangBtn.getText().toString();
                            sourceLangBtn.setText(targetLangBtn.getText().toString());
                            targetLangBtn.setText(tmp);

                            tmp = sourceLangTxt.getText().toString();
                            sourceLangTxt.setText(targetLangTxt.getText().toString());
                            targetLangTxt.setText(tmp);
                        }else{
                            targetLangBtn.setText(targetLangKo);
                            targetLanguageCode = targetLangCode;
                        }
                        System.out.println("source lang Code: " + sourceLanguageCode);
                        System.out.println("target lang Code: " + targetLanguageCode);
                    }
                });

                targetLangDialog.show();

                targetLangDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                targetLangDialog.setCanceledOnTouchOutside(true);
            }
        });



        sourceLangTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetLangTxt.setText(null);
            }
        });


        // 언어 감지 및 번역
        AppCompatButton translateBtn = findViewById(R.id.translateBtn);

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // text null 예외 처리
                if (!sourceLangTxt.getText().toString().isEmpty()){
                    sourceText = sourceLangTxt.getText().toString();
                    System.out.println("텍스트 뷰: " + sourceText);
                }else{
                    Toast.makeText(TranslateLayoutActivity.this, "텍스트를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 언어 감지인 경우
                if(sourceLanguageCode.equals("detect")){
                    DetectLan detectLan = new DetectLan(sourceText);
                    try {
                        sourceLanguageCode = detectLan.execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 쓰레드 끝 난 후에 값 가져오기
                    System.out.println("감지 언어: " + sourceLanguageCode);

                    switch(sourceLanguageCode){
                        case "ko":
                            sourceLangBtn.setText("한국어");
                            break;
                        case "ja":
                            sourceLangBtn.setText("일본어");
                            break;
                        case "en":
                            sourceLangBtn.setText("영어");
                            break;
                        case "zh-CN":
                            sourceLangBtn.setText("중국어 간체");
                            break;
                        case "zh-TW":
                            sourceLangBtn.setText("중국어 번체");
                            break;
                        case "vi":
                            sourceLangBtn.setText("베트남어");
                            break;
                        case "id":
                            sourceLangBtn.setText("인도네시아어");
                            break;
                        case "th":
                            sourceLangBtn.setText("태국어");
                            break;
                        case "de":
                            sourceLangBtn.setText("독일어");
                            break;
                        case "ru":
                            sourceLangBtn.setText("러시아어");
                            break;
                        case "es":
                            sourceLangBtn.setText("스페인어");
                            break;
                        case "it":
                            sourceLangBtn.setText("이탈리아어");
                            break;
                        case "fr":
                            sourceLangBtn.setText("프랑스어");
                            break;
                        default:
                            sourceLangBtn.setText("언어 감지");
                            sourceLanguageCode = "detect";
                            Toast.makeText(TranslateLayoutActivity.this, "언어를 감지할 수 없습니다. 새로운 문장을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                            return;
                    }
                }

                // 언어 감지 후 소스와 타겟이 같은 경우
                if (sourceLanguageCode.equals(targetLanguageCode)){
                    if (targetLanguageCode.equals("ko")){
                        targetLanguageCode = "en";
                        targetLangBtn.setText("영어");
                    }else{
                        targetLanguageCode="ko";
                        targetLangBtn.setText("한국어");
                    }
                }


                // 언어 번역
                TranslateLan translateLan = new TranslateLan(sourceLanguageCode, targetLanguageCode, sourceText);
                try {
                    targetText = translateLan.execute().get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 에러 예외 처리
                switch (targetText){
                    case "N2MT01":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "원본 언어가 제대로 설정되어 있는지 확인해 주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT02":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "아쉽지만 아직 지원하지 않는 원본 언어입니다. 다른 언어를 선택해 주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT03":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "목적 언어가 제대로 설정되어 있는지 확인해 주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT04":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "아쉽지만 아직 지원하지 않는 목적 언어입니다. 다른 언어를 선택해 주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT05":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "원본 언어와 목적 언어가 동일합니다! 다른 언어를 선택해 주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT06":
                        Toast.makeText(TranslateLayoutActivity.this, (String)sourceLangBtn.getText() + " -> " + (String)targetLangBtn.getText() + " 번역을 지원하지 않습니다. 다른 언어를 선택해 주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT07":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "번역할 텍스트를 입력해주세요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT08":
                        // 이 예외처리는 동작하지 않습니다.
                        Toast.makeText(TranslateLayoutActivity.this, "번역할 텍스트의 글자 수가 너무 많아요!", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    case "N2MT99":
                        Toast.makeText(TranslateLayoutActivity.this, "서버에서 값을 불러오지 못했습니다! 다시 실행해주세요.", Toast.LENGTH_SHORT).show();
                        targetLangTxt.setText("");
                        break;
                    default:
                        System.out.println("번역된 문장: " + targetText);
                        targetLangTxt.setText(targetText);
                        break;
                }



/*
                if (targetText == null){
                    Toast.makeText(TranslateLayoutActivity.this, "지원하지 않는 target 언어 입니다.", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }






}