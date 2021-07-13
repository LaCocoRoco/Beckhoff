package twincat.ads.constant;

public enum AdsError {
    /***********************************/
    /**** global constant variable *****/
    /***********************************/

	ADS_NO_ERR 					(0x0000),
	ADS_INTERNAL_ERR 			(0x0001),
	ADS_NO_RUNTIME 				(0x0002),
	ADS_MEM_ALLOC_LOCK_ERR 		(0x0003),
	ADS_INSERT_MAILBOX_ERR 		(0x0004),
	ADS_WRONG_HMSG 				(0x0005),
	ADS_PORT_NOT_FOUND 			(0x0006),
	ADS_MACHINE_NOT_FOUND 		(0x0007),
	ADS_UNKN_CMD_ID 			(0x0008),
	ADS_BAD_TASK_ID 			(0x0009),
	ADS_NO_IO 					(0x000A),
	ADS_UNKN_AMS_CMD			(0x000B),
	ADS_WIN32_ERR 				(0x000C),
	ADS_PORT_NOT_CONN 			(0x000D),
	ADS_INV_AMS_LEN 			(0x000E),
	ADS_INV_AMS_NETID 			(0x000F),
	ADS_LOW_INSTALL_LVL 		(0x0010),
	ADS_NO_DEBUG 				(0x0011),
	ADS_PORT_DISABLED 			(0x0012),
	ADS_PORT_ALREADY_CONN 		(0x0013),
	ADS_AMSSYNC_WIN32_ERR 		(0x0014),
	ADS_AMSSYNC_TIMEOUT 		(0x0015),
	ADS_AMSSYNC_AMS_ERR 		(0x0016),
	ADS_AMSSYNC_NO_INDEX_MAP 	(0x0017),
	ADS_INV_AMS_PORT 			(0x0018),
	ADS_NO_MEM 					(0x0019),
	ADS_TCP_SEND_ERR 			(0x001A),
	ADS_HOST_UNREACHABLE 		(0x001B),
	
	ADS_INV_AMS_NET_ID          (0x0200),
	
    ROUTER_NOLOCKEDMEMORY       (0x0500),
    ROUTER_RESIZEMEMORY         (0x0501),
    ROUTER_MAILBOXFULL          (0x0502),
    ROUTER_DEBUGBOXFULL         (0x0503),
    ROUTER_UNKNOWNPORTTYPE      (0x0504),
    ROUTER_NOTINITIALIZED       (0x0505),
    ROUTER_PORTALREADYINUSE     (0x0506),
    ROUTER_NOTREGISTERED        (0x0507),
    ROUTER_NOMOREQUEUES         (0x0508),
    ROUTER_INVALIDPORT          (0x0509),
    ROUTER_NOTACTIVATED         (0x050A),  	
	
