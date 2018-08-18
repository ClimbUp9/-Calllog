package com.oracle.calllog.analyze.utils;

import java.sql.*;
import java.util.*;


public class DBTool {
	
	//连接
	private Connection conn;
	//处理对象
	private Statement stmt;
	//编译预处理
	private PreparedStatement pstmt;
	//结果集合
	private ResultSet rs;
	
	
	/**
	 * 提交sql
	 * @param sql
	 */
	public void create(String sql){
		try {
			conn = ConnectionPool.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnectionPool.getInstance().revokeConnection(rs, stmt, conn);
		}
		
	}
	
	/**
	 * 编译预处理模式的查询
	 * @param sql
	 * @param params
	 * @return
	 * 	List<Map<String, Object>> results;
	 */
	public List<Map<String, Object>> queryByPrepared(String sql,Object...params) {
		List<Map<String, Object>> list = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			for(int index = 0;index<params.length;index++){
				pstmt.setObject(index+1, params[index]);				
			}
			ResultSet rs = pstmt.executeQuery();
			list = getListResults(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getInstance().revokeConnection(rs, pstmt, conn);
		}
		return list;
	}
	
	/**
	 * 编译预处理增删改
	 * @param sql
	 * @param params
	 * @return
	 */
	public int updateByPrepared(String sql,Object...params) {
		int count = 0;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			ConnectionPool.getInstance().beginTransacation(conn);
			for(int index=0;index<params.length;index++){
				pstmt.setObject(index+1,params[index]);
			}
			count = pstmt.executeUpdate();
			ConnectionPool.getInstance().commit(conn);
			
		} catch (SQLException e) {
			ConnectionPool.getInstance().rollback(conn);
			
			System.out.println("检查sql语句");
			e.printStackTrace();
		}finally{
			ConnectionPool.getInstance().revokeConnection(rs, pstmt, conn);
		}
		return count;
	}
	
	/**
	 * 普通的批处理String...sqls
	 * @param sql
	 * @param isclose
	 * @return
	 */
	public int[] updateBystmtBatch(String...sqls) {
		List<String> sqlList = new ArrayList<String>();
		for(String sql :sqls){
			sqlList.add(sql);
		}
		return updateBystmtBatch(sqlList);
	}
	
	/**
	 * 普通的批处理List<String>sqls
	 * @param sql
	 * @param isclose
	 * @return
	 */
	public int[] updateBystmtBatch(List<String>sqls) {
		int[] count = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			stmt = conn.createStatement();
			for(String sql : sqls){
				stmt.addBatch(sql);
			}
			ConnectionPool.getInstance().beginTransacation(conn);
			count = stmt.executeBatch();
			ConnectionPool.getInstance().commit(conn);
		} catch (SQLException e) {
			ConnectionPool.getInstance().rollback(conn);
			System.out.println("检查sql语句");
			e.printStackTrace();
		}finally{
			ConnectionPool.getInstance().revokeConnection(rs, stmt, conn);
		}
		return count;
	}
	
	
	/**
	 * 编译预处理模式的批处理Object[]...values
	 * @param sql
	 * @return int[]
	 */
	public int[] updateByPstmtBatch(String sql,Object[]...values) {
		List<Object[]> sqlList = new ArrayList<Object[]>();
		for(Object[] value :values){
			sqlList.add(value);
		}
		return updateByPstmtBatch(sql,sqlList);
	}
	
	/**
	 * 编译预处理模式的批处理List<Object[]> values
	 * @param sql
	 * @return int[]
	 */
	public int[] updateByPstmtBatch(String sql,List<Object[]> values) {
		int[] count = null;
		
		try {
			conn = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for(Object[] value :values){
				for(int index = 0;index<value.length;index++){
					pstmt.setObject(index+1,value[index]);
				}
				pstmt.addBatch();
			}
			ConnectionPool.getInstance().beginTransacation(conn);
			count = pstmt.executeBatch();
			ConnectionPool.getInstance().commit(conn);
		} catch (SQLException e) {
			ConnectionPool.getInstance().rollback(conn);
			System.out.println("检查sql语句");
			e.printStackTrace();
		}finally{
			ConnectionPool.getInstance().revokeConnection(rs, pstmt, conn);
		}
		return count;
	}
	
	
	/**
	 * ResultSet的结果转为list存储起来
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	 private List<Map<String, Object>> getListResults(ResultSet rs) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while(rs.next()){
			Map<String, Object> map = new HashMap<String, Object>();
			for(int index = 1;index<=rs.getMetaData().getColumnCount();index++){
				String key = rs.getMetaData().getColumnLabel(index);
				Object value = rs.getObject(key);
				map.put(key, value);
			}
			list.add(map);
		}
		return list;
	}
	
}
