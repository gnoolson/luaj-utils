package gnoolson.luaj_utils;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

// +
public class LuaTableUtils {

    // +
    public static void merge(LuaTable source, LuaTable dist){
        for (LuaValue key : source.keys()) {
            dist.set(key, source.get(key));
        }
    }

    // +
    public static void clear(LuaTable luaTable) {
        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs n = luaTable.next(k);
            k = n.arg1();
            if (k.isnil())
                break;

            luaTable.set(k, LuaValue.NIL);
        }
    }

    public static boolean deepEquals(LuaTable luaTable1, LuaTable luaTable2) {
        if (luaTable1 == luaTable2)
            return true;

        if (luaTable1 == null || luaTable2 == null)
            return false;

        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs n = luaTable1.next(k);
            k = n.arg1();
            if (k.isnil())
                break;

            LuaValue v1 = n.arg(2);
            LuaValue v2 = luaTable2.get(k);

            if (!valueEquals(v1, v2))
                return false;
        }

        k = LuaValue.NIL;
        while (true) {
            Varargs n = luaTable2.next(k);
            k = n.arg1();
            if (k.isnil())
                break;

            LuaValue v2 = n.arg(2);
            if (!valueEquals(luaTable1.get(k), v2))
                return false;
        }

        return true;
    }

    /*
    *
    *
    * */
    private static boolean valueEquals(LuaValue luaValue1, LuaValue luaValue2) {
        if (luaValue1 == null)
            luaValue1 = LuaValue.NIL;

        if (luaValue2 == null)
            luaValue2 = LuaValue.NIL;

        if (luaValue1.eq_b(luaValue2))
            return true;

        if (luaValue1.istable() && luaValue2.istable())
            return deepEquals((LuaTable) luaValue1, (LuaTable) luaValue2);

        return false;
    }

}
