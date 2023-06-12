<%@ page import="wifi.model.HistoryModel" %>
<%@ page import="java.util.List" %>
<%@ page import="wifi.dto.DbController" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #history {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #history td, #history th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #history tr:nth-child(even){background-color: #f2f2f2;}

        #history tr:hover {background-color: #ddd;}

        #history th {
            font-weight: bold;
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }

        #history td {
            text-align: center;
        }
    </style>
</head>
<body>
    <%
        DbController dbController = new DbController();
        List<HistoryModel> historyList = DbController.listHistory();
    %>


    <h1>위치 히스토리 목록</h1>
    <div>
        <a href = "index.jsp">홈</a> |
        <a href = "history.jsp">위치 히스토리 목록</a> |
        <a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
        <br>
    </div>

    <table id="history">
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        <tr>
                <%
                for (HistoryModel history : historyList) {
            %>
        <tr>
            <% int id = history.getID();
                String send = Integer.toString(id);
            %>
            <td> <%= id %> </td>
            <td> <%=history.getX()%> </td>
            <td> <%=history.getY()%> </td>
            <td> <%=history.getCheckDate()%> </td>
            <td>
                <button type="button" onclick="location.href='delete-history.jsp?id=<%=send%>'">삭제</button>
            </td>
        </tr>
        <%
            }
        %>
        </tr>
    </table>





</body>
</html>
