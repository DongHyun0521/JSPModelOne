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
	
	// 글의 총 개수
	public int getAllBbs(String category, String keyword) {
		String sql = "SELECT COUNT(*) FROM bbs";
		
		if (category.equals("title")) {
			sql+= " WHERE title LIKE '%" + keyword + "%' ";
		}
		else if (category.equals("content")) {
			sql+= " WHERE content LIKE '%" + keyword + "%' ";
		}
		else if (category.equals("writer")) {
			sql+= " WHERE id = '" + keyword + "' ";
		}
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글의 총 개수 1/4 (BbsDao: getAllBbs)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글의 총 개수 2/4 (BbsDao: getAllBbs)");
			
			rs = psmt.executeQuery();
			System.out.println("글의 총 개수 3/4 (BbsDao: getAllBbs)");
			
			if (rs.next())
				count = rs.getInt(1);
			System.out.println("글의 총 개수 4/4 (BbsDao: getAllBbs)");
			
		} catch (SQLException e) {
			System.out.println("글의 총 개수 실패 (BbsDao: getAllBbs)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return count;
	}
	
	// 글 작성
	public int bbsWrite(BbsDto dto) {
		String sql = "INSERT INTO bbs (id, ref, step, depth, "
				+ " title, content, wdate, parent, del, readcount) "
				+ " VALUES (?, (SELECT COALESCE(MAX(ref), 0) + 1 FROM bbs), 0, 0, "
				+ " ?, ?, now(), 0, 0, 0)";
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 작성 1/4 (BbsDao: bbsWrite)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글 작성 2/4 (BbsDao: bbsWrite)");
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			System.out.println("글 작성 3/4 (BbsDao: bbsWrite)");
			
			count = psmt.executeUpdate();
			System.out.println("글 작성 4/4 (BbsDao: bbsWrite)");
			
		} catch (Exception  e) {
			System.out.println("글 작성 실패 (BbsDao: bbsWrite)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return count;
	}
	
	// 글 검색
	public List<BbsDto> getBbsSearchPagingList(String category, String keyword, int pageNumber) {
		String sql = "SELECT seq, id, ref, step, depth, "
				+ " title, content, wdate, parent, "
				+ " del, readcount "
				+ " FROM bbs ";
		if (category.equals("title")) {
			sql+= " WHERE title LIKE '%" + keyword + "%' ";
		}
		else if (category.equals("content")) {
			sql+= " WHERE content LIKE '%" + keyword + "%' ";
		}
		else if (category.equals("writer")) {
			sql+= " WHERE id = '" + keyword + "' ";
		}
		sql+= " ORDER BY ref DESC, step ASC ";
		sql+= " LIMIT 10 OFFSET " + (pageNumber * 10);	// 한 페이지에 글 10개씩
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 검색 1/4 (BbsDao: getBbsSearchList)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글 검색 2/4 (BbsDao: getBbsSearchList)");
			
			rs = psmt.executeQuery();
			System.out.println("글 검색 3/4 (BbsDao: getBbsSearchList)");
			
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
			System.out.println("글 검색 4/4 (BbsDao: getBbsSearchList)");
			
		} catch (SQLException e) {
			System.out.println("글 검색 실패 (BbsDao: getBbsSearchList)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return list;
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
			System.out.println("조회수+1 2/4 (BbsDao: addReadcount)");
			
			psmt.setInt(1, dto.getSeq());
			System.out.println("조회수+1 3/4 (BbsDao: addReadcount)");
			
			readcount = psmt.executeUpdate();
			System.out.println("조회수+1 4/4 (BbsDao: addReadcount)");
			
		} catch (Exception  e) {
			System.out.println("조회수+1 실패 (BbsDao: addReadcount)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return readcount;
	}
	
	// 글 검색
	public List<BbsDto> getBbsSearchList(String category, String keyword) {
		String sql = "SELECT seq, id, ref, step, depth, "
				+ " title, content, wdate, parent, "
				+ " del, readcount "
				+ " FROM bbs ";
		if (category.equals("title")) {
			sql+= " WHERE title LIKE '%" + keyword + "%' ";
		}
		else if (category.equals("content")) {
			sql+= " WHERE content LIKE '%" + keyword + "%' ";
		}
		else if (category.equals("writer")) {
			sql+= " WHERE id = '" + keyword + "' ";
		}
		sql+= " ORDER BY ref DESC, step ASC;";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 검색 1/4 (BbsDao: getBbsSearchList)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글 검색 2/4 (BbsDao: getBbsSearchList)");
			
			rs = psmt.executeQuery();
			System.out.println("글 검색 3/4 (BbsDao: getBbsSearchList)");
			
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
			System.out.println("글 검색 4/4 (BbsDao: getBbsSearchList)");
			
		} catch (SQLException e) {
			System.out.println("글 검색 실패 (BbsDao: getBbsSearchList)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return list;
	}
	
	// 글 수정
	public int bbsUpdate(BbsDto dto) {
		String sql = "UPDATE bbs SET title = ?, content = ? "
				+ " WHERE seq = ? AND id = ?";
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 수정 1/4 (BbsDao: bbsUpdate)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글 수정 2/4 (BbsDao: bbsUpdate)");
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getSeq());
			psmt.setString(4, dto.getId());
			System.out.println("글 수정 3/4 (BbsDao: bbsUpdate)");
			
			count = psmt.executeUpdate();
			System.out.println("글 수정 4/4 (BbsDao: bbsUpdate)");
			
		} catch (Exception  e) {
			System.out.println("글 수정 실패 (BbsDao: bbsUpdate)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return count;
	}
	
	// 글 삭제
	public int bbsDelete(BbsDto dto) {
		String sql = "UPDATE bbs SET del = 1 "
				+ " WHERE seq = ? AND id = ?";
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("글 삭제 1/4 (BbsDao: bbsWrite)");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("글 삭제 2/4 (BbsDao: bbsWrite)");
			
			psmt.setInt(1, dto.getSeq());
			psmt.setString(2, dto.getId());
			System.out.println("글 삭제 3/4 (BbsDao: bbsWrite)");
			
			count = psmt.executeUpdate();
			System.out.println("글 삭제 4/4 (BbsDao: bbsWrite)");
			
		} catch (Exception  e) {
			System.out.println("글 삭제 실패 (BbsDao: bbsWrite)");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, null);
		}
		return count;
	}
	
	// 답글 작성
	public int answer(int seq, BbsDto bbs) {
		String sqlUpdate = "UPDATE bbs "
				+ " SET step = step + 1 "
				+ " WHERE ref = (SELECT ref FROM bbs WHERE seq = ?) "
				+ " AND step > (SELECT step FROM bbs WHERE seq = ?) ";
		
		String sqlInsert = "INSERT INTO bbs (id, ref, step, depth, "
				+ " title, content, wdate, parent, del, readcount) "
				+ " VALUES (?, (SELECT ref FROM bbs WHERE seq = ?), "
				+ " (SELECT step FROM bbs WHERE seq = ?) + 1, "
				+ " (SELECT depth FROM bbs WHERE seq = ?) + 1, "
				+ " ?, ?, now(), ?, 0, 0) ";
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sqlUpdate);
			System.out.println("답글 작성 1/7 (BbsDao: answer)");
			
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			System.out.println("답글 작성 2/7 (BbsDao: answer)");
			
			count = psmt.executeUpdate();
			System.out.println("답글 작성 3/7 (BbsDao: answer)");
			
			psmt.clearParameters();	// 초기화
			System.out.println("답글 작성 4/7 (BbsDao: answer)");
			
			psmt = conn.prepareStatement(sqlInsert);
			System.out.println("답글 작성 5/7 (BbsDao: answer)");
			
			psmt.setString(1, bbs.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, bbs.getTitle());
			psmt.setString(6, bbs.getContent());
			psmt.setInt(7, seq);
			System.out.println("답글 작성 6/7 (BbsDao: answer)");
			
			count = psmt.executeUpdate();
			System.out.println("답글 작성 7/7 (BbsDao: answer)");
			
		} catch (Exception  e) {
			System.out.println("답글 작성 실패 (BbsDao: answer)");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("답글 작성 롤백 실패 (BbsDao: answer)");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("답글 작성 오토커밋On 실패 (BbsDao: answer)");
				e.printStackTrace();
			}
			DBClose.close(psmt, conn, null);
		}
		return count;
	}
}