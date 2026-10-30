package gnoolson.luaj_utils;

import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.*;

import java.util.*;

class LuaTypeConverterTest {

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

        Assertions.assertEquals(collection1, LuaTypeConverter.toObject(LuaTypeConverter.toLua(collection1))); // <<<
        Assertions.assertEquals(map1, LuaTypeConverter.toObject(LuaTypeConverter.toLua(map1))); // <<<
        Assertions.assertEquals(Arrays.asList(objects), LuaTypeConverter.toObject(LuaTypeConverter.toLua(objects))); // <<<
    }

    @Test
    public void toLua() {
        Assertions.assertEquals(LuaValue.valueOf("str"), LuaTypeConverter.toLua("str"));
        Assertions.assertEquals(LuaValue.valueOf(12345), LuaTypeConverter.toLua(12345));
        Assertions.assertEquals(LuaValue.valueOf(3.14), LuaTypeConverter.toLua(3.14));
        Assertions.assertEquals(LuaValue.valueOf(true), LuaTypeConverter.toLua(true));
        Assertions.assertEquals(LuaValue.NIL, LuaTypeConverter.toLua(null));

        {
            LuaTable expected = new LuaTable();
            expected.set(1, LuaInteger.valueOf("str"));
            expected.set(2, LuaDouble.valueOf(999));
            expected.set(3, LuaString.valueOf(3.14));
            LuaValue actual = LuaTypeConverter.toLua(Arrays.asList("str", 999, 3.14));
            Assertions.assertTrue(LuaTableUtils.deepEquals(expected, (LuaTable) actual));
        }

        {
            LuaTable expected = new LuaTable();
            expected.set("key1", LuaInteger.valueOf("str"));
            expected.set("key2", LuaDouble.valueOf(999));
            expected.set("key3", LuaString.valueOf(3.14));
            LuaValue actual = LuaTypeConverter.toLua(new HashMap<String, Object>() {{
                put("key1", "str");
                put("key2", 999);
                put("key3", 3.14);
            }});
            Assertions.assertTrue(LuaTableUtils.deepEquals(expected, (LuaTable) actual));
        }

        {
            LuaTable expected = new LuaTable();
            expected.set(1, LuaValue.valueOf("string_1"));
            expected.set(2, LuaValue.valueOf("string_2"));
            expected.set(3, LuaValue.valueOf("string_3"));
            expected.set(4, LuaValue.valueOf("string_4"));
            expected.set(5, LuaValue.valueOf("string_5"));

            LuaValue actual = LuaTypeConverter.toLua(new ArrayList<String>() {{
                add("string_1");
                add("string_2");
                add("string_3");
                add("string_4");
                add("string_5");
            }});
            Assertions.assertTrue(LuaTableUtils.deepEquals(expected, (LuaTable) actual));
        }
    }

    @Test
    public void pojo() {
        VO vo = new VO(UUID.fromString("375d04bf-f356-47bf-9348-2ae8a1a39e54"));

        LuaValue luaValue = LuaTypeConverter.toLua(vo);
        Object actual = LuaTypeConverter.toObject(luaValue);

        Assertions.assertEquals(vo.getValue().toString(), ((Map<?, ?>) actual).get("value"));
    }

    @Test
    public void toStrings() {

        @Data
        class Pojo {
            int i = 111;
            double d = 3.14;
            String str = "STR";
        }

        Varargs varargs = LuaValue.varargsOf(new LuaValue[]{
                LuaValue.valueOf("str"),
                LuaValue.valueOf(111),
                LuaValue.valueOf(3.14),
                LuaTypeConverter.toLua(new Pojo())
        });

        String[] strings = LuaTypeConverter.toStrings(varargs, 1);

        Assertions.assertArrayEquals(
                new String[]{
                        "str",
                        "111",
                        "3.14",
                        "{\n  \"i\" : 111,\n  \"str\" : \"STR\",\n  \"d\" : 3.14\n}"
                },
                strings
        );
    }

    @Test
    public void map_to_table(){
        Map<String, Object> map = new HashMap<>();
        map.put(null, 3.14);
        map.put("key_1", "string_value");
        map.put("key_2", 2);
        map.put("key_3", new ArrayList(){{add(1); add(2); add(3); }});

        LuaValue table = LuaTypeConverter.toLua(map);

        Assertions.assertEquals(LuaValue.valueOf(3.14), table.get(LuaTypeConverter.LUA_VALUE_JAVA_NULL_KEY));
        Assertions.assertEquals(LuaValue.valueOf(3.14), table.get(LuaValue.valueOf("__java_null_key__")));
        Assertions.assertEquals(LuaValue.valueOf("string_value"), table.get("key_1"));
        Assertions.assertEquals(LuaValue.valueOf(2), table.get("key_2"));

        LuaTable list = new LuaTable(){{
            set(1, LuaValue.valueOf(1));
            set(2, LuaValue.valueOf(2));
            set(3, LuaValue.valueOf(3));
        }};
        Assertions.assertTrue(LuaTableUtils.deepEquals(list, (LuaTable) table.get("key_3")));
    }

    /*
     *
     *
     * */
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class VO {
        UUID value;
    }

}