package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import vo.Emp;

public class EmpDAO {
	// DEPTNO 뒤에 부서별 인원 같이 조회하는 메서드

	public static ArrayList<HashMap<String, Integer>> selectDeptNoCntList() throws Exception {
		ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		Connection conn = DBHelper.getConnection();
		String sql ="select count(*) cnt, deptno"
				+ " from emp"
				+ " where deptno is not null"
				+ " group by deptno"
				+ " order by deptno asc";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String, Integer> m = new HashMap<String, Integer>();
			m.put("deptno", rs.getInt("deptno"));
			m.put("cnt", rs.getInt("cnt"));
			list.add(m);
		}
		conn.close();
		return list;
	}
	
	public static ArrayList<HashMap<String, Object>> selectJobCaseList() throws Exception{
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT ename,"
				+ "        job,"
				+ "        CASE"
				+ "        WHEN job = 'PRESIDENT' Then '빨강'"
				+ "        WHEN job = 'MANAGER' THEN '주황'"
				+ "        WHEN job = 'ANALYST' THEN '노랑'"
				+ "        WHEN job = 'CLERK' THEN '초록'"
				+ "        ELSE '파랑' END color"
				+ " FROM emp";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("ename", rs.getString("ename"));
			m.put("job", rs.getString("job"));
			m.put("color", rs.getString("color"));
			list.add(m);
		}
		return list;
	}
	
	// 중복을 제외한 DEPTNO 목록을 출력하는 메서드
	public static ArrayList<Integer> selectDeptNoList() throws Exception {
		ArrayList<Integer> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT DISTINCT deptno deptNo"
				+ " FROM emp"
				+ " WHERE deptno IS NOT NULL"
				+ " ORDER BY deptno ASC";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Integer i = rs.getInt("deptNo"); // 랩퍼타입과 기본타입간에 Auto Boxing
			list.add(i);
		}
		
		conn.close();
		return list;
	}
	
	// 조인으로 Map을 사용하는 겨우
	public static ArrayList<HashMap<String, Object>> selectEmpAndDeptList()
													throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT"
				+ " emp.empno empNo, emp.ename ename, emp.deptno deptNo,"
				+ " dept.dname dname"
				+ " FROM emp INNER JOIN dept"
				+ " ON emp.deptno = dept.deptno";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<>();
			m.put("empNo", rs.getInt("empNo"));
			m.put("ename", rs.getString("ename"));
			m.put("deptNo", rs.getInt("deptNo"));
			m.put("dname", rs.getString("dname"));
			list.add(m);
		}
		return list;
	}
	
	// VO 사용
	public static ArrayList<Emp> selectEmpList() throws Exception {
		ArrayList<Emp> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT"
				+ " empno empNo, ename, sal"
				+ " FROM emp";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEmpNo(rs.getInt("empNo"));
			e.setEname(rs.getString("ename"));
			e.setSal(rs.getDouble("sal"));
			list.add(e);
		}
		
		return list;
	}
}
