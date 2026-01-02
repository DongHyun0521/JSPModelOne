// JSPModelOne - com.pro.dao - MemberDao.java
package com.mbc.pro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mbc.pro.db.DBClose;
import com.mbc.pro.db.DBConnection;
import com.mbc.pro.dto.MemberDto;

public class MemberDao {
	private static MemberDao dao = null;
	
	public MemberDao() {
		DBConnection.initConnection();
	}
	
	public static MemberDao getInstance() {
		if (dao == null)
			dao = new MemberDao();
		return dao;
	}
	
	// 아이디 중복 확인
	public int idcheck (String id) {
		String sql = "SELECT COUNT(*) FROM member WHERE id = ?;";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("아이디 중복 확인 1/3");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			System.out.println("아이디 중복 확인 2/3");
			
			rs = psmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			System.out.println("아이디 중복 확인 3/3");
			
		} catch (Exception  e) {
			System.out.println("아이디 중복 확인 실패");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return count;
	}
	
	// 회원가입
	public int addMember (MemberDto dto) {
		String sql = "INSERT INTO member "
				+ "(id, pw, name, email, auth) "
				+ "VALUES (?, ?, ?, ?, 3);";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("회원가입 1/3");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			System.out.println("회원가입 2/3");
			
			count = psmt.executeUpdate();
			System.out.println("회원가입 3/3");
			
		} catch (Exception  e) {
			System.out.println("회원가입 실패");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return count;
	}
	
	// 로그인
	public MemberDto login (String id, String pw) {
		String sql = "SELECT id, name, email, auth "
				+ "FROM member "
				+ "WHERE id = ? AND pw = ?;";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		MemberDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("로그인 1/3");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			System.out.println("로그인 2/3");
			
			rs = psmt.executeQuery();
			if (rs.next()) {
				String _id = rs.getString("id");
				String _name = rs.getString("name");
				String _email = rs.getString("email");
				int _auth = rs.getInt("auth");
				
				dto = new MemberDto(_id, "", _name, _email, _auth);
			}
			System.out.println("로그인 3/3");
		}
		catch (Exception  e) {
			System.out.println("로그인 실패");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return dto;
	}
	
	// 로그인
	/*public int login (MemberDto dto) {
		String sql = "SELECT COUNT(*) FROM member "
				+ "WHERE id = ? AND pw = ?;";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("로그인 1/3");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			System.out.println("로그인 2/3");
			
			rs = psmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			System.out.println("로그인 3/3");
			
		} catch (Exception  e) {
			System.out.println("로그인 실패");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return count;
	}*/
}
