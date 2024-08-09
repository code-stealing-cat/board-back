package com.jm.board_back.common;

public interface ResponseCode {

    // 상수 필드는 public static final 를 명시적으로 적지 않아도 Compile Time 에 자동으로 선언되어 상수로 만든다.
    // HTTP Status 200
    String SUCCESS = "SU"; // public static final

    // HTTP Status 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_TEL_NUMBER = "DT";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_BOARD = "NB";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    // HTTP Status 403
    String NO_PERMISSION = "NP";

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";
}