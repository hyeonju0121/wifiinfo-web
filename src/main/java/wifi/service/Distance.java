package wifi.service;

public class Distance {

    // 두 좌표(위도, 경도) 사이의 거리 구하기
    public static String getDistance(String x1, String y1, String x2, String y2) {

        double lat1 = Double.parseDouble(x1.replace("\"", ""));
        double lon1 = Double.parseDouble(y1.replace("\"", ""));
        double lat2 = Double.parseDouble(x2);
        double lon2 = Double.parseDouble(y2);

        double distance;
        final double RADIUS = 6731; // 반지름
        double toRadian = Math.PI / 180;

        double delToLat = Math.abs(lat2 - lat2) * toRadian;
        double delToLon = Math.abs(lon2 - lon1) * toRadian;

        double sinLat = Math.sin(delToLat / 2);
        double sinLon = Math.sin(delToLon / 2);
        double squareRoot = Math.sqrt(sinLat * sinLat + Math.cos(lat2 * toRadian) * Math.cos(lat1 * toRadian) * sinLon * sinLon);

        distance = 2 * RADIUS * Math.asin(squareRoot);

        String dis = String.format("%.4f", distance / 1000);
//        String dis = Double.parseDouble(temp);


        return dis;
    }

    public static void main(String[] args) {

        String x1 = "37.577123";
        String y1 = "127.001694";
        String x2 = "37.62364";
        String y2 = "126.9167";

//        String dis = getDistance(x1, y1, x2, y2);
//        System.out.println(dis);

    }



}
