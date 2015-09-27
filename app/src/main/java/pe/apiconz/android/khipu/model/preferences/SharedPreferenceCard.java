package pe.apiconz.android.khipu.model.preferences;

import java.util.HashMap;
import java.util.Map;

import pe.apiconz.android.khipu.commons.MapUtil;

/**
 * Created by Brian on 19/08/2015.
 */
public class SharedPreferenceCard extends SharedPreferenceBase {

    private static final String PREF_NAME = "name";
    private static final String PREF_MAP = "map";

    //region Name
    public static String getName() {
        return getPrefs().getString(PREF_NAME, "");
    }

    public static void setName(String name) {
        setString(PREF_NAME, name);
    }

    public static void setName() {
        setName("");
    }

    public static boolean isEmptyName() {

        return getName().isEmpty();
    }
    //endregion

    //region Map
    public static String getMap() {
        return getPrefs().getString(PREF_MAP, "");
    }

    public static boolean isEmptyMap() {
        return getMap().isEmpty();
    }

    public static Map<String, String> getHashMap() {
        Map<String, String> map = new HashMap<>();
        if (!isEmptyMap())
            map = MapUtil.sortMap(MapUtil.stringToMap(getMap()));
        return map;
    }

    public static void setHashMap(Map<String, String> map) {
        setString(PREF_MAP, MapUtil.mapToString(map));
    }

}
