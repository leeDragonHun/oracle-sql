<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="java.util.*"%>
<%
    ArrayList<Integer> list = EmpDAO.selectDeptNoList();
    ArrayList<HashMap<String, Integer>> list2 = EmpDAO.selectDeptNoCntList();
    ArrayList<HashMap<String, Object>> list3 = EmpDAO.selectJobCaseList();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
    <h1>DISTINCT 키워드 연산</h1>
    <select name="deptno">
        <option value="">:::::선택:::::</option>
        <%
            for( Integer i : list){
        %>
            <option value="<%=i %>"><%=i %></option>
        <%
            }
        %>
    </select>
    <h1>DISTINCT대신 GROUP 목록을 출력하는 메서드</h1>
    <table border="1">
        <tr>
            <th>DEPTNO</th>
            <th>COUNT</th>
        </tr>
        <%
            for(HashMap<String, Integer> m : list2){
        %>
                <tr>
                    <td><%=m.get("deptno") %></td>
                    <td><%=m.get("cnt") %></td>
                </tr>
        <%
            }
        %>
    </table>
    <h1>CASE문으로 정렬하기</h1>
    <table border="1">
        <tr>
            <td>ename</td>
            <td>job</td>
            <td>color</td>
        </tr>
                <%
            for(HashMap<String, Object> m : list3){
        %>
                <tr>
                    <td><%=m.get("ename") %></td>
                    <td><%=m.get("job") %></td>
                    <td><%=m.get("color") %></td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>