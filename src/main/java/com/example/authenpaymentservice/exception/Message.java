package com.example.authenpaymentservice.exception;

public class Message {
    private Message(){}
    public static final String NOT_FOUND = "Có lỗi xảy ra!";
    public static final String PASSWORD_INVALID = "Tài khoản hoặc mật khẩu không chính xác!";
    public static final String WRONG_FORMAT = "Sai định dạng tài khoản!";
    public static final String USERNAME_EXITED = "Email đã được đăng ký!";
    public static final String ACCOUNT_NON_ACTIVE = "Tài khoản chưa được kích hoạt!";
    public static final String ACCOUNT_ACTIVE = "Tài khoản đã được kích hoạt!";
    public static final String ACCOUNT_LOCKED = "Tài khoản của bạn đã bị khoá!";
    public static final String JWT_EXPIRED = "Tài khoản đã hết hạn đăng nhập. Vui lòng đăng nhập lại!";
    public static final String OAUTH2_EMAIL_NOT_FOUND = "Email not found from OAuth2 provider";
    public static final String FILE_EXTENSION_INVALID = "Định dạng file không đúng!";
    public static final String OTP_NOT_VALID = "OTP không chính xác!";
    public static final String REPASS_NOT_VALID = "Mật khẩu nhập lại không chính xác!";

    public static final String NO_ACCESS_RESOURCE = "Tài khoản không có quyền truy cập!";

    //shop message
    public static final String SHOP_EXISTED = "Shop đã tồn tại!";

}
