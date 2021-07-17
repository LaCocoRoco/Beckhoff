package twincat.ads.constant;

public enum State {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

	INVALID 		(0x0000),
	IDLE 			(0x0001),
	RESET 			(0x0002),
	INIT 			(0x0003),
	START 			(0x0004),
	RUN 			(0x0005),
	STOP 			(0x0006),
	SAVECFG 		(0x0007),
	LOADCFG 		(0x0008),
	POWERFAILURE	(0x0009),
	POWERGOOD		(0x000A),
	ERROR			(0x000B),
	SHUTDOWN		(0x000C),
	SUSPEND			(0x000D),
	RESUME			(0x000E),
	UNKNOWN			(0xFFFF);

    /*********************************/
    /******** global variable ********/
    /*********************************/

	public final int value;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    private State(int value) {
        this.value = value;
    }

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final State getByValue(int value) {
        for (State state : State.values()) {
            if (state.value == value) {
            	return state;
            }
        }
        
        return State.UNKNOWN;
    }
    
    public static final State getByString(String value) {
        for (State state : State.values()) {
            if (state.name().equalsIgnoreCase(value)) {
                return state;
            }
        }
        return State.UNKNOWN;
    } 
}
