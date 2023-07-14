// 이거 수정 해야함!!!! 언어 감지랑 코드 똑같음
















package com.example.ko_eng_translator;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateLan extends AsyncTask<String, Void, String> {

    String text;
    String source;
    String target;
    String clientId;
    String clientSecret;

    public TranslateLan(String source, String target, String text){
        this.source = source;
        this.target = target;
        this.text = text;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        clientId = MyClientID.ClientID; //애플리케이션 클라이언트 아이디값";
        clientSecret = MyClientID.ClientSecret; //애플리케이션 클라이언트 시크릿값";

        System.out.println(clientId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected String doInBackground(String... strings) {
        //////네이버 API
        try {
            text = URLEncoder.encode(text, "UTF-8");  /// 번역할 문장 Edittext  입력

            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);


            // post request
            String postParams = "source=" + source + "&" + "target=" + target + "&text=" + text;
            System.out.println(postParams);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            // {"langCode":"en"}

            String result = response.toString();
            result = result.split("\"")[15];
            System.out.println(result);



            return result;


        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // 네트워크 요청 결과를 처리합니다.
        System.out.println("이건 onPostExcute : " + result);
    }

}
