<%@ page import="static wifi.service.ApiRequest.updateDis" %>
<%@ page import="static wifi.service.ApiRequest.updateHis" %><%--
  Created by IntelliJ IDEA.
  User: a0108
  Date: 2023-06-12
  Time: 오후 5:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
  <%
    String lat = request.getParameter("latitude");
    String lnt = request.getParameter("longitude");

    updateDis(lat, lnt);
    updateHis(lat, lnt);

    response.sendRedirect("around-wifi.jsp?lat=" + lat + "&lnt=" + lnt);
  %>
update

</body>
</html>
