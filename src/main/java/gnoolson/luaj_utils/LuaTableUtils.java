package gnoolson.luaj_utils;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

// +
public class LuaTableUtils {

    public static void merge(LuaTable source, LuaTable dist){
        for (LuaValue key : source.keys()) {
            dist.set(key, source.get(key));
        }
    }

    public static void clear(LuaTable luaTable) {
        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs n = luaTable.next(k);
            k = n.arg1();
            if (k.isnil()) break;
            luaTable.set(k, LuaValue.NIL);
        }
    }

}
