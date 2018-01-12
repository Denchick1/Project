package com.example.root.metr.utils;

import android.text.TextUtils;

public class EditTextValidator {

    TextUtils textUtils;

    public EditTextValidator() {
    }

    public EditTextValidator(TextUtils textUtils) {
        this.textUtils = textUtils;
    }

    public boolean phoneValidateNew(String phone){
        return phone.length()==18;
    }

    public String phoneConvertation(String phone){
        try {
            String phone1=phone.substring(4,7);
            String phone2=phone.substring(9,12);
            String phone3=phone.substring(13,15);
            String phone4=phone.substring(16,18);
            return "7"+phone1+phone2+phone3+phone4;
        }catch (Exception e){
            boolean empty = textUtils.isEmpty(phone);
            boolean startWithPlus = phone.startsWith("+7");
            if(startWithPlus&&!empty&&phone.length()==12){
                return phone.substring(1,phone.length());
            }else if(!empty&&phone.startsWith("8")){
                return "7"+phone.substring(1,phone.length());
            }else return phone;
        }

    }

    public boolean isValidEmailAddress(String email) {
        if(textUtils.isEmpty(email)){
            return false;
        }else {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            return m.matches();
        }
    }
}
