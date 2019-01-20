package com.invmgmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.LoginInfoDao;
import com.invmgmt.entity.LoginInfo;

@Controller
@EnableWebMvc
public class LoginControlle {

    @Autowired
    LoginInfoDao loginInfoDao;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(LoginInfo loginInfo) {

	String view = "";
	boolean validaLogin = validateLogin(loginInfo);
	if (validaLogin) {
	    view = "Home";
	} else {
	    view = "LandingPage";
	}

	ModelAndView mav = new ModelAndView(view);

	return mav;
    }

    private boolean validateLogin(LoginInfo loginInfo) {
	boolean validLogin = false;

	String validPassword = loginInfoDao.gePasswordToValidate(new LoginInfo(loginInfo.getUserName(), ""));

	if (validPassword.equalsIgnoreCase(loginInfo.getPassword())) {
	    validLogin = true;
	}

	return validLogin;
    }
}
