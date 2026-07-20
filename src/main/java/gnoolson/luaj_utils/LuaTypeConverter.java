package gnoolson.luaj_utils;

import com.alibaba.fastjson.JSON;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.*;

public class LuaTypeConverter {

    // +
    public static boolean isArray(LuaTable table) {
        int len = table.length();
        LuaValue k = LuaValue.NIL;

        while (true) {
            Varargs n = table.next(k);
            if ((k = n.arg1()).isnil())
                break;

            if (k.isinttype()) {
                int idx = k.toint();
                if (idx < 1 || idx > len)
                    return false;

            } else {
                return false;
            }
        }

        return true;
    }

    // +
    public static Object toObject(LuaValue value) {
        Object result = null;

        if (value.istable()) {
            LuaTable table = (LuaTable) value;

            boolean isArray = isArray(table);

            if (isArray) {
                List<Object> arr = new ArrayList<>();
                int len = table.length();

                for (int i = 1; i <= len; i++) {
                    arr.add(toObject(table.get(i)));
                }

                return arr;
            } else {
                Map<String, Object> obj = new HashMap<>();
                LuaValue k = LuaValue.NIL;

                while (true) {
                    Varargs n = table.next(k);
                    if ((k = n.arg1()).isnil()) break;
                    LuaValue v = n.arg(2);
                    obj.put(k.tojstring(), toObject(v));
                }

                return obj;
            }

        } else if (value.isint()) {
            result = value.toint();
        } else if (value.islong()) {
            result = value.tolong();
        } else if (value.isnumber()) {
            result = value.todouble();
        } else if (value.isboolean()) {
            result = value.toboolean();
        } else if (value.isstring()) {
            result = value.tojstring();
        } else if (value.isnil()) {
            result = null;
        }

        return result;
    }

    // +
    public static LuaTable toLuaTable(Map<String, Object> data) {
        LuaTable table = new LuaTable();

        for (String key : data.keySet()) {
            table.set(key, toLuaValue(data.get(key)));
        }

        return table;
    }

    // +
    public static LuaTable toLuaTable(Collection<Object> arr) {
        LuaTable table = new LuaTable();

        int index = 1;
        for (Object o : arr) {
            table.set(index, toLuaValue(o));
            index++;
        }

        return table;
    }

    // +
    public static LuaValue toLuaTable(Object[] objects) {
        return toLuaTable(Arrays.asList(objects));
    }

    // +
    public static LuaValue toLuaValue(Object value) {
        if (value == null) {
            return LuaValue.NIL;
        } else if (value instanceof Boolean) {
            return LuaValue.valueOf((Boolean) value);
        } else if (value instanceof Integer) {
            return LuaInteger.valueOf((Integer) value);
        } else if (value instanceof Number) {
            return LuaValue.valueOf(((Number) value).doubleValue());
        } else if (value instanceof String) {
            return LuaValue.valueOf((String) value);
        } else if (value instanceof Map) {
            return toLuaTable((Map) value);
        } else if (value instanceof Collection) {
            return toLuaTable((Collection) value);
        } else {
            throw new IllegalArgumentException(String.format("\"%s\" is not supported", value.getClass().getCanonicalName()));
        }
    }

    // +-
    public static Map<String, Object> toMap(LuaTable table) {
        LuaValue[] keys = table.keys();
        Map<String, Object> data = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            LuaValue key = keys[i];
            LuaValue luaValue = table.get(key);

            if (luaValue.istable()) {
                data.put(key.toString(), toObject(luaValue));
            } else if (luaValue.isboolean()) {
                data.put(key.toString(), luaValue.checkboolean());
            } else if (luaValue.isint()) {
                data.put(key.toString(), luaValue.checkint());
            } else if (luaValue.islong()) {
                data.put(key.toString(), luaValue.checklong());
            } else if (luaValue.isnumber()) {
                data.put(key.toString(), luaValue.checkdouble());
            } else if (luaValue.isnil()) {
                data.put(key.toString(), LuaValue.NIL);
            } else {
                data.put(key.toString(), luaValue.toString());
            }
        }

        return data;
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
                Map<String, Object> stringObjectMap = LuaTypeConverter.toMap(arg.checktable());
                strings[i - 1] = JSON.toJSONString(stringObjectMap, true);
            } else {
                strings[i - 1] = arg.toString();
            }
        }

        return strings;
    }

}
