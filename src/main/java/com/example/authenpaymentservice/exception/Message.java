package com.example.authenpaymentservice.exception;

public class Message {
    private Message(){}
    public static final String NOT_FOUND = "Có lỗi xảy ra!";
    public static final String PASSWORD_INVALID = "Tài khoản hoặc mật khẩu không chính xác!";
    public static final String WRONG_FORMAT = "Sai định dạng tài khoản!";
    public static final String USERNAME_EXITED = "Tài khoản đã tồn tại!";
    public static final String ACCOUNT_NON_ACTIVE = "Tài khoản chưa được kích hoạt!";
    public static final String ACCOUNT_LOCKED = "Tài khoản của bạn đã bị khoá!";
    public static final String JWT_EXPIRED = "Tài khoản đã hết hạn đăng nhập. Vui lòng đăng nhập lại!";
    public static final String OAUTH2_EMAIL_NOT_FOUND = "Email not found from OAuth2 provider";
    public static final String FILE_EXTENSION_INVALID = "Định dạng file không đúng!";

    //shop message
    public static final String SHOP_EXISTED = "Shop đã tồn tại!";

}
