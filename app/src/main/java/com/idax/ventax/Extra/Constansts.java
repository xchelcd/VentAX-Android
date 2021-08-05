package com.idax.ventax.Extra;

import static com.idax.ventax.Extra.EndPoints.REST_URL;
import static com.idax.ventax.Extra.EndPoints.ROOT_URL;

public class Constansts {
    //IDAX
    public static final String version = "v2/";
    public static final String mainURL = ROOT_URL;
    public static final String restURL = REST_URL;
    public static final int ANDROID = 0;

    //REQUEST CODE
    public static final int REQUEST_CODE_REGISTER = 312;
    public static final int REQUEST_CODE_COMPANY_BOTTOMSHEET = 156;
    public static final int REQUEST_CODE_PROFILE_FRAGMENT = 951;
    public static final int REQUEST_CODE_ORDER_ITEM = 56;
    public static final int REQUEST_CODE_SHOPPING_CART = 1449;
    public static final int REQUEST_CODE_FAQs = 825;
    public static final int REQUEST_CODE_COMPANY_PHOTO = 1238;
    public static final int REQUEST_CODE_TUTORIAL = 915;

    //RESULT CODE
    public static final int RESULT_CODE_GO_TO_SHOPPING_CART = 45;
    public static final int RESULT_CODE_GO_TO_TUTORIAL = 285;
    public static final int RESULT_CODE_GOT_TO_MENU = 85;

    //INTENT
    public static final String INTENT_USER = "INTENT_USER";
    public static final String INTENT_COMPANY_LIST = "INTENT_COMPANY_LIST";
    public static final String INTENT_MODEL_ENTREPRENEUR = "INTENT_MODEL_ENTREPRENEUR";
    public static final String INTENT_MODEL_LOGIN = "INTENT_MODEL_LOGIN";
    public static final String INTENT_LOGIN_VIEW = "INTENT_LOGIN_VIEW";
    public static final String INTENT_ORDER_MODEL_LIST = "INTENT_ORDER_MODEL_LIST";
    public static final String INTENT_FAQ_LIST = "INTENT_FAQ_LIST";
    public static final String INTENT_COMPANY = "INTENT_COMPANY";

    //BUNDLE
    public static final String BUNDLE_COMPANY_LIST = "BUNDLE_COMPANY_LIST";
    public static final String BUNDLE_USER = "BUNDLE_USER";
    public static final String BUNDLE_MODEL_ENTREPRENEURS = "BUNDLE_MODEL_ENTREPRENEURS";
    public static final String BUNDLE_FAQ_LIST = "BUNDLE_FAQ_LIST";
    public static final String BUNDLE_COMPANY = "BUNDLE_COMPANY";
    public static final String BUNDLE_INTERFACE_OBJ = "BUNDLE_INTERFACE_OBJ";
    public static final String BUNDLE_INT_FOR_FAQ = "BUNDLE_INT_FOR_FAQ";

    //PERMISSIONS
    public final static int CODE_PERMISSION_INTERNET = 534;
    public final static int CODE_PERMISSION_CAMERA = 87;
    public final static int CODE_PERMISSION_STORAGE = 4123;

    //DRAWER
    public static final int POS_MENU = 0;
    public static final int POS_PROFILE = 1;
    public static final int POS_CART = 2;
    public static final int POS_COMPANY = 3;
    public static final int POS_ORDER = 4;
    public static final int POS_PRODUCT = 5;
    public static final int POS_SETTINGS = 6;
    public static final int POS_FAQ = 7;
    public static final int POS_IDAX = 8;
    public static final int POS_LOGOUT = 9;

    //PRIORITY -> accountType
    public static final int PRIORITY_BASIC = 0;
    public static final int PRIORITY_PREMIUM = 1;
    public static final int PRIORITY_BUSINESS = 2;

    //ITEMS
    //public static final int ITEM_BASIC = 4;
    public static final int ITEM_BASIC = 10;
    public static final int ITEM_PREMIUM = 20;
    public static final int ITEM_BUSINESS = 40;

    //FAQs
    public static final int FAQ_BASIC = 3;
    public static final int FAQ_PREMIUM = 8;
    public static final int FAQ_BUSINESS = 15;

    //TYPE COMPANY, CLIENT
    public static final int CANCEL_TYPE_COMPANY = 12;
    public static final int CANCEL_TYPE_CLIENT = 13;

    //EXTRA
    public static final int AFFILIATE = 1;
    public static final int NOT_AFFILIATE = 0;
    public static final int AS_COMPANY = 0;
    public static final int AS_USER = 1;

    //INFORMATION DIALOG
    public static final int POSITION_TOP = 1;
    public static final int POSITION_BOTTOM = 0;


    //SHARD PREFERENCES
    public static final String DATA_PERSISTENCE_USER = "DATA_PERSISTENCE_USER";
    public static final String SHARD_PREFERENCES_USER = "SHARD_PREFERENCES_USER";

    public static final String DATA_PERSISTENCE_ENROLLED = "DATA_PERSISTENCE_ENROLED";
    public static final String SHARD_PREFERENCES_ENROLLED = "SHARD_PREFERENCES_ENROLED";

    public static final String DATA_PERSISTENCE_IS_FIRST_TIME = "DATA_PERSISTENCE_IS_FIRST_TIME";
    public static final String SHARD_PREFERENCES_IS_FIRST_TIME = "SHARD_PREFERENCES_IS_FIRST_TIME";

    //ORDER STATE
    public static final int PENDING = 0;
    public static final int ACEPTED = 1;
    public static final int PROCCESS = 2;
    public static final int FINISHED = 3;
    public static final int CANCELED = 4;

    //STEP ORDER DIALOG VIEW TYPE
    public static final boolean COMPANY_VIEW = false;
    public static final boolean USER_VIEW = true;

    //NOTIFICATIONS
    public static final String NOTIFICATION_EXTRA_DATA = "NOTIFICATION_EXTRA_DATA";


}
