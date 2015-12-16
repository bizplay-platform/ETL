package bizplay.etl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class DateUtil {
	public static SimpleDateFormat	SDF = null;
	public static Calendar			CAL	= null; 
	
	/**
	 * 
	 * @param format
	 * @param l
	 * @return
	 */
	public static String getDate(String format , Locale l){
		CAL = Calendar.getInstance ();
		SDF = new SimpleDateFormat(format , l);
		return SDF.format(CAL.getTime());
	}
	
	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String getDate(String format){
		CAL = Calendar.getInstance ();
		SDF = new SimpleDateFormat(format);
		return SDF.format(CAL.getTime());
	}
	
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date , String format){
		CAL = Calendar.getInstance ();
		SDF = new SimpleDateFormat(format);
		CAL.setTime( date );
		return SDF.format(CAL.getTime());
	}
	
	/**
	 * 
	 * @param format
	 * @param iAmount
	 * @return
	 */
	public static String getAddDate(String format , int iAmount){
		CAL = Calendar.getInstance ();
		SDF = new SimpleDateFormat(format);
		CAL.setTime(new Date()         );
		CAL.add    ( Calendar.DATE, iAmount );
		return SDF.format(CAL.getTime());
	}
	
	/**
	 * 
	 * @param format
	 * @param iAmount
	 * @return
	 */
	public static String getAddMonth(String format , int iAmount){
		return "";
	}
	
	/**
	 * 
	 * @param format
	 * @param iAmount
	 * @return
	 */
	public static String getAddYear(String format , int iAmount){
		return "";
	}			
}
