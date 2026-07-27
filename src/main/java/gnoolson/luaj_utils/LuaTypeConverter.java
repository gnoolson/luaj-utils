package gnoolson.luaj_utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class LuaTypeConverter {

    public static Object toObject(LuaValue value) {
        if (value == null || value.isnil())
            return null;

        if (value.isboolean())
            return value.toboolean();

        if (value.isint())
            return value.toint();

        if (value.islong())
            return value.tolong();

        if (value.isnumber())
            return value.todouble();

        if (value.isstring())
            return value.tojstring();

        if (value.istable())
            return tableToJava(value.checktable());

        if (value.isuserdata())
            return value.touserdata();

        return value;
    }

    public static LuaValue toLua(Object object) {
        if (object == null)
            return LuaValue.NIL;

        if (object instanceof LuaValue)
            return (LuaValue) object;

        if (object instanceof Boolean)
            return LuaValue.valueOf((Boolean) object);

        if (object instanceof Byte)
            return LuaValue.valueOf(((Byte) object).intValue());

        if (object instanceof Short)
            return LuaValue.valueOf(((Short) object).intValue());

        if (object instanceof Integer)
            return LuaValue.valueOf((Integer) object);

        if (object instanceof Long)
            return LuaValue.valueOf((Long) object);

        if (object instanceof Float)
            return LuaValue.valueOf(((Float) object).doubleValue());

        if (object instanceof Double)
            return LuaValue.valueOf((Double) object);

        if (object instanceof BigInteger)
            return LuaValue.valueOf(((BigInteger) object).longValue());

        if (object instanceof BigDecimal)
            return LuaValue.valueOf(((BigDecimal) object).doubleValue());

        if (object instanceof Character)
            return LuaValue.valueOf(object.toString());

        if (object instanceof String)
            return LuaValue.valueOf((String) object);

        if (object instanceof byte[])
            return LuaValue.valueOf((byte[]) object);

        if (object instanceof Enum<?>)
            return LuaValue.valueOf(((Enum<?>) object).name());

        if (object instanceof Map<?, ?>)
            return mapToLua((Map<?, ?>) object);

        if (object instanceof Collection<?>)
            return collectionToLua((Collection<?>) object);

        if (object.getClass().isArray())
            return arrayToLua(object);

        Object json = JSON.toJSON(object);

        if (json instanceof JSONObject)
            return mapToLua((JSONObject) json);

        if (json instanceof JSONArray)
            return collectionToLua((JSONArray) json);

        return LuaValue.userdataOf(object);
    }

    /*
     *
     *
     * */
    private static Object tableToJava(LuaTable luaTable) {
        if (isArray(luaTable))
            return tableToList(luaTable);

        return tableToMap(luaTable);
    }

    private static List<Object> tableToList(LuaTable luaTable) {
        List<Object> list = new ArrayList<>();
        int n = luaTable.length();

        for (int i = 1; i <= n; i++)
            list.add(toObject(luaTable.get(i)));

        return list;
    }

    private static Map<String, Object> tableToMap(LuaTable table) {
        Map<String, Object> map = new LinkedHashMap<>();
        LuaValue key = LuaValue.NIL;

        while (true) {
            Varargs next = table.next(key);
            key = next.arg1();
            if (key.isnil())
                break;

            LuaValue value = next.arg(2);
            map.put(key.tojstring(), toObject(value));
        }

        return map;
    }

    private static boolean isArray(LuaTable luaTable) {
        int len = luaTable.length();
        int count = 0;
        LuaValue key = LuaValue.NIL;

        while (true) {
            Varargs next = luaTable.next(key);
            key = next.arg1();

            if (key.isnil())
                break;

            if (!key.isint())
                return false;

            int index = key.toint();

            if (index < 1 || index > len)
                return false;

            count++;
        }

        return count == len;
    }

    private static LuaTable mapToLua(Map<?, ?> map) {
        LuaTable luaTable = new LuaTable();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            LuaValue key = toLuaKey(entry.getKey());
            luaTable.set(key, toLua(entry.getValue()));
        }

        return luaTable;
    }

    private static LuaTable collectionToLua(Collection<?> collection) {
        LuaTable luaTable = new LuaTable();
        int index = 1;

        for (Object object : collection)
            luaTable.set(index++, toLua(object));

        return luaTable;
    }

    private static LuaTable arrayToLua(Object array) {
        LuaTable luaTable = new LuaTable();
        int length = Array.getLength(array);

        for (int i = 0; i < length; i++)
            luaTable.set(i + 1, toLua(Array.get(array, i)));

        return luaTable;
    }

    private static LuaValue toLuaKey(Object key) {
        if (key == null)
            throw new IllegalArgumentException("Map key cannot be null");

        if (key instanceof String)
            return LuaValue.valueOf((String) key);

        if (key instanceof Integer)
            return LuaValue.valueOf((Integer) key);

        if (key instanceof Long)
            return LuaValue.valueOf((Long) key);

        if (key instanceof Short)
            return LuaValue.valueOf(((Short) key).intValue());

        if (key instanceof Byte)
            return LuaValue.valueOf(((Byte) key).intValue());

        if (key instanceof Boolean)
            return LuaValue.valueOf((Boolean) key);

        if (key instanceof Character)
            return LuaValue.valueOf(key.toString());

        if (key instanceof Enum<?>)
            return LuaValue.valueOf(((Enum<?>) key).name());

        return LuaValue.valueOf(key.toString());
    }

    // +
    public static String[] toStrings(Varargs args, int startFrom) {
        if (startFrom > 1) {
            args = args.subargs(startFrom);
        }

        String[] strings = new String[args.narg()];
        for (int i = 1; i <= args.narg(); i++) {
            LuaValue arg = args.arg(i);

            if (arg.istable()) {
                Object object = LuaTypeConverter.toObject(arg.checktable());
                strings[i - 1] = JSON.toJSONString(object, true);
            } else {
                strings[i - 1] = arg.toString();
            }
        }

        return strings;
    }

}
