<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<!-- 컨트롤러 -->
<%
    // DAO 호출
    ArrayList<HashMap<String, Object>> list = EmpDAO.selfJoin();
%>
<!-- 뷰 -->
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
    <h1>grade</h1>
    <table border="1">
        <tr>
            <th>EMPNO</th>
            <th>ENAME</th>
            <th>GRADE</th>
            <th>mgrName</th>
            <th>mgrGrade</th>
        </tr>
            <%
                for(HashMap<String, Object> m : list){
            %>
                <tr>
                    <td><%=m.get("empno") %></td>
                    <td><%=m.get("ename") %></td>
                    <td>
                        <%
                            for(int i=0; i < (int)(m.get("grade")); i++){
                        %>
                                ⭐
                        <%
                            }
                        %>
                    </td>
                    <td><%=m.get("mgrName") %></td>
                    <td>
                        <%
                            for(int i=0; i < (int)(m.get("mgrGrade")); i++){
                        %>
                                ❤️
                        <%
                            }
                        %>
                    </td>
                </tr>
            <%
                }
            %>
    </table>
</body>
</html>