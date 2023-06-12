package wifi.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import wifi.Secret;
import wifi.dto.DbController;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import static wifi.dto.DbController.*;

public class ApiRequest {

    public static JsonObject apiResult() throws IOException {
        JsonParser jsonParser = new JsonParser();
        StringBuilder urlTemp = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlTemp.append("/" + URLEncoder.encode(Secret.KEY, "UTF-8"));
        urlTemp.append("/" + URLEncoder.encode("json/TbPublicWifiInfo", "UTF-8"));
        urlTemp.append("/" + URLEncoder.encode("1", "UTF-8")); // 시작값
        urlTemp.append("/" + URLEncoder.encode("100", "UTF-8")); // 종료값

        URL url = new URL(urlTemp.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code : " + httpURLConnection.getResponseCode());

        System.out.println("url : " + url);

        BufferedReader br;
        if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        }

        // output
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        httpURLConnection.disconnect();

        // String -> Json 변환
        Object object = jsonParser.parse(sb.toString());
        JsonObject jsonObject = (JsonObject) object;

        System.out.println(jsonObject);

        return jsonObject;
    }

    public static int totalCnt(JsonObject object) throws IOException {

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject2 = (JsonObject) object.get("TbPublicWifiInfo");
        JsonElement jsonObject3 = jsonObject2.get("list_total_count");

        int cnt = jsonObject3.getAsInt();

        return cnt;
    }

    public static void insertAPI() throws IOException {
        JsonObject jsonObject = apiResult();
        DbController dbController = new DbController();

        JsonArray jsonArray = (JsonArray) jsonObject.get("TbPublicWifiInfo").getAsJsonObject().get("row");

        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                dbController.insert(String.valueOf(jsonArray.get(i)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String data = String.valueOf(jsonArray.get(i));
            System.out.println("data : " + data);
        }

    }

    public static JsonObject apiResult1000(int startIdx, int endIdx) throws IOException {
        String start = Integer.toString(startIdx);
        String end = Integer.toString(endIdx);

        JsonParser jsonParser = new JsonParser();
        StringBuilder urlTemp = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlTemp.append("/" + URLEncoder.encode(Secret.KEY, "UTF-8"));
        urlTemp.append("/" + URLEncoder.encode("json/TbPublicWifiInfo", "UTF-8"));
        urlTemp.append("/" + URLEncoder.encode(start, "UTF-8"));
        urlTemp.append("/" + URLEncoder.encode(end, "UTF-8"));

        URL url = new URL(urlTemp.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code : " + httpURLConnection.getResponseCode());

        System.out.println("url : " + url);

        BufferedReader br;
        if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        httpURLConnection.disconnect();

        Object object = jsonParser.parse(sb.toString());
        JsonObject jsonObject = (JsonObject) object;

        return jsonObject;
    }

    public static void insertDB(JsonObject object)throws IOException {
        DbController dbController = new DbController();

        JsonArray jsonArray = (JsonArray) object.get("TbPublicWifiInfo").getAsJsonObject().get("row");


        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                dbController.insert(String.valueOf(jsonArray.get(i)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String data = String.valueOf(jsonArray.get(i));
            System.out.println("data : " + data);
        }

    }

    public static void getWifiTotal() throws IOException {
        int count = totalCnt(apiResult());
        System.out.println("count = " + count);

        int startIdx = 1;
        int endIdx = 1000;

        while (true) {
            if (endIdx == 1000 * (count / 1000)) {
                startIdx += 1000;
                endIdx = (1000 * (count / 1000)) + count % 1000;
                insertDB(apiResult1000(startIdx, endIdx));
                break;
            } else {
                insertDB(apiResult1000(startIdx, endIdx));
                startIdx += 1000;
                endIdx += 1000;
            }
        }
    }

    public static void updateDis(String x, String y) throws SQLException {
        int cnt = 1;
        int totalCnt = selectCnt();

//        String x1 = "37.577123";
//        String y1 = "127.001694";
        String x1 = x;
        String y1 = y;

        while (true) {
            if (cnt > totalCnt) {
                break;
            }
            System.out.println("현재 cnt : " + cnt);

            String[] arr = selectDistance(cnt); // db 에서 해당 id에 맞는 데이터 조회

            String manageNum = arr[0];
            String x2 = arr[1];
            String y2 = arr[2];

            String dis = Distance.getDistance(x1, y1, x2, y2); // 거리 계산하기
            String[] result = {manageNum, x2, y2, dis};

            updateDistance(result); // db 업데이트

            cnt += 1;
        }
    }

    public static void updateHis(String x, String y) throws SQLException {
        insertHistory(x, y);
    }

    public static void deleteHis (int id) {
        deleteHistory(id);
    }

    public static void main(String[] args) throws IOException, SQLException {

//        getWifiTotal();

//        insertDB(apiResult());

    }

}
