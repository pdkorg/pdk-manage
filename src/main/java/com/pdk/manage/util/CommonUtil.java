package com.pdk.manage.util;

import com.pdk.manage.model.IBaseVO;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubo on 2015/8/25
 */
public class CommonUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatDate() {
        return formatDate(new Date());
    }

    public static Date toDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getRealPath(String resource) {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getRealPath(resource);
    }

    public static <T extends IBaseVO> Map<String, T> toMap(Collection<T> c) {

        Map<String, T> result = new HashMap<>();

        for (T t : c) {
            result.put(t.getId(), t);
        }

        return result;

    }

    public static boolean pdkBoolean(String booleanField) {
        return booleanField != null && "Y".equals(booleanField);
    }

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static String getContextPath() {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getContextPath();
    }

    public static String getResourceToken() {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getInitParameter("resource_token");
    }

    public static String getResourcePath() {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getInitParameter("resource_path");
    }

    public static String getMsgServerPath() {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getInitParameter("msg_server");
    }

    public static String getChatWebSocketPath() {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getInitParameter("chat_cs_websocket");
    }

    public static String getResourceFileUploadPath() {
        return getResourcePath() + "/file/upload";
    }
}
