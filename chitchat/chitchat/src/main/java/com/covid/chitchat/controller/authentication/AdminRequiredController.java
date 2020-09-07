package com.covid.chitchat.controller.authentication;

import com.covid.chitchat.constant.CommonConstant;
import com.covid.chitchat.constant.RoleType;
import com.covid.chitchat.util.Validator;

import javax.servlet.http.HttpSession;

public abstract class AdminRequiredController extends LoginRequiredController {

    public boolean isAdmin() {
        if (isLoggedIn()) {
            HttpSession session = Validator.getSession();
            String role = (String) session.getAttribute(CommonConstant.AttributeConstant.ROLE);
            return role != null && role.equals(RoleType.ADMIN.getValue());
        } else
            return false;
    }
}
