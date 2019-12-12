package cn.sjtu.project.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
    public static String readFromInputStream(InputStream is) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while (null != (line = bf.readLine())) {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
