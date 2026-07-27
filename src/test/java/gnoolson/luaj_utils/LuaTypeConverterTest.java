package gnoolson.luaj_utils;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.*;

import java.util.*;

class LuaTypeConverterTest {

    @Test
    public void isArray() {
        { // true
            LuaTable luaTable = new LuaTable();
            Assertions.assertTrue(LuaTypeConverter.isArray(luaTable));
        }
        { // true
            LuaTable luaTable = new LuaTable();
            luaTable.set(1, LuaValue.valueOf("str_1"));
            Assertions.assertTrue(LuaTypeConverter.isArray(luaTable));
        }
        { // true
            LuaTable luaTable = new LuaTable();
            luaTable.set(1, LuaValue.valueOf("str_1"));
            luaTable.set(2, LuaValue.valueOf("str_2"));
            Assertions.assertTrue(LuaTypeConverter.isArray(luaTable));
        }
        { // false
            LuaTable luaTable = new LuaTable();
            luaTable.set("key", LuaValue.valueOf("value"));
            Assertions.assertFalse(LuaTypeConverter.isArray(luaTable));
        }
    }

    @Test
    public void toObject() {
        boolean flag1 = Boolean.TRUE;
        int int1 = Integer.MAX_VALUE;
        long long1 = Long.MAX_VALUE;
        double double1 = Double.MAX_VALUE;
        String str1 = "_STRING_";
        Collection<Object> collection1 = new ArrayList<Object>() {{
            add("str_1");
            add("str_2");
        }};
        Map<String, Object> map1 = new HashMap<String, Object>() {{
            put("key_1", "str_1");
            put("key_2", "str_2");
            put("key_3", "str_3");
        }};
        Object _null = null;
        Object[] objects = new Object[]{"str_1", "str_2"};


        Assertions.assertEquals(flag1, LuaTypeConverter.toObject(LuaValue.valueOf(flag1)));
        Assertions.assertEquals(int1, LuaTypeConverter.toObject(LuaValue.valueOf(int1)));
        Assertions.assertEquals(long1, LuaTypeConverter.toObject(LuaValue.valueOf(long1)));
        Assertions.assertEquals(double1, LuaTypeConverter.toObject(LuaValue.valueOf(double1)));
        Assertions.assertEquals(str1, LuaTypeConverter.toObject(LuaValue.valueOf(str1)));
        Assertions.assertEquals(_null, LuaTypeConverter.toObject(LuaValue.NIL));

        Assertions.assertEquals(collection1, LuaTypeConverter.toObject(LuaTypeConverter.toLuaTable(collection1))); // <<<
        Assertions.assertEquals(map1, LuaTypeConverter.toObject(LuaTypeConverter.toLuaTable(map1))); // <<<
        Assertions.assertEquals(Arrays.asList(objects), LuaTypeConverter.toObject(LuaTypeConverter.toLuaTable(objects))); // <<<
    }

    @Test
    public void toMap() {
        LuaTable t = new LuaTable();
        t.set(1, LuaValue.valueOf(1122));
        t.set(2, LuaValue.valueOf(3344));

        LuaTable luaTable = new LuaTable();
        luaTable.set("key_1", LuaValue.valueOf("str"));
        luaTable.set("key_2", LuaValue.valueOf(1234));
        luaTable.set("key_3", LuaValue.valueOf(true));
        luaTable.set("key_4", LuaValue.valueOf(3.14));
        luaTable.set("key_5", LuaValue.valueOf(Long.MAX_VALUE));
        luaTable.set("key_6", LuaValue.NIL);
        luaTable.set("key_7", t);

        Map<String, Object> map = LuaTypeConverter.toMap(luaTable);

        Assertions.assertEquals("str", map.get("key_1"));
        Assertions.assertEquals(1234, map.get("key_2"));
        Assertions.assertEquals(true, map.get("key_3"));
        Assertions.assertEquals(3.14, map.get("key_4"));
        Assertions.assertEquals(Long.MAX_VALUE, map.get("key_5"));
        Assertions.assertEquals(null, map.get("key_6"));
        Assertions.assertEquals(new ArrayList<Integer>() {{
            add(1122);
            add(3344);
        }}, map.get("key_7"));

    }

    @Test
    public void toStrings() {
        Varargs varargs = LuaValue.varargsOf(new LuaValue[]{
                LuaValue.valueOf("str_1"),
                LuaValue.valueOf(1122),
                LuaValue.valueOf(3.14),
                LuaValue.valueOf(true),
        });

        String[] strings = LuaTypeConverter.toStrings(varargs, 1);

        Assertions.assertEquals(4, strings.length);
        Assertions.assertEquals("str_1", strings[0]);
        Assertions.assertEquals("1122", strings[1]);
        Assertions.assertEquals("3.14", strings[2]);
        Assertions.assertEquals("true", strings[3]);
    }

    @Test
    public void toLuaValue() {
        Assertions.assertEquals(LuaValue.valueOf("str"), LuaTypeConverter.toLuaValue("str"));
        Assertions.assertEquals(LuaValue.valueOf(12345), LuaTypeConverter.toLuaValue(12345));
        Assertions.assertEquals(LuaValue.valueOf(3.14), LuaTypeConverter.toLuaValue(3.14));

        @ToString
        @EqualsAndHashCode
        @RequiredArgsConstructor
        class VO {
            final UUID value;
        }

        Assertions.assertEquals(LuaValue.valueOf("VO(value=375d04bf-f356-47bf-9348-2ae8a1a39e54)"),LuaTypeConverter.toLuaValue(new VO(UUID.fromString("375d04bf-f356-47bf-9348-2ae8a1a39e54"))));
        Assertions.assertEquals(LuaValue.valueOf(true), LuaTypeConverter.toLuaValue(true));
        Assertions.assertEquals(LuaValue.NIL, LuaTypeConverter.toLuaValue(null));

        {
            LuaTable expected = new LuaTable();
            expected.set(1, LuaInteger.valueOf("str"));
            expected.set(2, LuaDouble.valueOf(999));
            expected.set(3, LuaString.valueOf(3.14));
            LuaValue actual = LuaTypeConverter.toLuaValue(Arrays.asList("str", 999, 3.14));
            Assertions.assertTrue(LuaTableUtils.deepEquals(expected, (LuaTable) actual));
        }

        {
            LuaTable expected = new LuaTable();
            expected.set("key1", LuaInteger.valueOf("str"));
            expected.set("key2", LuaDouble.valueOf(999));
            expected.set("key3", LuaString.valueOf(3.14));
            LuaValue actual = LuaTypeConverter.toLuaValue(new HashMap<String, Object>() {{
                put("key1", "str");
                put("key2", 999);
                put("key3", 3.14);
            }});
            Assertions.assertTrue(LuaTableUtils.deepEquals(expected, (LuaTable) actual));
        }
    }

}