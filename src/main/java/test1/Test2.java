package test1;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class Test2 {
    private static boolean wildMatch(String pattern, String str) {
        pattern = toJavaPattern(pattern);
        return java.util.regex.Pattern.matches(pattern, str);
    }

    private static String toJavaPattern(String pattern) {
        String result = "^";
        char metachar[] = { '$', '^', '[', ']', '(', ')', '{', '|', '*', '+', '?', '.', '/' };
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            boolean isMeta = false;
            for (int j = 0; j < metachar.length; j++) {
                if (ch == metachar[j]) {
                    result += "/" + ch;
                    isMeta = true;
                    break;
                }
            }
            if (!isMeta) {
                if (ch == '*') {
                    result += ".*";
                } else {
                    result += ch;
                }

            }
        }
        result += "$";
        return result;
    }
    
    public static void main(String[] args) {
//          test("abc*", "abcd");
//          test("*", "toto");
//          test("toto.java", "tutu.java");
//          test("12345", "1234");
//          test("1234", "12345");
//          test("*f", "");
//          test("***", "toto");
//          test("*.java", "toto.");
//          test("*.java", "toto.jav");
//          test("*.java", "toto.java");
//          test("abc*", "");
//          test("a*c", "abbbbbccccc");
//          test("abc*xyz", "abcxxxyz");
//          test("*xyz", "abcxxxyz");
//          test("abc**xyz", "abcxxxyz");
//          test("abc**x", "abcxxx");
//          test("*a*b*c**x", "aaabcxxx");
//          test("abc*x*yz", "abcxxxyz");
//          test("abc*x*yz*", "abcxxxyz");
//          test("a*b*c*x*yf*z*", "aabbccxxxeeyffz");
//          test("a*b*c*x*yf*zze", "aabbccxxxeeyffz");
//          test("a*b*c*x*yf*ze", "aabbccxxxeeyffz");
//          test("a*b*c*x*yf*ze", "aabbccxxxeeyfze");
//          test("*LogServerInterface*.java", "_LogServerInterfaceImpl.java");
//          test("abc*xyz", "abcxyxyz");
        
        
        PathMatcher matcher = new AntPathMatcher();  
        // 完全路径url方式路径匹配  
         String requestPath="/user/list.htm?username=aaa&departmentid=2&pageNumber=1&pageSize=20";//请求路径  
         String patternPath="/user/list.htm**";//路径匹配模式  
         
         
         String requestPath2="/user/list.htm";//请求路径  
        // 不完整路径uri方式路径匹配  
        // String requestPath="/app/pub/login.do";//请求路径  
        // String patternPath="/**/login.do";//路径匹配模式  
        // 模糊路径方式匹配  
        // String requestPath="/app/pub/login.do";//请求路径  
        // String patternPath="/**/*.do";//路径匹配模式  
        // 包含模糊单字符路径匹配  
        //String requestPath = "/app/pub/login.do";// 请求路径  
        //String patternPath = "/**/lo?in.do";// 路径匹配模式  
        boolean result = matcher.match(patternPath, requestPath);
        System.out.println(result);
        result = matcher.match(patternPath, requestPath2);
        System.out.println(result);
        
        result = matcher.match(patternPath, requestPath);
        System.out.println(result);
 
        
    }

    private static void test(String pattern, String str) {
        System.out.println(pattern+" " + str + " =>> " + wildMatch(pattern, str));        
    }
}
