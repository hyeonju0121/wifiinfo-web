package wifi.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import wifi.Secret;
import wifi.model.HistoryModel;
import wifi.model.WifiInfoModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbController {

    public static ResultSet resultSet = null;
    public static Connection connection = null;
    public static Statement statement = null;

    public static void Connection() throws SQLException {
        String url = Secret.URL;
        String dbUserId = Secret.DB_USERID;
        String dbPassword = Secret.DB_PASSWORD;

        // jdbc 드라이버 로드, 커넥션, 스테이트먼트 객체 생성
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            statement = connection.createStatement();

            System.out.println("db 연결 성공");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int selectCnt() throws SQLException {
        Connection();
        int cnt = 0;
        try {
            String sql = " select * from wifi ; ";
            resultSet = statement.executeQuery(sql);

            resultSet.last();
            cnt = resultSet.getRow();
            resultSet.beforeFirst();

//            System.out.println(cnt);


        } catch (SQLException e) {

        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cnt;
    }

    public static void insert(String data) throws SQLException {

        try {
            WifiInfoModel wifiInfoModel = new WifiInfoModel();

            String temp = data;
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(temp);

            // manageNum 정제
//            String manageNum = jsonObject.get("X_SWIFI_MGR_NO").getAsString();
//            manageNum = "WF" + manageNum.substring(manageNum.length()-6, manageNum.length());

            wifiInfoModel.setManageNum(jsonObject.get("X_SWIFI_MGR_NO").getAsString());
//            wifiInfoModel.setManageNum(manageNum);
            wifiInfoModel.setBorough(jsonObject.get("X_SWIFI_WRDOFC").getAsString());
            wifiInfoModel.setWifiName(jsonObject.get("X_SWIFI_MAIN_NM").getAsString());
            wifiInfoModel.setAddress1(jsonObject.get("X_SWIFI_ADRES1").getAsString());
            wifiInfoModel.setAddress2(jsonObject.get("X_SWIFI_ADRES2").getAsString());
            wifiInfoModel.setiAddress(jsonObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
            wifiInfoModel.setiType(jsonObject.get("X_SWIFI_INSTL_TY").getAsString());
            wifiInfoModel.setiAgency(jsonObject.get("X_SWIFI_INSTL_MBY").getAsString());
            wifiInfoModel.setService(jsonObject.get("X_SWIFI_SVC_SE").getAsString());
            wifiInfoModel.setNetType(jsonObject.get("X_SWIFI_CMCWR").getAsString());
            wifiInfoModel.setiDate(jsonObject.get("X_SWIFI_CNSTC_YEAR").getAsString());
            wifiInfoModel.setInOut(jsonObject.get("X_SWIFI_INOUT_DOOR").getAsString());
            wifiInfoModel.setConnection(jsonObject.get("X_SWIFI_REMARS3").getAsString());
            wifiInfoModel.setX(jsonObject.get("LNT").getAsString());
            wifiInfoModel.setY(jsonObject.get("LAT").getAsString());
            wifiInfoModel.setWorkDate(jsonObject.get("WORK_DTTM").getAsString());

            Connection();

            String sql = String.format("INSERT INTO wifi " +
                            " (manage_num , borough, wifi_name, address1, address2, i_address, i_type, i_agency, service, net_type, i_date, in_out, connection, x, y, word_date ) " +
                            " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', " +
                            "'%s', '%s', '%s', '%s', '%s', '%s', '%s' ) " +
                            "ON DUPLICATE KEY UPDATE manage_num = '%s' ; ",
                    wifiInfoModel.getManageNum(), wifiInfoModel.getBorough(), wifiInfoModel.getWifiName(), wifiInfoModel.getAddress1(), wifiInfoModel.getAddress2(), wifiInfoModel.getiAddress(), wifiInfoModel.getiType(), wifiInfoModel.getiAgency(), wifiInfoModel.getService(),
                    wifiInfoModel.getNetType(), wifiInfoModel.getiDate(), wifiInfoModel.getInOut(), wifiInfoModel.getConnection(), wifiInfoModel.getX(), wifiInfoModel.getY(), wifiInfoModel.getWorkDate(), wifiInfoModel.getManageNum());

            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] selectDistance(int cnt) throws SQLException {
        String[] arr = new String[3];
        int count = cnt;

        try {
            Connection();

            String sql = String.format(" select manage_num, x, y from wifi where id = '%d' ; ", count);

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String manageNum = resultSet.getString("manage_num");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");

                arr[0] = manageNum;
                arr[1] = x;
                arr[2] = y;

                System.out.println(manageNum + ", " + x + ", " + y);
                System.out.println("조회성공!");
            }

            return arr;

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return arr;
    }


    public static void insertHistory(String x, String y) {
        String x1 = x;
        String y1 = y;

        try {
            Connection();

            String sql = String.format("insert into history (x, y, check_date) " +
                    " values ( '%s' , '%s' , now()); ", x1, y1);

            resultSet = statement.executeQuery(sql);

            System.out.println("히스토리 추가 완료!");

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void updateDistance(String[] result) throws SQLException {

        String manageNum = result[0];
        String x = result[1];
        String y = result[2];
        String disTemp = result[3];
        Double dis = Double.parseDouble(disTemp);

        try {
            Connection();

            String sql = String.format(" update wifi set distance = '%s' " +
                    " where manage_num = '%s' and x = '%s' and y = '%s'; ",
                    dis, manageNum, x, y);

            resultSet = statement.executeQuery(sql);

            System.out.println("업데이트 완료!");

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<WifiInfoModel> list() {

        List<WifiInfoModel> wifiList = new ArrayList<WifiInfoModel>();

        try {
            Connection();

            String sql = " select manage_num, distance, borough, wifi_name, address1, address2," +
                    " i_address, i_type, i_agency, service, net_type, i_date, in_out, " +
                    " connection, x, y, word_date " +
                    " from wifi; ";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String manageNum = resultSet.getString("manage_num");
                double distance = resultSet.getDouble("distance");
                String borough = resultSet.getString("borough");
                String wifiName = resultSet.getString("wifi_name");
                String address1 = resultSet.getString("address1");
                String address2 = resultSet.getString("address2");
                String iAddress = resultSet.getString("i_address");
                String iType = resultSet.getString("i_type");
                String iAgency = resultSet.getString("i_agency");
                String service = resultSet.getString("service");
                String netType = resultSet.getString("net_type");
                String iDate = resultSet.getString("i_date");
                String inOut = resultSet.getString("in_out");
                String connection = resultSet.getString("connection");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");
                String workDate = resultSet.getString("word_date");

                WifiInfoModel wifi = new WifiInfoModel();
                wifi.setManageNum(manageNum);
                wifi.setDistance(distance);
                wifi.setBorough(borough);
                wifi.setWifiName(wifiName);
                wifi.setAddress1(address1);
                wifi.setAddress2(address2);
                wifi.setiAddress(iAddress);
                wifi.setiType(iType);
                wifi.setiAgency(iAgency);
                wifi.setService(service);
                wifi.setNetType(netType);
                wifi.setiDate(iDate);
                wifi.setInOut(inOut);
                wifi.setConnection(connection);
                wifi.setX(x);
                wifi.setY(y);
                wifi.setWorkDate(workDate);

                wifiList.add(wifi);
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return wifiList;
    }

    public static List<WifiInfoModel> listTop20() {
        List<WifiInfoModel> wifiList = new ArrayList<WifiInfoModel>();

        try {
            Connection();

            String sql = " select manage_num, distance, borough, wifi_name, address1, address2, " +
                    " i_address, i_type, i_agency, service, net_type, i_date, in_out, " +
                    " connection, x, y, word_date from wifi " +
                    " where distance is not null " +
                    " order by distance asc limit 20; ";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String manageNum = resultSet.getString("manage_num");
                double distance = resultSet.getDouble("distance");
                String borough = resultSet.getString("borough");
                String wifiName = resultSet.getString("wifi_name");
                String address1 = resultSet.getString("address1");
                String address2 = resultSet.getString("address2");
                String iAddress = resultSet.getString("i_address");
                String iType = resultSet.getString("i_type");
                String iAgency = resultSet.getString("i_agency");
                String service = resultSet.getString("service");
                String netType = resultSet.getString("net_type");
                String iDate = resultSet.getString("i_date");
                String inOut = resultSet.getString("in_out");
                String connection = resultSet.getString("connection");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");
                String workDate = resultSet.getString("word_date");

                WifiInfoModel wifi = new WifiInfoModel();
                wifi.setManageNum(manageNum);
                wifi.setDistance(distance);
                wifi.setBorough(borough);
                wifi.setWifiName(wifiName);
                wifi.setAddress1(address1);
                wifi.setAddress2(address2);
                wifi.setiAddress(iAddress);
                wifi.setiType(iType);
                wifi.setiAgency(iAgency);
                wifi.setService(service);
                wifi.setNetType(netType);
                wifi.setiDate(iDate);
                wifi.setInOut(inOut);
                wifi.setConnection(connection);
                wifi.setX(x);
                wifi.setY(y);
                wifi.setWorkDate(workDate);

                wifiList.add(wifi);
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return wifiList;

    }

    public static List<HistoryModel> listHistory() {
        List<HistoryModel> historyList = new ArrayList<HistoryModel>();

        try {
            Connection();

            String sql = " select id, x, y, check_date from history order by id desc ; ";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");
//                Date checkDate = resultSet.getDate("check_date");

                Timestamp checkDateTemp = resultSet.getTimestamp("check_date");
                String checkDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkDateTemp);

                HistoryModel history = new HistoryModel();
                history.setID(id);
                history.setX(x);
                history.setY(y);
                history.setCheckDate(checkDate);

                historyList.add(history);
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return historyList;

    }

    public static void deleteHistory(int id) {
        try {
            Connection();

            String sql = String.format("delete from history where id = '%d' ; ", id );

            resultSet = statement.executeQuery(sql);

            System.out.println("삭제 성공!");

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws SQLException {

//        insertHistory("1", "2");

//        deleteHistory(1);

}
}
