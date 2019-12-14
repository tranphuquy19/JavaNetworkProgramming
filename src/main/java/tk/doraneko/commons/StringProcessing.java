package tk.doraneko.commons;

/**
 * @author tranphuquy19@gmail.com
 * @since 14/12/2019
 */
public class StringProcessing {
    public static String convertToLowercase(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z')
                result += (char) (s.charAt(i) + 32);
            else result += c;
        }
        return result;
    }

    public static String convertToUppercase(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z')
                result += (char) (s.charAt(i) - 32);
            else result += c;
        }
        return result;
    }

    public static String convertToTogglecase(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z')
                result += (char) (s.charAt(i) - 32);
            else if (c >= 'A' && c <= 'Z')
                result += (char) (s.charAt(i) + 32);
            else result += c;
        }
        return result;
    }

    public static String reverstString(String s){
        String result = "";
        for(int i = s.length() - 1; i >= 0; i--){
            result += s.charAt(i);
        }
        return result;
    }

    public static void main(String[] args) {
        String strTest = "AaBbCc123";
        System.out.println(convertToUppercase(strTest));
        System.out.println(convertToLowercase(strTest));
        System.out.println(convertToTogglecase(strTest));
        System.out.println(reverstString(strTest));
    }
}
