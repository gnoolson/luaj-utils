package gnoolson.luaj_utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LuaArgUtilsTest {

    @Test
    void getLuaFunctionFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            ZeroArgFunction field1 = new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    return null;
                }
            };

            sourceTable.set("field1", field1);

            LuaFunction actualValue = LuaArgUtils.getLuaFunctionFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(field1, actualValue);
        }

        {// field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaFunctionFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // fail
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[0]));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaFunctionFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'function'", runtimeException.getMessage());
        }
    }

    @Test
    void getLuaTableFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            LuaTable field1 = new LuaTable();
            field1.set("key", LuaValue.valueOf("value"));

            sourceTable.set("field1", field1);

            LuaTable actualValue = LuaArgUtils.getLuaTableFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(field1, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaTableFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[0]));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaTableFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'table'", runtimeException.getMessage());
        }
    }

    @Test
    public void getByteArrayFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            byte[] data = new byte[]{0xF, 0xF};
            sourceTable.set("field1", new LuaUserdata(data));

            byte[] actualValue = LuaArgUtils.getByteArrayFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertArrayEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getByteArrayFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", LuaString.valueOf("STRING"));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getByteArrayFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"string\", expected 'userdata'", runtimeException.getMessage());
        }
    }

    @Test
    public void getStringFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            String data = "STRING";
            sourceTable.set("field1", LuaString.valueOf(data));

            String actualValue = LuaArgUtils.getStringFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getStringFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[]{}));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getStringFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'string'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLuaValueFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            LuaValue data = LuaNumber.valueOf(3.14);
            sourceTable.set("field1", data);

            LuaValue actualValue = LuaArgUtils.getLuaValueFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaValueFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }
    }

    @Test
    public void getIntFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            int data = Integer.MAX_VALUE;
            sourceTable.set("field1", LuaString.valueOf(data));

            int actualValue = LuaArgUtils.getIntFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getIntFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[]{}));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getIntFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'int'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLongFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            long data = Long.MAX_VALUE;
            sourceTable.set("field1", LuaString.valueOf(data));

            long actualValue = LuaArgUtils.getLongFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLongFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[]{}));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLongFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'long'", runtimeException.getMessage());
        }
    }

    @Test
    public void getDoubleFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            double data = Double.MAX_VALUE;
            sourceTable.set("field1", LuaString.valueOf(data));

            double actualValue = LuaArgUtils.getDoubleFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getDoubleFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[]{}));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getDoubleFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'number'", runtimeException.getMessage());
        }
    }

    @Test
    public void getBooleanFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            boolean data = Boolean.TRUE;
            sourceTable.set("field1", LuaString.valueOf(data));

            boolean actualValue = LuaArgUtils.getBooleanFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getBooleanFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[]{}));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getBooleanFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'boolean'", runtimeException.getMessage());
        }
    }

    @Test
    public void getObjectFromLuaTable() {
        { // ok
            LuaTable sourceTable = new LuaTable();

            List<String> data = new ArrayList<String>() {{
                add("str_1");
                add("str_2");
                add("str_3");
            }};
            sourceTable.set("field1", LuaTypeConverter.toLua(data));

            Object actualValue = LuaArgUtils.getObjectFromLuaTable(sourceTable, "sourceTable", "field1");
            Assertions.assertEquals(data, actualValue);
        }

        { // field is nil
            LuaTable sourceTable = new LuaTable();
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getObjectFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" was not found in LuaTable \"sourceTable\"", runtimeException.getMessage());
        }

        { // wrong type
            LuaTable sourceTable = new LuaTable();
            sourceTable.set("field1", new LuaUserdata(new byte[]{}));

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getObjectFromLuaTable(sourceTable, "sourceTable", "field1");
            });

            Assertions.assertEquals("Field \"field1\" in LuaTable \"sourceTable\" is \"userdata\", expected 'table'", runtimeException.getMessage());
        }
    }

    @Test
    public void getStringFromFunctionArgs() {
        { // ok
            String data = "STRING";
            LuaValue arg = LuaString.valueOf(data);
            String actualResult = LuaArgUtils.getStringFromFunctionArgs(arg, 1, "name1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getStringFromFunctionArgs(arg, 1, "name1");
            });
            Assertions.assertEquals("Argument \"name1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getStringFromFunctionArgs(arg, 1, "name1");
            });
            Assertions.assertEquals("Argument \"name1\" (1) is \"userdata\", expected 'string'", runtimeException.getMessage());
        }
    }


    @Test
    void getFunctionFromFunctionArgs() {
        { // ok
            LuaFunction function = new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    return null;
                }
            };

            LuaFunction actualResult = LuaArgUtils.getFunctionFromFunctionArgs(function, 1, "function1");
            Assertions.assertEquals(function, actualResult);
        }

        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getFunctionFromFunctionArgs(arg, 1, "function1");
            });
            Assertions.assertEquals("Argument \"function1\" (1) cannot be nil", runtimeException.getMessage());
        }

        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getFunctionFromFunctionArgs(arg, 1, "function1");
            });
            Assertions.assertEquals("Argument \"function1\" (1) is \"userdata\", expected 'function'", runtimeException.getMessage());
        }
    }

    @Test
    public void getBooleanFromFunctionArgs() {
        { // ok
            boolean data = Boolean.TRUE;
            LuaValue arg = LuaBoolean.valueOf(data);
            boolean actualResult = LuaArgUtils.getBooleanFromFunctionArgs(arg, 1, "flag1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getBooleanFromFunctionArgs(arg, 1, "flag1");
            });
            Assertions.assertEquals("Argument \"flag1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getBooleanFromFunctionArgs(arg, 1, "flag1");
            });
            Assertions.assertEquals("Argument \"flag1\" (1) is \"userdata\", expected 'boolean'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLuaValueFromFunctionVarargs() {
        LuaValue name1 = LuaString.valueOf("STRING");
        LuaValue flag1 = LuaBoolean.valueOf(true);
        LuaValue number1 = LuaInteger.valueOf(1122);

        Varargs varargs = LuaValue.varargsOf(new LuaValue[]{name1, flag1, number1});

        LuaValue actualResult1 = LuaArgUtils.getLuaValueFromFunctionVarargs(varargs, 1, "name1");
        Assertions.assertEquals(name1, actualResult1);

        LuaValue actualResult2 = LuaArgUtils.getLuaValueFromFunctionVarargs(varargs, 2, "flag1");
        Assertions.assertEquals(flag1, actualResult2);

        LuaValue actualResult3 = LuaArgUtils.getLuaValueFromFunctionVarargs(varargs, 3, "number1");
        Assertions.assertEquals(number1, actualResult3);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            LuaValue aVoid = LuaArgUtils.getLuaValueFromFunctionVarargs(varargs, 4, "void");
        });
        Assertions.assertEquals("Argument \"void\" (4) cannot be nil", runtimeException.getMessage());
    }

    @Test
    public void getDoubleFromFunctionArgs() {
        { // ok
            double data = Double.MAX_VALUE;
            LuaValue arg = LuaNumber.valueOf(data);
            double actualResult = LuaArgUtils.getDoubleFromFunctionArgs(arg, 1, "number1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getDoubleFromFunctionArgs(arg, 1, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getDoubleFromFunctionArgs(arg, 1, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" (1) is \"userdata\", expected 'number'", runtimeException.getMessage());
        }
    }

    @Test
    public void getIntFromFunctionArgs() {
        { // ok
            int data = Integer.MAX_VALUE;
            LuaValue arg = LuaNumber.valueOf(data);
            int actualResult = LuaArgUtils.getIntFromFunctionArgs(arg, 1, "number1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getIntFromFunctionArgs(arg, 1, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getIntFromFunctionArgs(arg, 1, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" (1) is \"userdata\", expected 'int'", runtimeException.getMessage());
        }
    }

    @Test
    public void getByteArrayFromFunctionArgs() {
        { // ok
            byte[] data = new byte[]{0xF, 0xF, 0xF, 0xF, 0xF};
            LuaValue arg = new LuaUserdata(data);
            byte[] actualResult = LuaArgUtils.getByteArrayFromFunctionArgs(arg, 1, "bytes1");
            Assertions.assertArrayEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getByteArrayFromFunctionArgs(arg, 1, "bytes1");
            });
            Assertions.assertEquals("Argument \"bytes1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = LuaBoolean.valueOf(false);
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getByteArrayFromFunctionArgs(arg, 1, "bytes1");
            });
            Assertions.assertEquals("Argument \"bytes1\" (1) is \"boolean\", expected 'userdata'", runtimeException.getMessage());
        }
    }

    @Test
    public void getObjectFromFunctionArgs() {
        { // ok
            List<String> data = new ArrayList<String>() {{
                add("str_1");
                add("str_2");
                add("str_3");
            }};
            LuaValue arg = LuaTypeConverter.toLua(data);

            Object actualResult = LuaArgUtils.getObjectFromFunctionArgs(arg, 1, "obj1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getObjectFromFunctionArgs(arg, 1, "obj1");
            });
            Assertions.assertEquals("Argument \"obj1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getObjectFromFunctionArgs(arg, 1, "obj1");
            });
            Assertions.assertEquals("Argument \"obj1\" (1) is \"userdata\", expected 'table'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLuaTableFromFunctionArgs() {
        { // ok
            LuaValue arg = new LuaTable();
            LuaTable actualResult = LuaArgUtils.getLuaTableFromFunctionArgs(arg, 1, "table1");
            Assertions.assertEquals(arg, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaTableFromFunctionArgs(arg, 1, "table1");
            });
            Assertions.assertEquals("Argument \"table1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaTableFromFunctionArgs(arg, 1, "table1");
            });
            Assertions.assertEquals("Argument \"table1\" (1) is \"userdata\", expected 'table'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLongFromFunctionArgs() {
        { // ok
            long data = Long.MAX_VALUE;
            LuaValue arg = LuaNumber.valueOf(data);
            long actualResult = LuaArgUtils.getLongFromFunctionArgs(arg, 1, "number1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLongFromFunctionArgs(arg, 1, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" (1) cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLongFromFunctionArgs(arg, 1, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" (1) is \"userdata\", expected 'long'", runtimeException.getMessage());
        }
    }

    @Test
    public void getStringFromLuaValue() {
        { // ok
            String data = "STRING";
            LuaValue arg = LuaString.valueOf(data);
            String actualResult = LuaArgUtils.getStringFromLuaValue(arg, "name1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getStringFromLuaValue(arg, "name1");
            });
            Assertions.assertEquals("Argument \"name1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getStringFromLuaValue(arg, "name1");
            });
            Assertions.assertEquals("Argument \"name1\" is \"userdata\", expected 'string'", runtimeException.getMessage());
        }
    }

    @Test
    public void getBooleanFromLuaValue() {
        { // ok
            boolean data = Boolean.TRUE;
            LuaValue arg = LuaBoolean.valueOf(data);
            boolean actualResult = LuaArgUtils.getBooleanFromLuaValue(arg, "flag1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getBooleanFromLuaValue(arg, "flag1");
            });
            Assertions.assertEquals("Argument \"flag1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getBooleanFromLuaValue(arg, "flag1");
            });
            Assertions.assertEquals("Argument \"flag1\" is \"userdata\", expected 'boolean'", runtimeException.getMessage());
        }
    }

    @Test
    public void getDoubleFromLuaValue() {
        { // ok
            double data = Double.MAX_VALUE;
            LuaValue arg = LuaNumber.valueOf(data);
            double actualResult = LuaArgUtils.getDoubleFromLuaValue(arg, "number1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getDoubleFromLuaValue(arg, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getDoubleFromLuaValue(arg, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" is \"userdata\", expected 'number'", runtimeException.getMessage());
        }
    }

    @Test
    public void getIntFromLuaValue() {
        { // ok
            int data = Integer.MAX_VALUE;
            LuaValue arg = LuaNumber.valueOf(data);
            int actualResult = LuaArgUtils.getIntFromLuaValue(arg, "number1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getIntFromLuaValue(arg, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getIntFromLuaValue(arg, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" is \"userdata\", expected 'int'", runtimeException.getMessage());
        }
    }

    @Test
    public void getByteArrayFromLuaValue() {
        { // ok
            byte[] data = new byte[]{0xF, 0xF, 0xF, 0xF, 0xF};
            LuaValue arg = new LuaUserdata(data);
            byte[] actualResult = LuaArgUtils.getByteArrayFromLuaValue(arg, "bytes1");
            Assertions.assertArrayEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getByteArrayFromLuaValue(arg, "bytes1");
            });
            Assertions.assertEquals("Argument \"bytes1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = LuaBoolean.valueOf(false);
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getByteArrayFromLuaValue(arg, "bytes1");
            });
            Assertions.assertEquals("Argument \"bytes1\" is \"boolean\", expected 'userdata'", runtimeException.getMessage());
        }
    }

    @Test
    public void getObjectFromLuaValue() {
        { // ok
            List<String> data = new ArrayList<String>() {{
                add("str_1");
                add("str_2");
                add("str_3");
            }};
            LuaValue arg = LuaTypeConverter.toLua(data);

            Object actualResult = LuaArgUtils.getObjectFromLuaValue(arg, "obj1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getObjectFromLuaValue(arg, "obj1");
            });
            Assertions.assertEquals("Argument \"obj1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getObjectFromLuaValue(arg, "obj1");
            });
            Assertions.assertEquals("Argument \"obj1\" is \"userdata\", expected 'table'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLuaTableFromLuaValue() {
        { // ok
            LuaValue arg = new LuaTable();
            LuaTable actualResult = LuaArgUtils.getLuaTableFromLuaValue(arg, "table1");
            Assertions.assertEquals(arg, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaTableFromLuaValue(arg, "table1");
            });
            Assertions.assertEquals("Argument \"table1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLuaTableFromLuaValue(arg, "table1");
            });
            Assertions.assertEquals("Argument \"table1\" is \"userdata\", expected 'table'", runtimeException.getMessage());
        }
    }

    @Test
    public void getLongFromLuaValue() {
        { // ok
            long data = Long.MAX_VALUE;
            LuaValue arg = LuaNumber.valueOf(data);
            long actualResult = LuaArgUtils.getLongFromLuaValue(arg, "number1");
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLongFromLuaValue(arg, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" cannot be nil", runtimeException.getMessage());
        }
        { // wrong type
            LuaValue arg = new LuaUserdata(new byte[]{0x12, 0x12, 0x12});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getLongFromLuaValue(arg, "number1");
            });
            Assertions.assertEquals("Argument \"number1\" is \"userdata\", expected 'long'", runtimeException.getMessage());
        }
    }

    @Test
    public void getSelf_FromFunctionArgs_luaValue() {
        class Module extends LuaTable {
        }
        { // ok
            Module data = new Module();
            Object actualResult = LuaArgUtils.getSelfFromFunctionArgs(data);
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            LuaValue arg = LuaValue.NIL;
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getSelfFromFunctionArgs(arg);
            });
            Assertions.assertEquals("Argument \"self\" (1) cannot be nil", runtimeException.getMessage());
        }
    }

    @Test
    public void getSelf_FromFunctionArgs_varargs() {
        class Module extends LuaTable {
        }
        { // ok
            Module data = new Module();
            Varargs varargs = LuaValue.varargsOf(data, LuaValue.valueOf(1122));
            Object actualResult = LuaArgUtils.getSelfFromFunctionArgs(varargs);
            Assertions.assertEquals(data, actualResult);
        }
        { // arg is nil
            Varargs varargs = LuaValue.varargsOf(new LuaValue[]{});
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                LuaArgUtils.getSelfFromFunctionArgs(varargs);
            });
            Assertions.assertEquals("Argument \"self\" (1) cannot be nil", runtimeException.getMessage());
        }
    }

}