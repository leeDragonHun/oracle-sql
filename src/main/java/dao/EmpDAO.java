package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import vo.Emp;

public class EmpDAO {
	// q005OrderBy.jsp
	   public static ArrayList<HashMap<String, Integer>> selectEmpSalStats() 
               throws Exception {
		ArrayList<HashMap<String, Integer>> list 
		            = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT"
		   + " grade"
		   + ", COUNT(*) count"
		   + ", SUM(sal) sum"
		   + ", AVG(sal) avg"
		   + ", MAX(sal) max"
		   + ", MIN(sal) min"
		   + " FROM emp"
		   + " GROUP BY grade"
		   + " ORDER BY grade ASC";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String, Integer> m = new HashMap<>();
			m.put("grade", rs.getInt("grade"));
			m.put("count", rs.getInt("count"));
			m.put("sum", rs.getInt("sum"));
			m.put("avg", rs.getInt("avg"));
			m.put("max", rs.getInt("max"));
			m.put("min", rs.getInt("min"));
			list.add(m);
		}
		conn.close();
		return list;
		}
	   
    // q007SelfJoin.jsp에 쓸 메서드
	public static ArrayList<HashMap<String, Object>> selfJoin() throws Exception {
		// 반환할 타입 생성
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		
		// DB 연동
		Connection conn = DBHelper.getConnection();
		
		// 사용할 쿼리 준비
		String sql = "SELECT"
				+ " e1.empno empno,"
				+ " e1.ename ename,"
				+ " e1.grade grade,"
				+ " NVL(e2.ename, '관리자없음') \"mgrName\","
				+ " NVL(e2.grade, 0) \"mgrGrade\""
				+ " FROM emp e1"
				+ " LEFT OUTER JOIN emp e2"
				+ " ON e1.mgr = e2.empno"
				+ " ORDER BY e1.empno ASC";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		// HashMap에 값 해당하는 값 넣기
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<>();
			m.put("empno", rs.getInt("empno"));
			m.put("ename", rs.getString("ename"));
			m.put("grade", rs.getInt("grade"));
			m.put("mgrName", rs.getString("mgrName"));
			m.put("mgrGrade", rs.getInt("mgrGrade"));
			list.add(m);
		}
		// 자원반납
		conn.close();
		// 반환값
		return list;
	}
	
    // q006GroupBy.jsp
	public static ArrayList<Emp> selectEmpListSort(String col, String sort) throws Exception {
		
		// 매개값 디버깅
		System.out.println("EmpDAO.selectEmpListSort 에서 받은 칼럼 : " + col);
		System.out.println("EmpDAO.selectEmpListSort 에서 받은 순서 : " + sort);
		
		ArrayList<Emp> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		
		// 이번에 사용할 쿼리
		String sql = "SELECT empno, ename"
				+ " FROM emp";
		
		if(col != null && sort != null) {
			sql = sql + " ORDER BY "+col+" "+sort;
		}
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		System.out.println("완성쿼리 : " + stmt);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEmpNo(rs.getInt("empno"));
			e.setEname(rs.getString("ename"));
			list.add(e);
		}
		
		/*
		 * 동적쿼리(쿼리문자열이 매개값에 분기되어 차이가 나는 경우
		 * 없다
		 * empno ASC
		 * empno DESC
		 * ename ASC
		 * ename DESC
		 */
		
		conn.close();
		return list;
	}
	
	// q004WhereIn.jsp
	public static ArrayList<Emp> selectEmpListByGrade(ArrayList<Integer> ckList) throws Exception {
		ArrayList<Emp> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT ename, grade"
				+ " FROM emp"
				+ " WHERE grade IN ";
		PreparedStatement stmt = null;
		if(ckList.size() == 1) {
			sql = sql + "(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
		} else if(ckList.size() == 2) {
			sql = sql + "(?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
		} else if(ckList.size() == 3) {
			sql = sql + "(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			stmt.setInt(3, ckList.get(2));
		} else if(ckList.size() == 4) {
			sql = sql + "(?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			stmt.setInt(3, ckList.get(2));
			stmt.setInt(4, ckList.get(3));
		} else if(ckList.size() == 5) {
			sql = sql + "(?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			stmt.setInt(3, ckList.get(2));
			stmt.setInt(4, ckList.get(3));
			stmt.setInt(5, ckList.get(4));
		}
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEname(rs.getString("ename"));
			e.setGrade(rs.getInt("grade"));
			list.add(e);
		}
		conn.close();
		return list;
	}
	
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
