package com.codeleven.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.yourkit.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorLogUtil {
    public static boolean hasError(String logFile) {
        return !returnPrettyError(logFile).isEmpty();
    }

    public static String returnPrettyError(String logFile) {
        File file = new File(logFile);
        try {
            String content = FileUtil.readFileContentAsUtf8(file);
            Pattern pattern = Pattern.compile("\\[(WARN|INFO|EXCEPTION|ERROR|NOTICE)\\].*", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(content);
            StringBuilder result = new StringBuilder();
            if (matcher.find()) {
                for (int i = 0; i < matcher.groupCount(); i++) {
                    result.append(matcher.group(i));
                }
            }
            return result.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void deleteFile(String logFile) {
        File file = new File(logFile);
        file.delete();
    }

    public static void reportError(String groupId, String title, String error, NotificationType errorLevel) {
        Notifications.Bus.notify(new Notification(groupId,
                title,
                error,
                errorLevel));
    }
}
