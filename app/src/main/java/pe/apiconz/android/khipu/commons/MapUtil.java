package pe.apiconz.android.khipu.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Brian on 19/08/2015.
 */
public class MapUtil {

    public static String mapToString(Map<String, String> map) {

        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }

    public static Map<String, String> stringToMap(String input) {

        Map<String, String> map = new HashMap<>();

        String[] nameValuePairs = input.split("&");
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                map.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
                        nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return map;
    }

    public static Map<String, String> sortMap(Map<String, String> unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        Collections.sort(list, (lhs, rhs) ->

                ((Comparable) ((Map.Entry) lhs).getValue()).compareTo(((Map.Entry) rhs).getValue()));

        Map sortedMap = new LinkedHashMap();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
