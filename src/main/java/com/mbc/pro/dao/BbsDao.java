// JSPModelOne - com.mbc.pro.dao - BbsDao.java
package com.mbc.pro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mbc.pro.db.DBClose;
import com.mbc.pro.db.DBConnection;
import com.mbc.pro.dto.BbsDto;

public class BbsDao {
	private static BbsDao dao = new BbsDao();
	
	private BbsDao() {}
	
	public static BbsDao getInstance() {
		return dao;
	}
	
	// 글 목록
	public List<BbsDto> getBbsList() {
		String sql = "SELECT seq, id, ref, step, depth, "
				+ "title, content, wdate, parent, "
				+ "del, readcount "
				+ "FROM bbs "
				+ "ORDER BY ref DESC, step ASC;";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 목록 1/4 (BbsDao: getBbsList)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글 목록 2/4 (BbsDao: getBbsList)");
			
			rs = psmt.executeQuery();
			System.out.println("글 목록 3/4 (BbsDao: getBbsList)");
			
			while(rs.next()) {
				int seq = rs.getInt("seq");
				String id = rs.getString("id");
				
				int ref = rs.getInt("ref");
				int step = rs.getInt("step");
				int depth = rs.getInt("depth");
				
				String title = rs.getString("title");
				String content = rs.getString("content");
				String wdate = rs.getString("wdate");
				int parent = rs.getInt("parent");
				
				int del = rs.getInt("del");
				int readcount = rs.getInt("readcount");
				
				BbsDto dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, parent, del, readcount);
				list.add(dto);
			}
			System.out.println("글 목록 4/4 (BbsDao: getBbsList)");
			
		} catch (SQLException e) {
			System.out.println("글 목록 실패 (BbsDao: getBbsList)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return list;
	}
	
	// 글 작성
	public int bbsWrite(BbsDto dto) {
		String sql = "INSERT INTO bbs (id, ref, step, depth, "
				+ "title, content, wdate, parent, del, readcount) "
				+ "VALUES (?, (SELECT COALESCE(MAX(ref), 0) + 1 FROM bbs), 0, 0, "
				+ "?, ?, now(), 0, 0, 0);";
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 작성 1/3 (BbsDao: bbsWrite)");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			System.out.println("글 작성 2/3 (BbsDao: bbsWrite)");
			
			count = psmt.executeUpdate();
			System.out.println("글 작성 3/3 (BbsDao: bbsWrite)");
			
		} catch (Exception  e) {
			System.out.println("글 작성 실패 (BbsDao: bbsWrite)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return count;
	}
	
	// 글 상세
	public BbsDto bbsDetail(BbsDto dto) {
		String sql = "SELECT seq, id, ref, step, depth, title, content, wdate, parent, del, readcount "
				+ "FROM bbs "
				+ "WHERE seq = ?;";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 상세 1/4 (BbsDao: bbsDetail)");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, dto.getSeq());
			System.out.println("글 상세 2/4 (BbsDao: bbsDetail)");
			
			rs = psmt.executeQuery();
			System.out.println("글 상세 3/4 (BbsDao: bbsDetail)");
			
			if(rs.next()) {
				int seq = rs.getInt("seq");
				String id = rs.getString("id");
				
				int ref = rs.getInt("ref");
				int step = rs.getInt("step");
				int depth = rs.getInt("depth");
				
				String title = rs.getString("title");
				String content = rs.getString("content");
				String wdate = rs.getString("wdate");
				int parent = rs.getInt("parent");
				
				int del = rs.getInt("del");
				int readcount = rs.getInt("readcount");
				
				dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, parent, del, readcount);
			}
			System.out.println("글 상세 4/4 (BbsDao: bbsDetail)");
			
		} catch (Exception  e) {
			System.out.println("글 상세 실패 (BbsDao: bbsDetail)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return dto;
	}
	
	// 조회수+1
	public int addReadcount(BbsDto dto) {
		String sql = "UPDATE bbs SET readcount = readcount + 1 "
				+ "WHERE seq = ?;";
		Connection conn = null;
		PreparedStatement psmt = null;
		int readcount = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("조회수+1 1/3 (BbsDao: addReadcount)");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, dto.getSeq());
			System.out.println("조회수+1 2/3 (BbsDao: addReadcount)");
			
			readcount = psmt.executeUpdate();
			System.out.println("조회수+1 3/3 (BbsDao: addReadcount)");
			
		} catch (Exception  e) {
			System.out.println("조회수+1 실패 (BbsDao: addReadcount)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return readcount;
	}
}
