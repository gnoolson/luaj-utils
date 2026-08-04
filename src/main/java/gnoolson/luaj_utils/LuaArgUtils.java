package gnoolson.luaj_utils;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

// +
public class LuaArgUtils {

    /*
     *
     *
     * */
    // +
    public static LuaTable getLuaTableFromLuaTable(LuaTable luaTable, String tableName, String fieldName) {
        LuaValue luaValue = luaTable.get(fieldName);
        checkNotNil(luaValue, tableName, fieldName);
        return checkTable(luaValue, tableName, fieldName);
    }

    // +
    public static LuaFunction getLuaFunctionFromLuaTable(LuaTable luaTable, String tableName, String fieldName) {
        LuaValue luaValue = luaTable.get(fieldName);
        checkNotNil(luaValue, tableName, fieldName);
        return checkFunction(luaValue, tableName, fieldName);
    }

    // +
    public static byte[] getByteArrayFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return checkBytes(luaValue, tableName, argName);
    }

    // +
    public static String getStringFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return checkString(luaValue, tableName, argName);
    }

    // +
    public static LuaValue getLuaValueFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return luaValue;
    }

    // +
    public static int getIntFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return checkInt(luaValue, tableName, argName);
    }

    // +
    public static long getLongFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return checkLong(luaValue, tableName, argName);
    }

    // +
    public static double getDoubleFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return checkNumber(luaValue, tableName, argName);
    }

    // +
    public static boolean getBooleanFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        return checkBoolean(luaValue, tableName, argName);
    }

    // +
    public static Object getObjectFromLuaTable(LuaTable luaTable, String tableName, String argName) {
        LuaValue luaValue = luaTable.get(argName);
        checkNotNil(luaValue, tableName, argName);
        LuaTable objectLuaTable = checkTable(luaValue, tableName, argName);
        return LuaTypeConverter.toObject(objectLuaTable);
    }

    /*
     *
     *
     * */
    // +
    public static String getStringFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkString(luaValue, argNumber, argName);
    }

    // +
    public static boolean getBooleanFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkBoolean(luaValue, argNumber, argName);
    }

    // +
    public static LuaFunction getFunctionFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkFunction(luaValue, argNumber, argName);
    }

    // +
    public static LuaValue getLuaValueFromFunctionVarargs(Varargs varargs, int argNumber, String argName) {
        LuaValue arg = varargs.arg(argNumber);
        checkNotNil(arg, argNumber, argName);
        return arg;
    }

    // +
    public static double getDoubleFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkNumber(luaValue, argNumber, argName);
    }

    // +
    public static int getIntFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkInt(luaValue, argNumber, argName);
    }

    // +
    public static byte[] getByteArrayFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkBytes(luaValue, argNumber, argName);
    }

    // +
    public static Object getObjectFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        LuaTable table = checkTable(luaValue, argNumber, argName);
        return LuaTypeConverter.toObject(table);
    }

    // +
    public static LuaTable getLuaTableFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkTable(luaValue, argNumber, argName);
    }

    // +
    public static long getLongFromFunctionArgs(LuaValue luaValue, int argNumber, String argName) {
        checkNotNil(luaValue, argNumber, argName);
        return checkLong(luaValue, argNumber, argName);
    }

    /*
     *
     *
     * */
    // +
    public static String getStringFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkString(luaValue, argName);
    }

    // +
    public static boolean getBooleanFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkBoolean(luaValue, argName);
    }

    // +
    public static double getDoubleFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkNumber(luaValue, argName);
    }

    // +
    public static int getIntFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkInt(luaValue, argName);
    }

    // +
    public static byte[] getByteArrayFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkBytes(luaValue, argName);
    }

    // +
    public static Object getObjectFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        LuaTable table = checkTable(luaValue, argName);
        return LuaTypeConverter.toObject(table);
    }

    // +
    public static LuaTable getLuaTableFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkTable(luaValue, argName);
    }

    // +
    public static long getLongFromLuaValue(LuaValue luaValue, String argName) {
        checkNotNil(luaValue, argName);
        return checkLong(luaValue, argName);
    }

    // +
    public static <T> T getSelfFromFunctionArgs(LuaValue luaValue) {
        checkNotNil(luaValue, 1, "self");
        return (T) luaValue;
    }

    // +
    public static <T> T getSelfFromFunctionArgs(Varargs args) {
        LuaValue self = args.arg(1);
        return getSelfFromFunctionArgs(self);
    }

    /*
     *
     *
     * */
    // +
    public static byte[] checkBytes(LuaValue luaValue, String argName) {
        if (!luaValue.isuserdata())
            throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'userdata'", argName, luaValue.typename())); // +

        return (byte[]) luaValue.checkuserdata(byte[].class);
    }

    // +
    public static byte[] checkBytes(LuaValue luaValue, int argNumber, String argName) {
        if (!luaValue.isuserdata())
            throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'userdata'", argName, argNumber, luaValue.typename())); // +

        return (byte[]) luaValue.checkuserdata(byte[].class);
    }

    // +
    public static byte[] checkBytes(LuaValue luaValue, String tableName, String argName) {
        if (!luaValue.isuserdata())
            throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'userdata'", argName, tableName, luaValue.typename())); // +

        return (byte[]) luaValue.checkuserdata(byte[].class);
    }

    // +
    public static void checkNotNil(LuaValue luaValue, String tableName, String fieldName) {
        if (luaValue.equals(LuaValue.NIL))
            throw new IllegalArgumentException(String.format("Field \"%s\" was not found in LuaTable \"%s\"", fieldName, tableName)); // +
    }

    // +
    public static String checkString(LuaValue luaValue, String tableName, String fieldName) {
        if (luaValue.isstring())
            return luaValue.checkjstring();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'string'", fieldName, tableName, luaValue.typename())); // +
    }

    // +
    public static LuaTable checkTable(LuaValue luaValue, String tableName, String fieldName) {
        if (luaValue.istable())
            return luaValue.checktable();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'table'", fieldName, tableName, luaValue.typename())); // +
    }

    // +
    public static LuaFunction checkFunction(LuaValue luaValue, String tableName, String fieldName) {
        if (luaValue.isfunction())
            return luaValue.checkfunction();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'function'", fieldName, tableName, luaValue.typename())); // +
    }

    // +
    public static int checkInt(LuaValue luaValue, String tableName, String argName) {
        if (luaValue.isint())
            return luaValue.checkint();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'int'", argName, tableName, luaValue.typename())); // +
    }

    // +
    public static double checkNumber(LuaValue luaValue, String tableName, String argName) {
        if (luaValue.isnumber())
            return luaValue.checkdouble();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'number'", argName, tableName, luaValue.typename())); // +
    }

    // +
    public static long checkLong(LuaValue luaValue, String tableName, String argName) {
        if (luaValue.islong())
            return luaValue.checklong();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'long'", argName, tableName, luaValue.typename())); // +
    }

    // +
    public static boolean checkBoolean(LuaValue luaValue, String tableName, String argName) {
        if (luaValue.isboolean())
            return luaValue.checkboolean();

        throw new IllegalArgumentException(String.format("Field \"%s\" in LuaTable \"%s\" is \"%s\", expected 'boolean'", argName, tableName, luaValue.typename())); // +
    }

    /*
     *
     *
     * */
    // +
    public static void checkNotNil(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.equals(LuaValue.NIL))
            throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) cannot be nil", argName, argNumber)); // +
    }

    // +
    public static String checkString(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.isstring())
            return luaValue.checkjstring();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'string'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static double checkNumber(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.isnumber())
            return luaValue.checkdouble();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'number'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static int checkInt(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.isint())
            return luaValue.checkint();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'int'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static LuaTable checkTable(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.istable())
            return luaValue.checktable();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'table'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static long checkLong(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.islong())
            return luaValue.checklong();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'long'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static boolean checkBoolean(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.isboolean())
            return luaValue.checkboolean();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'boolean'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static LuaFunction checkFunction(LuaValue luaValue, int argNumber, String argName) {
        if (luaValue.isfunction())
            return luaValue.checkfunction();

        throw new IllegalArgumentException(String.format("Argument \"%s\" (%d) is \"%s\", expected 'function'", argName, argNumber, luaValue.typename())); // +
    }

    // +
    public static void checkNotNil(LuaValue luaValue, String argName) {
        if (luaValue.equals(LuaValue.NIL))
            throw new IllegalArgumentException(String.format("Argument \"%s\" cannot be nil", argName)); // +
    }

    // +
    public static String checkString(LuaValue luaValue, String argName) {
        if (luaValue.isstring())
            return luaValue.checkjstring();

        throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'string'", argName, luaValue.typename())); // +
    }

    // +
    public static double checkNumber(LuaValue luaValue, String argName) {
        if (luaValue.isnumber())
            return luaValue.checkdouble();

        throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'number'", argName, luaValue.typename())); // +
    }

    // +
    public static int checkInt(LuaValue luaValue, String argName) {
        if (luaValue.isint())
            return luaValue.checkint();

        throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'int'", argName, luaValue.typename())); // +
    }

    // +
    public static LuaTable checkTable(LuaValue luaValue, String argName) {
        if (luaValue.istable())
            return luaValue.checktable();

        throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'table'", argName, luaValue.typename())); // +
    }

    // +
    public static long checkLong(LuaValue luaValue, String argName) {
        if (luaValue.islong())
            return luaValue.checklong();

        throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'long'", argName, luaValue.typename())); // +
    }

    // +
    public static boolean checkBoolean(LuaValue luaValue, String argName) {
        if (luaValue.isboolean())
            return luaValue.checkboolean();

        throw new IllegalArgumentException(String.format("Argument \"%s\" is \"%s\", expected 'boolean'", argName, luaValue.typename())); // +
    }

}
