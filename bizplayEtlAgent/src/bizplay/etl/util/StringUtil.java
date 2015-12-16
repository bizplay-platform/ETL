package bizplay.etl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class StringUtil{

	
	
	   public StringUtil()
	    { 
	    }
	   
		/**
		* 금액 포맷 지정
		* @param  saup : xxxxxxxx
		* @return String : xxx,xxx,xxx
		*/
		public static String getAmtForm(String saup){
			DecimalFormat formatter = new DecimalFormat("##,###,###,###,###");	//금액 포맷형식 지정
			String str = nvl(saup,"");
			if(saup == null || saup.trim().equals("")){
				str = "";
			}else{
				str = formatter.format(Long.parseLong(str));					
			}
			return str;
		}
		
		/**
		* 주민/사업자 번호 포맷
		* @param  saup : xxxxxxxxxxxxx
		* @return String : 999999xxxxxxx
		*/
		public static String getRegNoForm(String saup){		
			String str = nvl(saup,"");		
			if(saup == null || saup.trim().equals("")){
				str = "";
			}else{
				if(str.length() == 13)
				{
					str = str.substring(0,6)+"-XXXXXXX";
				}
				else if(str.length() == 10)
				{
					str = saup.substring(0,3)+"-"+saup.substring(3,5)+"-"+saup.substring(5);
				}
			}
			return str;
		}
		
		/**
		* 사용자 이름 포맷
		* @param  saup : xxxxxxxxxxxxx
		* @return String : 7자이상...처리
		*/
		public static String getNameForm(String saup){		
			String str = nvl(saup,"");		
			if(saup == null || saup.trim().equals("")){
				str = "";
			}else{
				if(str.length() > 6)
				{
					str = str.substring(0,6)+"..";
				}							
			}
			return str;
		}
		
		/**
		* 날짜 포맷 지정
		* @param  saup : xxxxxxxx
		* @return String : xxxx-xx-xx
		*/
		public static String getDateFormKor(String saup){		
			String str = nvl(saup,"");
			if(saup == null || saup.trim().equals("")){
				str = "";
			}else{
				if(saup.trim().length() == 6){
					str = saup.substring(2,4)+"/"+saup.substring(4,6);
				}
				else if(saup.trim().length() == 8){
					str = saup.substring(0,4)+"/"+saup.substring(4,6)+"/"+saup.substring(6);
				}				
			}		
			return str;
		}	   
		
		
		/**
		* 날짜 포맷 지정
		* @param  saup : xxxxxxxx
		* @return String : xxxx-xx-xx
		*/
		public static String getDateForm(String saup){		
			String str = nvl(saup,"");
			if(saup == null || saup.trim().equals("")){
				str = "";
			}else{
				if(saup.trim().length() == 8){
					str = saup.substring(0,4)+"-"+saup.substring(4,6)+"-"+saup.substring(6);
				}
			}		
			return str;
		}
		
		
	   
		/**
		 * 반각문자를 전각 문자로 변환한다.
		 * @param str
		 * @return 전각변한 문자
		 */
		public static String toFullChar(String src) {
			if (src == null)
				return "";
			
			// 변환된 문자들을 쌓아놓을 StringBuffer 를 마련한다
			StringBuffer strBuf = new StringBuffer();
			char c	= 0;
			for (int i = 0; i < src.length(); i++) {
				c = src.charAt(i);

				//영문이거나 특수 문자 일경우.
				if (c >= 0x21 && c <= 0x7e) {
					c += 0xfee0;
				} else if (c == 0x20) {
					c = 0x3000;
				}

				// 문자열 버퍼에 변환된 문자를 쌓는다
				strBuf.append(c);
			}
			
			return strBuf.toString();
		}
		
		/**
		 * 전각문자를 반각 문자로 변경한다.
		 * @param str
		 * @return 반각변한 문자
		 */
		public static String toHalfChar(String src) {
			StringBuffer strBuf	= new StringBuffer();
			char c	= 0;
			for (int i = 0; i < src.length(); i++) {
				c = src.charAt(i);

				//영문이거나 특수 문자 일경우.
				if (c >= 0xff01 && c <= 0xff5e) {
					c -= 0xfee0;
				} else if (c == 0x3000) {
					c = 0x20;
				}

				// 문자열 버퍼에 변환된 문자를 쌓는다
				strBuf.append(c);
			}
			
			return strBuf.toString();
		}		

	    public static String subString(String str, int offset, int leng)
	    {
	        return new String(str.getBytes(), offset - 1, leng);
	    }

	    public static String subString(String str, int offset)
	    {
	        byte bytes[] = str.getBytes();
	        int size = bytes.length - (offset - 1);
	        return new String(bytes, offset - 1, size);
	    }

	    public static String fitString(String str, int size)
	    {
	        return fitString(str, size, 2);
	    }

	    public static String fitString(String str, int size, int align)
	    {
	        byte bts[] = str.getBytes();
	        int len = bts.length;
	        if(len == size)
	            return str;
	        if(len > size)
	        {
	            String s = new String(bts, 0, size);
	            if(s.length() == 0)
	            {
	                StringBuffer sb = new StringBuffer(size);
	                for(int idx = 0; idx < size; idx++)
	                    sb.append("?");

	                s = sb.toString();
	            }
	            return s;
	        }
	        if(len < size)
	        {
	            int cnt = size - len;
	            char values[] = new char[cnt];
	            for(int idx = 0; idx < cnt; idx++)
	                values[idx] = ' ';

	            if(align == 1)
	                return (new StringBuilder(String.valueOf(String.copyValueOf(values)))).append(str).toString();
	            else
	                return (new StringBuilder(String.valueOf(str))).append(String.copyValueOf(values)).toString();
	        } else
	        {
	            return str;
	        }
	    }

	    public static String[] toStringArray(String str)
	    {
	        List list = new ArrayList();
	        for(StringTokenizer st = new StringTokenizer(str); st.hasMoreTokens(); list.add(st.nextToken()));
	        return toStringArray(list);
	    }

	    public static String[] toStringArray(List list)
	    {
	        String strings[] = new String[list.size()];
	        for(int idx = 0; idx < list.size(); idx++)
	            strings[idx] = list.get(idx).toString();

	        return strings;
	    }

	    public static String replace(String src, String from, String to)
	    {
	        if(src == null)
	            return null;
	        if(from == null)
	            return src;
	        if(to == null)
	            to = "";
	        StringBuffer buf = new StringBuffer();
	        int pos;
	        while((pos = src.indexOf(from)) >= 0) 
	        {
	            buf.append(src.substring(0, pos));
	            buf.append(to);
	            src = src.substring(pos + from.length());
	        }
	        buf.append(src);
	        return buf.toString();
	    }

	    public static String cutString(String str, int limit)
	    {
	        if(str == null || limit < 4)
	            return str;
	        int len = str.length();
	        int cnt = 0;
	        int index;
	        for(index = 0; index < len && cnt < limit;)
	            if(str.charAt(index++) < '\u0100')
	                cnt++;
	            else
	                cnt += 2;

	        if(index < len)
	            str = (new StringBuilder(String.valueOf(str.substring(0, index - 1)))).append("...").toString();
	        return str;
	    }

	    public static String cutEndString(String src, String end)
	    {
	        if(src == null)
	            return null;
	        if(end == null)
	            return src;
	        int pos = src.indexOf(end);
	        if(pos >= 0)
	            src = src.substring(0, pos);
	        return src;
	    }

	    public static char[] makeCharArray(char c, int cnt)
	    {
	        char a[] = new char[cnt];
	        Arrays.fill(a, c);
	        return a;
	    }

	    public static String getString(char c, int cnt)
	    {
	        return new String(makeCharArray(c, cnt));
	    }

	    public static String getLeftTrim(String lstr)
	    {
	        if(!lstr.equals(""))
	        {
	            int strlen = 0;
	            int cptr = 0;
	            boolean lpflag = true;
	            strlen = lstr.length();
	            cptr = 0;
	            lpflag = true;
	            do
	            {
	                char chk = lstr.charAt(cptr);
	                if(chk != ' ')
	                    lpflag = false;
	                else
	                if(cptr == strlen)
	                    lpflag = false;
	                else
	                    cptr++;
	            } while(lpflag);
	            if(cptr > 0)
	                lstr = lstr.substring(cptr, strlen);
	        }
	        return lstr;
	    }

	    public static String getRightTrim(String lstr)
	    {
	        if(!lstr.equals(""))
	        {
	            int strlen = 0;
	            int cptr = 0;
	            boolean lpflag = true;
	            strlen = lstr.length();
	            cptr = strlen;
	            lpflag = true;
	            do
	            {
	                char chk = lstr.charAt(cptr - 1);
	                if(chk != ' ')
	                    lpflag = false;
	                else
	                if(cptr == 0)
	                    lpflag = false;
	                else
	                    cptr--;
	            } while(lpflag);
	            if(cptr < strlen)
	                lstr = lstr.substring(0, cptr);
	        }
	        return lstr;
	    }

	    public static String getLeft(String str, int len)
	    {
	        if(str == null)
	            return "";
	        else
	            return str.substring(0, len);
	    }

	    public static String getRight(String str, int len)
	    {
	        if(str == null)
	            return "";
	        String dest = "";
	        for(int i = str.length() - 1; i >= 0; i--)
	            dest = (new StringBuilder(String.valueOf(dest))).append(str.charAt(i)).toString();

	        str = dest;
	        str = str.substring(0, len);
	        dest = "";
	        for(int i = str.length() - 1; i >= 0; i--)
	            dest = (new StringBuilder(String.valueOf(dest))).append(str.charAt(i)).toString();

	        return dest;
	    }

	    public static String nvl(String str, String replace)
	    {
	        if(str == null)
	            return replace;
	        else
	            return str;
	    }

	    public static String checkEmpty(String str, String replace)
	    {
	        if(str == null || str.equals(""))
	            return replace;
	        else
	            return str;
	    }

	    public static String capitalize(String str)
	    {
	        int strLen;
	        if(str == null || (strLen = str.length()) == 0)
	            return str;
	        else
	            return (new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	    }

	    public static String getErrorTrace(Exception e)
	    {
	        if(e == null)
	        {
	            return "";
	        } else
	        {
	            StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            String errTrace = sw.toString();
	            return errTrace;
	        }
	    }

	    public static String escapeXml(String s)
	    {
	        if(s == null)
	            return "";
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < s.length(); i++)
	        {
	            char c = s.charAt(i);
	            if(c == '&')
	                sb.append("&amp;");
	            else
	                sb.append(c);
	        }

	        return sb.toString();
	    }

	    public static List getTokenList(String s, String token)
	    {
	        if(s == null || s.equals(""))
	            return null;
	        List tokenList = new ArrayList();
	        for(StringTokenizer st = new StringTokenizer(s, token); st.hasMoreTokens(); tokenList.add(st.nextToken().trim()));
	        return tokenList;
	    }

	    public static int getTokenLength(String s, String token)
	    {
	        if(s == null)
	            return 0;
	        int len = 0;
	        for(StringTokenizer st = new StringTokenizer(s, token); st.hasMoreTokens();)
	            len++;

	        return len;
	    }

	    public static String getToken(int index, String s, String token)
	    {
	        if(s == null)
	            return "";
	        StringTokenizer st = new StringTokenizer(s, token);
	        StringBuffer sb = new StringBuffer("");
	        int i = 0;
	        do
	        {
	            if(!st.hasMoreTokens())
	                break;
	            if(index == i)
	            {
	                sb.append(st.nextToken());
	                break;
	            }
	            st.nextToken();
	            i++;
	        } while(true);
	        if(sb.toString().length() > 0)
	            return sb.toString().trim();
	        else
	            return "";
	    }

	    public static String getToken(int index, String s, String token, String nvl)
	    {
	        if(s == null)
	            return nvl;
	        StringTokenizer st = new StringTokenizer(s, token);
	        StringBuffer sb = new StringBuffer("");
	        int i = 0;
	        do
	        {
	            if(!st.hasMoreTokens())
	                break;
	            if(index == i)
	            {
	                sb.append(st.nextToken());
	                break;
	            }
	            st.nextToken();
	            i++;
	        } while(true);
	        if(sb.toString().length() > 0)
	            return sb.toString().trim();
	        else
	            return nvl;
	    }

	    public static void deleteStringBuffer(StringBuffer strBuf)
	    {
	        strBuf.delete(0, strBuf.length());
	    }

	    public static boolean isset(String str)
	    {
	        return str != null && str.length() > 0;
	    }

	    public static String collapse(String str, String characters, String replacement)
	    {
	        if(str == null)
	            return null;
	        StringBuffer newStr = new StringBuffer();
	        boolean prevCharMatched = false;
	        for(int i = 0; i < str.length(); i++)
	        {
	            char c = str.charAt(i);
	            if(characters.indexOf(c) != -1)
	            {
	                if(!prevCharMatched)
	                {
	                    prevCharMatched = true;
	                    newStr.append(replacement);
	                }
	            } else
	            {
	                prevCharMatched = false;
	                newStr.append(c);
	            }
	        }

	        return newStr.toString();
	    }

	    public static String getString(String str, int max)
	    {
	        byte temp[] = str.getBytes();
	        int count = 0;
	        int str_count;
	        for(str_count = 0; max > str_count && str_count != temp.length; str_count++)
	            if(temp[str_count] < 0)
	                count++;

	        if(count % 2 != 0)
	            str = new String(temp, 0, str_count - 1);
	        else
	            str = new String(temp, 0, str_count);
	        return str;
	    }

	    public static boolean checkUndefined(Object obj)
	    {
	        boolean result = false;
	        if(obj.toString().equals("Undefined"))
	            result = true;
	        else
	            result = false;
	        return result;
	    }

	    public static String dashedPdaNo(String pdaNo)
	    {
	        String firstPdaNo = "";
	        String secondPdaNo = "";
	        String thirdPdaNo = "";
	        if(pdaNo == null || pdaNo.trim().length() == 0)
	            return "";
	        if(pdaNo.trim().length() < 10 || 11 < pdaNo.trim().length())
	        {
	            return pdaNo;
	        } else
	        {
	            firstPdaNo = (new StringBuilder(String.valueOf(pdaNo.substring(0, 3)))).append("-").toString();
	            secondPdaNo = (new StringBuilder(String.valueOf(pdaNo.substring(3, pdaNo.length() - 4)))).append("-").toString();
	            thirdPdaNo = pdaNo.substring(pdaNo.length() - 4);
	            return (new StringBuilder(String.valueOf(firstPdaNo))).append(secondPdaNo).append(thirdPdaNo).toString();
	        }
	    }

	    public static String makeLikeValue(String value)
	    {
	        StringBuffer sb = new StringBuffer();
	        sb.append('%');
	        if(value != null)
	            sb.append(value);
	        sb.append('%');
	        return sb.toString();
	    }

	    public static boolean existsNonAscii(String src)
	    {
	        byte b[] = src.getBytes();
	        for(int i = 0; i < b.length; i++)
	            if(b[i] < 0)
	                return true;

	        return false;
	    }

	    public static String[] parseGuid(String input, String separator, int count)
	    {
	        StringTokenizer token = new StringTokenizer(input, separator);
	        if(token.countTokens() != count)
	            return null;
	        String outputs[] = new String[token.countTokens()];
	        int i = 0;
	        while(token.hasMoreElements()) 
	            outputs[i++] = token.nextToken();
	        return outputs;
	    }
	    
	    public static String getRandomString(int len){
	    	Random random = new Random();
	    	StringBuilder sb = new StringBuilder(len);
	    	for (int i = 0; i < len; i++) {
	    		sb.append((char) ('a' + random.nextInt('z' - 'a')));
	    	}
	    	return sb.toString();
	    }
	    

	    public static boolean isBlank(Object s)
	    {
	        return s == null || (s instanceof String) && s.toString().trim().length() < 1;
	    }

		public static String camelString(String prefix , Object obj) {
			String   strCamelString = prefix;
			String   strTemp        = (String)obj;
			String[] arrTemp        = strTemp.split("_");
			
			for(int i = 0; i < arrTemp.length; i++){
				strCamelString += arrTemp[i].substring(0,1) + arrTemp[i].substring(1).toLowerCase();
			}
			return strCamelString;
		}
		
	    public static String traceToString(Throwable e) {
	        StringBuffer sb = new StringBuffer();
		    if(e != null){
		        try {
		            sb.append(e.toString());
		            sb.append("\n");
		            StackTraceElement element[] = e.getStackTrace();
		            for (int idx = 0; idx < element.length; idx++) {
		                sb.append("\tat ");
		                sb.append(element[idx].toString());
		                sb.append("\n");
		            }
		        } catch (Exception ex) {
		            return e.toString();
		        }
	    	}
	        return sb.toString();
	    }
	    
	    
	    public static boolean inValue( String strArr[], String value) {
	    	boolean check = false;
	    	
	    	if(strArr.length > 0){
	    		for(int i = 0; i < strArr.length; i++){
	    			if( value.equals(strArr[i]) ){
	    				check = true;
	    			}
	    		}
	    	}
	    	
	    	return check;
	    }
	    
	    public static int inValue(String str, String character){
	    	int count = 0;
	        if(str == null)
	            return count;
	        
	        for(int i = 0; i < str.length(); i++){
	            char c = str.charAt(i);
	            if(character.indexOf(c) > -1){
	            	count++;
	            }
	        }
	        return count;
	    }
	    

		public static String[] makeArrayToString(String raw, int len) {
			if (raw==null) return null;
			String[] ary =null;
			try {
				// raw 의 byte
				byte[] rawBytes =raw.getBytes("UTF-8");
				int rawLength =rawBytes.length;
							
				if(rawLength >len){
					int aryLength =(rawLength/len) + (rawLength%len !=0 ? 1 : 0);
					ary =new String[aryLength];

					int endCharIndex =0;	// 문자열이 끝나는 위치
					String tmp;
					for(int i=0; i<aryLength; i++){
						
						if(i==(aryLength-1)){
							tmp = raw.substring( endCharIndex );
						} else {
								
							int useByteLength = 0;
							int rSize =0;
							for ( ; endCharIndex < raw.length(); endCharIndex++ ) {
							
								if ( raw.charAt( endCharIndex ) > 0x007F ){				
									System.out.println(raw.charAt( endCharIndex ));
									useByteLength += 3;
								} else{ 
									useByteLength++;
								}
								if ( useByteLength > len ){
									break;
								}
								rSize++;
							}	
							tmp = raw.substring( (endCharIndex-rSize), endCharIndex );
						}
						
						ary[i] =tmp;
					}
					
				} else {
					ary =new String[]{raw};
				}
					
			} catch(java.io.UnsupportedEncodingException e) {}
						
			return ary;
		}
	    
	    public static String null2void(String s)
	    {
	        return isBlank(s) ? "" : s;
	    }
		
	    public static String null2void(String s , String c){
	        return isBlank(s) ? c : s;
	    }
	    
	    public static final int RIGHT = 1;
	    public static final int LEFT = 2;
	
}
