package kr.co.dhflour.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.dhflour.emaillist.vo.EmailListVo;

public class EmailListDao {
	//list fetch (select multi)
	public List<EmailListVo> fetchList() {
		List<EmailListVo> list = new ArrayList<EmailListVo>();
		
		Connection conn = null; //지역변수는 반드시 초기화해주어야 한다.(=null)
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			stmt = conn.createStatement();
			String sql = "select no, last_name, first_name, email from emaillist";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				//가져와서 변수에 담는다.
				long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				//VO 객체를 만들어서 변수 값을 넣는다.
				EmailListVo vo = new EmailListVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				//리스트에 담는다.
				list.add(vo);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 -_-");
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("연결실패 -_-+" + e);
			//e.printStackTrace();
		} finally {
			try {
				//conn, pstmt이 null인 상태에서 들어온 경우 Error발생을 막기위해.
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	//fetch (select single)
	public EmailListVo fetch(long num) {
		EmailListVo vo = null;
		
		Connection conn = null; //지역변수는 반드시 초기화해주어야 한다.(=null)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			String sql = "select no, last_name, first_name, email from emaillist where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				
				vo = new EmailListVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 -_-");
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("연결실패 -_-+" + e);
			//e.printStackTrace();
		} finally {
			try {
				//conn, pstmt이 null인 상태에서 들어온 경우 Error발생을 막기위해.
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	//insert (insert row)
	public boolean InsertVo(EmailListVo vo) {
		
		boolean result = false;
		
		Connection conn = null; //지역변수는 반드시 초기화해주어야 한다.(=null)
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			//3. Statenemt 준비
			String sql = "insert into emaillist values (seq_emaillist.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			//4. 값 바인딩
			pstmt.setString(1, vo.getLastName());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());
			
			//5. SQL문 실행
			int count = pstmt.executeUpdate();
			
			//6. 성공유무
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 -_-");
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("연결실패 -_-+");
			//e.printStackTrace();
		} finally {
			//6. 자원정리
			try {
				
				if (pstmt != null) {
					pstmt.close();
				}
				//conn이 null인 상태에서 들어온 경우 Error발생을 막기위해.
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
