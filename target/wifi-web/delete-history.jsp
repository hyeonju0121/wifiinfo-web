<%@ page import="static wifi.service.ApiRequest.deleteHis" %><%--
  Created by IntelliJ IDEA.
  User: a0108
  Date: 2023-06-12
  Time: 오후 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <%
        int id = Integer.parseInt(request.getParameter("id"));

        deleteHis(id);

        response.sendRedirect("history.jsp");
    %>

</body>
</html>
