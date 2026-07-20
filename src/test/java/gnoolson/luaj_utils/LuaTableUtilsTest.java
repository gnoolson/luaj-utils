package gnoolson.luaj_utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;


class LuaTableUtilsTest {

    @Test
    public void merge(){
        LuaTable source = new LuaTable();
        source.set("key_1", LuaString.valueOf("value_1"));
        source.set("key_2", LuaString.valueOf("value_2"));

        LuaTable dist = new LuaTable();
        source.set("key_3", LuaString.valueOf("value_3"));

        LuaTableUtils.merge(source, dist);

        LuaTable expected = new LuaTable();
        expected.set("key_1", LuaString.valueOf("value_1"));
        expected.set("key_2", LuaString.valueOf("value_2"));
        expected.set("key_3", LuaString.valueOf("value_3"));


        Assertions.assertAll("match",
                ()->{
                    Assertions.assertEquals(expected.get("key_1").checkjstring(), dist.get("key_1").checkjstring());
                },
                ()->{
                    Assertions.assertEquals(expected.get("key_2").checkjstring(), dist.get("key_2").checkjstring());
                },
                ()->{
                    Assertions.assertEquals(expected.get("key_3").checkjstring(), dist.get("key_3").checkjstring());
                }
        );
    }

    @Test
    public void clear(){

        LuaTable table = new LuaTable();
        table.set("key_1", LuaString.valueOf("value_1"));
        table.set("key_2", LuaString.valueOf("value_2"));
        table.set("key_3", LuaString.valueOf("value_3"));

        LuaTableUtils.clear(table);

        Assertions.assertAll("not found",
                ()->{
                    Assertions.assertEquals(LuaValue.NIL, table.get("key_1"));
                },
                ()->{
                    Assertions.assertEquals(LuaValue.NIL, table.get("key_2"));
                },
                ()->{
                    Assertions.assertEquals(LuaValue.NIL, table.get("key_3"));
                }
        );
    }

}