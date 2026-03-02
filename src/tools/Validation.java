package tools;

public interface Validation {

    public static final String DEV_ID_VALID = "^DEV[0-9]{3}$";
    public static final String PROJECT_ID_VALID = "^(?i)proj[0-9]{2}$";
    public static final String FULL_NAME_VALID = "^[a-zA-Z]+(\\s[a-zA-Z]+)+$";
    public static final String PROGRAM_LANGUAGE_VALID = "^[A-Za-z][A-Za-z0-9+#]+$";
    public static final String SALARY_VALID = "^[1-9][0-9]{3,}$";
    public static final String DURATION_MONTHS_VALID = "^[1-9][0-9]*$";
    public static final String START_DATE_VALID = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
    public static final String END_DATE_VALID = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
    public static final String PROJECT_NAME_VALID = "^[A-Z][a-zA-Z0-9]*(\\s[a-zA-Z0-9]+)*$";
    public static final String INTEGER_VALID = "^[0-9]+$";
    public static final String DOUBLE_VALID = "^[0-9]+\\.[0-9]+$";
    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
