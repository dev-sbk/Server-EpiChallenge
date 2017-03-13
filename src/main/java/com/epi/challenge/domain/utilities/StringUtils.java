package com.epi.challenge.domain.utilities;


/**
 *
 * @author Saber Ben Khalifa
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static final String GET_METHOD = "get";
    public static final String SET_METHOD = "set";
    public static final String IS_METHOD = "IS";

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String field2Get(String fieldname) {
        return fieldToBeanMethod(fieldname, GET_METHOD);
    }

    public static String field2Set(String fieldname) {
        return fieldToBeanMethod(fieldname, SET_METHOD);
    }

    /**
     * Build method name from field name
     *
     * @param fieldname Field name
     * @param methodKind either "get", "set" or "is" (for boolean).
     * @return
     */
    public static String fieldToBeanMethod(String fieldname, String methodKind) {
        StringBuilder builder = new StringBuilder(methodKind);

        builder.append(Character.toUpperCase(fieldname.charAt(0)));
        builder.append(fieldname.substring(1));

        return builder.toString();
    }
}
