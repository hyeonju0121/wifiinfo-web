<%@ page import="wifi.model.WifiInfoModel" %>
<%@ page import="wifi.dto.DbController" %>
<%@ page import="java.util.List" %>
<%@ page import="static wifi.service.ApiRequest.updateDis" %>
<%@ page import="static wifi.service.ApiRequest.updateHis" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #wifi {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #wifi td, #wifi th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #wifi tr:nth-child(even){background-color: #f2f2f2;}

        #wifi tr:hover {background-color: #ddd;}

        #wifi th {
            font-weight: bold;
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }

        #wifi td {
            text-align: center;
        }
    </style>
</head>
<body>
    <%
        DbController dbController = new DbController();
        List<WifiInfoModel> wifiList = DbController.listTop20();
    %>
<%--    <%--%>
<%--        String lat = request.getParameter("latitude");--%>
<%--        String lnt = request.getParameter("longitude");--%>

<%--        updateDis(lat, lnt);--%>
<%--        updateHis(lat, lnt);--%>
<%--    %>--%>



    <h1>와이파이 정보 구하기</h1>
    <div>
        <a href = "index.jsp">홈</a> |
        <a href = "history.jsp">위치 히스토리 목록</a> |
        <a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
        <br>
    </div>

    <form action = 'around-wifi.jsp', method="GET">
        LAT : <input type = "text" name="latitude">
        LNT :  <input type = "text" name="longitude">
                <input type="button" value="내 위치 가져오기" onclick="get_AT()">
        <input type="submit" value="근처 WIFI 정보 보기">
    </form>

    <table id="wifi">
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>
        <tr>
            <%
                for (WifiInfoModel wifi : wifiList) {
            %>
                <tr>
                <td> <%=wifi.getDistance()%> </td>
                <td> <%=wifi.getManageNum()%> </td>
                <td> <%=wifi.getBorough()%> </td>
                <td> <%=wifi.getWifiName()%> </td>
                <td> <%=wifi.getAddress1()%> </td>
                <td> <%=wifi.getAddress2()%> </td>
                <td> <%=wifi.getiAddress()%> </td>
                <td> <%=wifi.getiType()%> </td>
                <td> <%=wifi.getiAgency()%> </td>
                <td> <%=wifi.getService()%> </td>
                <td> <%=wifi.getNetType()%> </td>
                <td> <%=wifi.getiDate()%> </td>
                <td> <%=wifi.getInOut()%> </td>
                <td> <%=wifi.getConnection()%> </td>
                <td> <%=wifi.getX()%> </td>
                <td> <%=wifi.getY()%> </td>
                <td> <%=wifi.getWorkDate()%> </td>
                </tr>
            <%
                }
            %>
        </tr>
    </table>

</body>
</html>
