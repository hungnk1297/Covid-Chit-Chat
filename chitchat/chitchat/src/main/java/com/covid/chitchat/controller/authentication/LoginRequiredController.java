package com.covid.chitchat.controller.authentication;

import com.covid.chitchat.constant.CommonConstant;
import com.covid.chitchat.util.Validator;

import javax.servlet.http.HttpSession;

public abstract class LoginRequiredController {

    public boolean isLoggedIn() {
        HttpSession session = Validator.getSession();
        return session.getAttribute(CommonConstant.AttributeConstant.USER_LOGGED_IN) != null;
    }
}
