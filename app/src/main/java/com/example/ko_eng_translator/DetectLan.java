package com.example.ko_eng_translator;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class DetectLan extends AsyncTask<String, Void, String> {

    String source;
    String clientId;
    String clientSecret;

    public DetectLan(String source){
        this.source = source;
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
            String text = URLEncoder.encode(source, "UTF-8");  /// 번역할 문장 Edittext  입력

//            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            String apiURL = "https://openapi.naver.com/v1/papago/detectLangs";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);


            // post request
            String postParams = "query=" + text;
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
            result = result.split("\"")[3];
            System.out.println(result);

            return result;


            //        textView.setText(response.toString());
/*            getresult = response.toString();

            getresult = getresult.split("\"")[27];   //스플릿으로 번역된 결과값만 가져오기*/


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
