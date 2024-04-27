<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<!-- 컨트롤러 -->
<%
    ArrayList<HashMap<String, Integer>> list
        = EmpDAO.selectEmpSalStats();
%>


<!-- 뷰 -->
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
    <h1>등급별 Sal통계</h1>
    <table border="1">
        <tr>
            <th>grade</th>
            <th>count</th>
            <th>sum</th>
            <th>avg</th>
            <th>max</th>
            <th>min</th>
        </tr>
            <%
                for(HashMap<String, Integer> m : list) {
            %>
                    <tr>
                        <td>
                            <%=m.get("grade") %>
                        </td>
                        <td>
                            <%
                                for(int i=0; i < m.get("count"); i++){
                            %>
                                    ⭐
                            <%
                                }
                            %>
                        </td>
                        <td><%=m.get("sum") %></td>
                        <td><%=m.get("avg") %></td>
                        <td><%=m.get("max") %></td>
                        <td><%=m.get("min") %></td>
                    </tr>
            <%
                }
            %>
    </table>
</body>
</html>