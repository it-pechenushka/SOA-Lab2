package ru.ilyai.lab2.helper.common;

public class ErrorMessages {
    public static final String WARNING_INTEGER = "Field's 'X' and 'Z' in Location, 'Students Count', 'Expelled Students', 'Should Be Expelled', 'Weight' should be integer number.";
    public static final String WARNING_ID = "Field's 'Id' should be integer number and positive (if present).";
    public static final String WARNING_FLOAT = "Field's 'Y' in Location, 'X' and 'Y' in Coordinates should be float number.";
    public static final String WARNING_PAGINATION = "Field's 'Page Size' and 'Page Number' should be positive integer number.";
    public static final String INVALID_ID = "Invalid 'Id' entered! 'Id' should be positive integer number";
    public static final String ENTITY_NOT_FOUND = "Study Group not found!";
    public static final String EMPTY_ENTITY_LIST = "Does not any entities in the store by this criteria!";
}
