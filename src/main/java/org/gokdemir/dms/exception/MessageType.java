package org.gokdemir.dms.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1001","Kayıt bulunamadı"),
    GENERAL_EXCEPTION("9999","Genel bir hata oluştu"),
    REFRESH_TOKEN_EXPIRED("1002", "refresh token süresi dolmuştur"),
    REFRESH_TOKEN_NOT_FOUND("1003", "refresh token bulunamadı"),
    USERNAME_OR_PASSWORD_INVALID("1004","kullanıcı adı veya şifre hatalı"),
    COMPANY_ALREADY_EXISTS("1005", "Bu isimde bir firma zaten mevcut!"),
    FOLDER_CREATION_FAILED("1006", "Firma klasörü oluşturulamadı!");


    private String code;

    private String message;

    private MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
