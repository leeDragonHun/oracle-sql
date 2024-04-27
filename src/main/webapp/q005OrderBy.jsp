<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "vo.*" %>
<%@ page import = "java.util.*" %>

<!-- 컨트롤러 -->
<%
    // 정렬할 컬럼
    String col = request.getParameter("col");
    System.out.println("정렬할 칼럼 : " + col);

    // 오름차순asc? 내림차순desc?
    String sort = request.getParameter("sort");
    System.out.println("정렬할 순서 : " + sort);
    
    // DAO안에 잘 만들어 놓은 모델을 반환하자 
    ArrayList<Emp> list = EmpDAO.selectEmpListSort(col, sort);
    System.out.println("리스트의 사이즈 : " + list.size());
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
    <table border="1">
        <tr>
            <th>
                empno
                <a href="./q005OrderBy.jsp?col=empno&sort=asc">오름</a>
                <a href="./q005OrderBy.jsp?col=empno&sort=desc">내림</a>
            </th>
            <th>
                ename
                <a href="./q005OrderBy.jsp?col=ename&sort=asc">오름</a>
                <a href="./q005OrderBy.jsp?col=ename&sort=desc">내림</a>
            </th>
        </tr>
        
        <%
            for(Emp e : list){
        %>
                <tr>
                    <td><%=e.getEmpNo() %></td>
                    <td><%=e.getEname() %></td>
                </tr>
        <%        
            }
        %>
    </table>
</body>
</html>
















