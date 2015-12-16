package bizplay.etl.manager;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bizplay.etl.util.StringUtil;

public class DatabaseManager {

	public List<Map<String,String>> executeQuery( Connection con , String strQuert , String[] strParams )throws Exception{
		ResultSet         		rs        = null;
		List<Map<String,String>>list      = null;
		PreparedStatement 		pstmt     = null;
		try{
			pstmt = con.prepareStatement( strQuert );
			if( strParams != null ){
				for( int i = 0 ; i < strParams.length ; i++ ){
					if( StringUtil.null2void(strParams[i]).indexOf("::int") > -1 ){
						pstmt.setInt( i+1 , Integer.parseInt( strParams[i].substring(0, StringUtil.null2void(strParams[i]).indexOf("::int")) ) );
					}else{
						pstmt.setString( i+1 , strParams[i] );
					}
				}
			}
			rs = pstmt.executeQuery();
			list = getResult( rs );
		}
		catch( Exception e ){
			throw e;
		}
		finally{
			release( rs    );
			release( pstmt );
		}
		return list;
	}
	
	public JSONArray executeQueryToJSON( Connection con , String strQuert , String[] strParams )throws Exception{
		ResultSet         		rs    = null;
		JSONArray               list  = null;
		PreparedStatement 		pstmt = null;
		try{
			pstmt = con.prepareStatement( strQuert );
			
			if( strParams != null ){
				for( int i = 0 ; i < strParams.length ; i++ ){
					if( strParams[i] != "" ){
						if( StringUtil.null2void(strParams[i]).indexOf("::int") > -1 ){
							pstmt.setInt( i+1 , Integer.parseInt( strParams[i].substring(0, StringUtil.null2void(strParams[i]).indexOf("::int")) ) );
						}else{
							pstmt.setString( i+1 , strParams[i] );	
						}
					}
				}
			}
			rs   = pstmt.executeQuery();
			list = getResultToJSON( rs );
		}catch( Exception e ){
			throw e;
		}
		finally{
			release( rs    );
			release( pstmt );
		}
		return list;
	}
	
	
	public synchronized int executeUpdate( Connection con , String strQuert , String[] strParams )throws Exception{
		PreparedStatement 	pstmt = null;
		int					nRet  = 0;
		
		try{
			pstmt = con.prepareStatement( strQuert );
			if( strParams != null ){
				for( int i = 0 ; i < strParams.length ; i++ ){
					if( strParams[i] != "" ){
						if( StringUtil.null2void(strParams[i]).indexOf("::int") > -1 ){
							pstmt.setInt( i+1 , Integer.parseInt( strParams[i].substring(0, StringUtil.null2void(strParams[i]).indexOf("::int")) ) );
						}else{
							pstmt.setString( i+1 , strParams[i] );	
						}
					}
				}
			}
         	nRet = pstmt.executeUpdate();
		} 
		catch (Exception e) {
			throw e; 
		} 
		finally{
			release( pstmt );
		}
		return nRet;
	}
	
	public List<Map<String,String>> getResult( ResultSet rs )throws Exception{
		List <Map<String,String>>  list = new ArrayList<Map<String,String>>();
		ResultSetMetaData          rsmd = rs.getMetaData();
		Map<String,String> 		   m    = null; 	  
		
		while( rs.next() ){
			m = new HashMap<String,String>();
			
			for( int i = 0 ; i < rsmd.getColumnCount() ; i++ ){
				if( rsmd.getColumnType( i+1 ) == Types.CLOB ){
					int			 nBuf   = 1024;
					int			 nRead  = 0;
					char[]		 cBuf   = new char[nBuf];
					Reader		 reader = rs.getCharacterStream( i+1 );
					StringBuffer sb     = new StringBuffer();
                 
					if( reader != null ){
						while( ( nRead = reader.read( cBuf , 0 , nBuf ) ) >= 0 ){
							sb.append( cBuf , 0 , nRead );
						}
						reader.close();
					}
					m.put( ((String)rsmd.getColumnName( i+1 )).toUpperCase() , sb.toString() );
				}else{
					m.put( ((String)rsmd.getColumnName( i+1 )).toUpperCase() , rs.getString( i+1 ) );
				}
			}
			
			list.add( m );
		}
		return list;
	}
	
	public JSONArray getResultToJSON( ResultSet rs )throws Exception{
		JSONArray         list = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		JSONObject        m    = null;
		while( rs.next() ){
			m 	  = new JSONObject();
			for( int i = 0 ; i < rsmd.getColumnCount(); i++ ){
				if( rsmd.getColumnType( i+1 ) == Types.CLOB ){
					int nBuf = 1024;
					int nRead = 0;
					char[] cBuf = new char[nBuf];
					Reader reader = rs.getCharacterStream( i+1 );
					StringBuffer sb = new StringBuffer();
					if( reader != null ){
						while( ( nRead = reader.read( cBuf , 0 , nBuf ) ) >= 0 ){
							sb.append( cBuf , 0 , nRead );
						}
						reader.close();
					}
					m.put( ((String)rsmd.getColumnName( i+1 )).toUpperCase() , sb.toString() );
				}else{
					m.put( ((String)rsmd.getColumnName( i+1 )).toUpperCase() , rs.getString( i+1 ) );
				}
			}
			list.add( m );
		}
		return list;
	}
	

	public void release( Statement stmt ){
		try{
			if (stmt != null){
				stmt.close();
				stmt = null;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void release(PreparedStatement pstmt){
		try{
			if (pstmt != null){
				pstmt.close();
				pstmt = null;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void release(ResultSet rs){
		try{
			if(rs!=null){
				rs.close();
				rs = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
		
	public void rollback(Connection con){
		try{
			if (con != null){
				if (!con.getAutoCommit()) con.rollback();
			}
		}catch (SQLException e){
         e.printStackTrace();
		}
	}
	
	public void commit(Connection con) {
		try {
			if (con != null) {
				if (!con.getAutoCommit()) con.commit();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}	
		
}
