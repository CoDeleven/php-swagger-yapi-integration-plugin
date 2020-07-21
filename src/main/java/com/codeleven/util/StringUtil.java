package com.codeleven.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {
    public static String readFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] bufs = new char[1024];
        int len = -1;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = bufferedReader.read(bufs)) != -1) {
            stringBuilder.append(bufs, 0, len);
        }
        return stringBuilder.toString();
    }
}
