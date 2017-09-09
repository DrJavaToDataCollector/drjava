package edu.rice.cs.drjava.collect;

/**
 *	package-visible Enum with basic events that we might sent to the server, excluding debugging and testing events
 */

enum EventName{
	DRJAVA_START("drjava_start"),
	DRJAVA_FINISH("drjava_finish"),
	

	ADD("file_add"),
    DELETE("file_delete"),
    RENAME("rename"),
    EDIT("edit"),
    COMPILE("compile"),
    FILE_OPEN("file_open"),
    FILE_SELECT("file_select"),
    FILE_CLOSE("file_close"),

    SHOWN_ERROR_INDICATOR("shown_error_indicator"),
    SHOWN_ERROR_MESSAGE("shown_error_message");


	private final String name;
    
    private EventName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
}
