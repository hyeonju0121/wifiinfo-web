
<%@ page import="static wifi.dto.DbController.selectCnt" %>
<%@ page import="static wifi.service.ApiRequest.getWifiTotal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<style>
    h1{text-align:center;}
    .atag {text-align:center;}
</style>
<body>
    <%
       int cnt = selectCnt();

       if (cnt == 0) {
           getWifiTotal();
       }
    %>
    <h1> <%= selectCnt() %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>

    <div class = "atag">
        <a href = "index.jsp">홈으로 가기</a>
    </div>

</body>
</html>
