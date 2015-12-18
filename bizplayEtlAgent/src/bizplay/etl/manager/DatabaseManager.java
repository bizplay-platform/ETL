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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bizplay.etl.util.StringUtil;

public class DatabaseManager {

	/**
	 * executeQuery
	 * @param con
	 * @param strQuery
	 * @param strParams
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> executeQuery( Connection connection , String query , String[] params )throws Exception{
		ResultSet         		rs        = null;
		List<Map<String,String>>list      = null;
		PreparedStatement 		pstmt     = null;
		try{
			pstmt = connection.prepareStatement( query );
			if( params != null ){
				for( int i = 0 ; i < params.length ; i++ ){
					if( StringUtil.null2void(params[i]).indexOf("::int") > -1 ){
						pstmt.setInt( i+1 , Integer.parseInt( params[i].substring(0, StringUtil.null2void(params[i]).indexOf("::int")) ) );
					}else{
						pstmt.setString( i+1 , params[i] );
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
	
	/**
	 * executeQueryToJSON
	 * @param con
	 * @param strQuery
	 * @param strParams
	 * @return
	 * @throws Exception
	 */
	public JSONArray executeQueryToJSON( Connection connection , String query , String[] params )throws Exception{
		ResultSet         		rs    = null;
		JSONArray               list  = null;
		PreparedStatement 		pstmt = null;
		try{
			pstmt = connection.prepareStatement( query );
			
			if( params != null ){
				for( int i = 0 ; i < params.length ; i++ ){
					if( params[i] != "" ){
						if( StringUtil.null2void(params[i]).indexOf("::int") > -1 ){
							pstmt.setInt( i+1 , Integer.parseInt( params[i].substring(0, StringUtil.null2void(params[i]).indexOf("::int")) ) );
						}else{
							pstmt.setString( i+1 , params[i] );	
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
	
	/**
	 * executeUpdate
	 * @param con
	 * @param strQuery
	 * @param strParams
	 * @return
	 * @throws SQLException
	 */
	public synchronized int executeUpdate( Connection connection , String query , String[] params )throws SQLException{
		PreparedStatement 	pstmt = null;
		int					nRet  = 0;
		
		try{
			pstmt = connection.prepareStatement( query );
			if( params != null ){
				for( int i = 0 ; i < params.length ; i++ ){
					if( params[i] != "" ){
						if( StringUtil.null2void(params[i]).indexOf("::int") > -1 ){
							pstmt.setInt( i+1 , Integer.parseInt( params[i].substring(0, StringUtil.null2void(params[i]).indexOf("::int")) ) );
						}else{
							pstmt.setString( i+1 , params[i] );	
						}
					}
				}
			}
         	nRet = pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			throw e; 
		} 
		finally{
			release( pstmt );
		}
		return nRet;
	}
	
	/**
	 * getResult
	 * @param rs
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * getResultToJSON
	 * @param rs
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * Statement를 반납 합니다.
	 * @param statement
	 */
	public void release( Statement statement ){
		try{
			if (statement != null){
				statement.close();
				statement = null;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * PreparedStatement를 반납 합니다.
	 * @param preparedstatement
	 */
	public void release(PreparedStatement preparedstatement){
		try{
			if (preparedstatement != null){
				preparedstatement.close();
				preparedstatement = null;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * ResultSet을 반납 합니다.
	 * @param resultSet
	 */
	public void release(ResultSet resultSet){
		try{
			if(resultSet!=null){
				resultSet.close();
				resultSet = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Transaction을 설정 합니다.
	 * @param connection
	 */
	public void beginTransaction(Connection connection){
		try{
			if (connection != null){
				if (connection.getAutoCommit()) connection.setAutoCommit(false);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * Transaction을 종료 합니다.
	 * @param connection
	 */
	public void endTransaction(Connection connection){
		try{
			if (connection != null){
				if (!connection.getAutoCommit()) connection.setAutoCommit(true);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}		
	}	
	
	/**
	 * rollback
	 * @param connection
	 */
	public void rollback(Connection connection){
		try{
			if (connection != null){
				if (!connection.getAutoCommit()) connection.rollback();
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * commit
	 * @param connection
	 */
	public void commit(Connection connection) {
		try {
			if (connection != null) {
				if (!connection.getAutoCommit()) connection.commit();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @description : Query 바인딩 변수를 추출합니다.
	 * @param       : String
	 * @return      : List<String>
	 */
	public List<String> getParam( String query ){
		List <String>lparamList = new ArrayList<String>(); 
		
		Pattern p = Pattern.compile("\\#[a-zA-Z0-9 ,_-]*#");
		
		Matcher m = p.matcher(query);
		
		while (m.find()) {
			lparamList.add(m.group().replaceAll("#", ""));
		}
		
		return lparamList;
	}
	
	/**
    * @description : JDBC에서 읽을수 있는 Query문장으로 변경 합니다.
    * @param       : String
    * @return      : String
    */
	public String queryCompile( String query ){
		return query.replaceAll("\\#[a-zA-Z0-9 ,_-]*#", "?");
	}	
		
}
