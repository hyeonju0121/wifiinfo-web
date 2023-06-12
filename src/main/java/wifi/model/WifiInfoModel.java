package wifi.model;

public class WifiInfoModel {

    private Double  distance; // 거리
    private String manageNum; // 관리번호
    private String borough; // 자치구
    private String wifiName;
    private String address1; // 도로명주소
    private String address2; // 상세주소
    private String iAddress; // 설치위치
    private String iType; // 설치유형
    private String iAgency; // 설치기관
    private String service; // 서비스구분
    private String netType; // 망종류
    private String iDate; // 설치년도
    private String inOut; // 실내외구분
    private String connection; // 접속환경
    private String x; // x좌표
    private String y; // y좌표
    //    private Date workDate; // 작업일자
    private String workDate; // 작업일자

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getManageNum() {
        return manageNum;
    }

    public void setManageNum(String manageNum) {
        this.manageNum = manageNum;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getiAddress() {
        return iAddress;
    }

    public void setiAddress(String iAddress) {
        this.iAddress = iAddress;
    }

    public String getiType() {
        return iType;
    }

    public void setiType(String iType) {
        this.iType = iType;
    }

    public String getiAgency() {
        return iAgency;
    }

    public void setiAgency(String iAgency) {
        this.iAgency = iAgency;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getiDate() {
        return iDate;
    }

    public void setiDate(String iDate) {
        this.iDate = iDate;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }
}