	ADS_ERRCLASS_DEVICE_ERR 	(0x0700),
	ADS_SRVICE_NOT_SUPP 		(0x0701),
	ADS_INV_IGRP 				(0x0702),
	ADS_INV_IOFF 				(0x0703),
	ADS_READWRITE_NOT_PERMIT 	(0x0704),
	ADS_INV_PARAM_SIZE 			(0x0705),
	ADS_INV_PARAM_VALS 			(0x0706),
	ADS_DEV_NOT_RDY 			(0x0707),
	ADS_DEV_BUSY 				(0x0708),
	ADS_INV_CONTEXT 			(0x0709),
	ADS_OUTOF_MEM 				(0x070A),
	ADS_INV_PARAM_VALS2 		(0x070B),
	ADS_REQ_ITEM_NOT_FOUND 		(0x070C),
	ADS_SYNTAX_ERROR 			(0x070D),
	ADS_OBJ_MISMATCH 			(0x070E),
	ADS_OBJ_ALRDY_EXISTS 		(0x070F),
	ADS_SYMB_NOT_FOUND 			(0x0710),
	ADS_INV_SYMB 				(0x0711),
	ADS_INV_SERVER_STATE 		(0x0712),
	ADS_ADSTRANSMODE_NOT_SUPP 	(0x0713),
	ADS_INV_NOT_HDL 			(0x0714),
	ADS_NOTCLIENT_NOT_REG 		(0x0715),
	ADS_NOMORE_NOT_HDL 			(0x0716),
	ADS_SIZE_TOOBIG_FOR_WATCH 	(0x0717),
	ADS_DEV_NOT_INIT 			(0x0718),
	ADS_DEVICE_TIMEOUT 			(0x0719),
	ADS_QUERYINTERFACE_FAILED 	(0x071A),
	ADS_WRONG_INTERFACE_REQ 	(0x071B),
	ADS_INV_CLASS_ID 			(0x071C),
	ADS_INV_OBJ_ID 				(0x071D),
	ADS_REQ_PENDING 			(0x071E),
	ADS_REQ_ABORTED 			(0x071F),
	ADS_SIGNAL_WARN 			(0x0720),
	ADS_INV_ARR_INDEX 			(0x0721),
	ADS_SYMB_NOT_ACTIVE 		(0x0722),
	ADS_ACCESS_DENIED 			(0x0723),
	ADS_ERRCLASS_CLIENT_ERR 	(0x0740),
	ADS_INV_PARAM_AT_SERVICE 	(0x0741),
	ADS_EMPTY_POLL_LIST 		(0x0742),
	ADS_VAR_CONN_IN_USE 		(0x0743),
	ADS_INVOKE_ID_IN_USE 		(0x0744),
	ADS_TIMEOUT_ELAPSED 		(0x0745),
	ADS_WIN32_SUBSYSTEM_ERR 	(0x0746),
	ADS_INV_CLIENT_TIMEOUT 		(0x0747),
	ADS_ADSPORT_CLOSED 			(0x0748),
	ADS_ERRCLASS_INTERNAL_ERR 	(0x0750),
	ADS_HASHTBL_OVERFLOW 		(0x0751),
	ADS_HASHTBL_KEY_NOT_FOUND 	(0x0752),
	ADS_NOMORE_SYMB_IN_CACHE 	(0x0753),
	ADS_INV_RESP_RECEIVED 		(0x0754),
	ADS_SYNC_PORT_LOCKED 		(0x0755),

	RUNTIME_INTERNAL 			(0x1000),
	RUNTIME_BADTIMERPERIODS 	(0x1001),
	RUNTIME_INVALIDTASKPTR 	 	(0x1002),
	RUNTIME_INVALIDSTACKPTR 	(0x1003),
	RUNTIME_PRIOEXISTS 		 	(0x1004),
	RUNTIME_NOMORETCB 			(0x1005),
	RUNTIME_NOMORESEMAS 		(0x1006),
	RUNTIME_NOMOREQUEUES 		(0x1007),
	RUNTIME_EXTIRQALREADYDEF 	(0x100D),
	RUNTIME_EXTIRQNOTDEF 		(0x100E),
	RUNTIME_EXTIRQINSTALLFAILED (0x100F),
	RUNTIME_IRQLNOTLESSOREQUAL  (0x1010),
	
	KERNEL_ATT_OPER_UNREACHHOST (0x274C),
	KERNEL_CONN_TIMEOUT 		(0x274D),
	KERNEL_CONN_REFUSED 		(0x2751),
	
	VARIABLE_WRITE_PARSE_ERROR	(0x5555),
	   
	UNKNOWN						(0xFFFF);

    /***********************************/
    /********* global variable *********/
    /***********************************/

	public final int value;

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

    private AdsError(int value) {
        this.value = value;
    }

    /***********************************/
    /** public static final function ***/
    /***********************************/
  
    public static final AdsError getByValue(int value) {
        for (AdsError adsError : AdsError.values()) {
            if (adsError.value == value) {
            	return adsError;
            }
        }
        
        return AdsError.UNKNOWN;
    }
	
    public static final AdsError getByString(String value) {
        for (AdsError adsError : AdsError.values()) {
            if (adsError.name().equalsIgnoreCase(value)) {
                return adsError;
            }
        }
        return AdsError.UNKNOWN;
    } 
}
