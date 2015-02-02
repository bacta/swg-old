package com.ocdsoft.bacta.swg.name;

/**
 * Created by kburkhardt on 3/28/14.
 */
public interface NameService {

    public static final String NAME_APPROVED = "name_approved";
    public static final String NAME_DECLINED_CANT_CREATE_AVATAR = "name_declined_cant_create_avatar";  // Hacking char creation?
    public static final String NAME_DECLINED_DEVELOPER = "name_declined_developer";
    public static final String NAME_DECLINED_EMPTY = "name_declined_empty";  //
    public static final String NAME_DECLINED_FICTIONALLY_RESERVED = "name_declined_fictionally_reserved";
    public static final String NAME_DECLINED_IN_USE = "name_declined_in_use";
    public static final String NAME_DECLINED_INTERNAL_ERROR = "name_declined_internal_error";
    public static final String NAME_DECLINED_NO_NAME_GENERATOR = "name_declined_no_name_generator";
    public static final String NAME_DECLINED_NO_TEMPLATE = "name_declined_no_template";
    public static final String NAME_DECLINED_NOT_CREATURE_TEMPLATE = "name_declined_no_template";
    public static final String NAME_DECLINED_NUMBER = "name_declined_number";
    public static final String NAME_DECLINED_RACIALLY_INAPPROPRIATE = "name_declined_racially_inappropriate";
    public static final String NAME_DECLINED_RESERVED = "name_declined_reserved";	 // Profane
    public static final String NAME_DECLINED_RETRY = "name_declined_retry";
    public static final String NAME_DECLINED_SYNTAX = "name_declined_syntax";
    public static final String NAME_DECLINED_TOO_FAST = "name_declined_too_fast";

    public static final int PLAYER = 1;
    public static final int CREATURE = 2;
    public static final int RESOURCE = 3;

    String generateName(int type, Object... args);

    String validateName(int type, String name, Object... args);

    void addPlayerName(String firstName);
}
