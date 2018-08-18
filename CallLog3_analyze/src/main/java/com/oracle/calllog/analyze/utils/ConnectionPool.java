package com.oracle.calllog.analyze.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class ConnectionPool {

	//һ�����ӳ�
	private static ConnectionPool pool = new ConnectionPool();
	private ConnectionPool(){
		try {//Global.CLASSNAME
			Class.forName(PropUtil.getProperty(Global.CLASSNAME));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionPool getInstance(){
		return pool;
	}
	
	private LinkedList<Connection> conns = new LinkedList<Connection>();
	//��ȡһ������
	public synchronized Connection getConnection(){
		Connection conn = null;
		if(conns.size()>0){
			conn = conns.removeFirst();
		}else{
			try {
				conn = DriverManager.getConnection(
						PropUtil.getProperty(Global.URL),
						PropUtil.getProperty(Global.USERNAME),
						PropUtil.getProperty(Global.PASSWORD));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	//����һ������
	public void revokeConnection(ResultSet rs,Statement stmt,Connection conn){
		
		try {
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			synchronized (conns) {	
				if(conns.size()<10){
					conns.addLast(conn);
				}else{
					conn.close();
				}
			}
		} catch (SQLException e) {
			System.err.println("����ʧ��");
			e.printStackTrace();
		}

	}

	/**
	 * ��������
	 */
	public void beginTransacation(Connection conn){
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ύ����
	 */
	public void commit(Connection conn){
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ع�
	 */
	public void rollback(Connection conn){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
