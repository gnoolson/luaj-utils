package gnoolson.luaj_utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class LuaTypeConverter {

    public static final String JAVA_NULL_KEY = "__java_null_key__";
    public static final LuaValue LUA_VALUE_JAVA_NULL_KEY = LuaValue.valueOf(JAVA_NULL_KEY);
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

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

        JsonNode jsonNode = JSON_MAPPER.convertValue(object, JsonNode.class);

        if (jsonNode instanceof ObjectNode) {
            Map<String, Object> map = JSON_MAPPER.convertValue(jsonNode, new TypeReference<Map<String, Object>>() {
            });
            return mapToLua(map);
        }

        if (jsonNode instanceof ArrayNode) {
            Collection<?> collection = JSON_MAPPER.convertValue(jsonNode, new TypeReference<Collection<?>>() {
            });
            return collectionToLua(collection);
        }

        return LuaValue.userdataOf(object);
    }

    /*
     *
     *
     * */
    private static Object tableToJava(LuaTable luaTable) {
        if (LuaTableUtils.isArray(luaTable))
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
            return LUA_VALUE_JAVA_NULL_KEY;

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

        try {
            String[] strings = new String[args.narg()];
            for (int i = 1; i <= args.narg(); i++) {
                LuaValue arg = args.arg(i);

                if (arg.istable()) {
                    Object object = LuaTypeConverter.toObject(arg.checktable());
                    strings[i - 1] = JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                } else {
                    strings[i - 1] = arg.toString();
                }
            }

            return strings;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
