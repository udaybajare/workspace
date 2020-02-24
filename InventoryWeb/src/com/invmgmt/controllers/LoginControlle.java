package com.invmgmt.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.LoginInfoDao;
import com.invmgmt.dao.SessionDao;
import com.invmgmt.entity.LoginInfo;
import com.invmgmt.entity.SessionEntry;

@Controller
@EnableWebMvc
public class LoginControlle {

	@Autowired
	LoginInfoDao loginInfoDao;

	@Autowired
	SessionDao sessionDao;

	final static String VIEW = "LandingPage";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView(VIEW);

		return modelAndView;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	protected ModelAndView login(LoginInfo loginInfo, HttpSession session) {

		String view = "";
		boolean validaLogin = validateLogin(loginInfo);
		if (validaLogin) {

			double sessionId = 0;
			// Call session DAO and add the entry of the sessionId created above
			// for the current userName

			boolean sessionSaved = false;

			while (!sessionSaved) {
				sessionId = Math.random();
				sessionSaved = sessionDao
						.saveSession(new SessionEntry(String.valueOf(sessionId), loginInfo.getUserName()));
			}

			session.setAttribute("userName", loginInfo.getUserName());
			session.setAttribute("sessionId", sessionId);

			view = "home";
		} else {
			view = "Login";

		}

		ModelAndView modelAndView = new ModelAndView(view);

		return modelAndView;

	}

	private boolean validateLogin(LoginInfo loginInfo)
	{
		boolean validLogin = false;
		String validPassword = loginInfoDao.gePasswordToValidate(new LoginInfo(loginInfo.getUserName(), ""));

		if (validPassword.equalsIgnoreCase(loginInfo.getPassword())) {
			validLogin = true;
		}
		return validLogin;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	protected ModelAndView logout(HttpSession session)
	{
		double sessionId = (double) session.getAttribute("sessionId");
		boolean sessionDelete = true;
		String userName = (String) session.getAttribute("userName");
		sessionDelete = sessionDao.deleteSession(String.valueOf(sessionId));
		return new ModelAndView("redirect:login");
	}
}
