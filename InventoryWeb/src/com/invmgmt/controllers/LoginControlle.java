package com.invmgmt.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.LoginInfoDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.dao.SessionDao;
import com.invmgmt.dao.UserDetailsDao;
import com.invmgmt.entity.LoginInfo;
import com.invmgmt.entity.Project;
import com.invmgmt.entity.SessionEntry;
import com.invmgmt.entity.UserDetails;

@Controller
@EnableWebMvc
public class LoginControlle {

	@Autowired
	LoginInfoDao loginInfoDao;

	@Autowired
	SessionDao sessionDao;

	@Autowired
	BOQDetailsDao boqDetailsDao;

	@Autowired
	ProjectDao projectDao;

	@Autowired
	UserDetailsDao userDetailsDao;

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

			// view = "Home";
			view = "newHome";
		} else {
			view = "LandingPage";

		}

		ModelAndView modelAndView = new ModelAndView(view);

		if (validaLogin) {
			String projectIdVal = "";

			projectIdVal = boqDetailsDao.getRecentProject();

			Project project = projectDao.getProject(Integer.parseInt(projectIdVal));

			if (!(projectIdVal.equals("") || projectIdVal.equals("0"))) {
				modelAndView.addObject("projectDesc", project.getProjectDesc());
				modelAndView.addObject("projectIdVal", projectIdVal);
				modelAndView.addObject("projectNameVal", project.getProjectName());
			} else {
				modelAndView.addObject("projectDesc", "No Recent Project");
				modelAndView.addObject("projectIdVal", "No Recent Project");
				modelAndView.addObject("projectNameVal", "No Recent Project");
			}
		}

		return modelAndView;

	}

	private boolean validateLogin(LoginInfo loginInfo) {
		boolean validLogin = false;
		String validPassword = loginInfoDao.gePasswordToValidate(new LoginInfo(loginInfo.getUserName(), ""));

		if (validPassword.equalsIgnoreCase(loginInfo.getPassword())) {
			validLogin = true;
		}
		return validLogin;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	protected ModelAndView logout(HttpSession session) {
		double sessionId = (double) session.getAttribute("sessionId");
		boolean sessionDelete = true;
		String userName = (String) session.getAttribute("userName");
		sessionDelete = sessionDao.deleteSession(String.valueOf(sessionId));
		return new ModelAndView("redirect:login");
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	protected @ResponseBody String registerUser(UserDetails userDetails) {
		String result = "SUCCESS";
		boolean loginInfoAdded = false;
		boolean userRegistered = userDetailsDao.saveUser(userDetails);
		if (userRegistered) {
			loginInfoAdded = loginInfoDao
					.addLoginInfo(new LoginInfo(userDetails.getUserName(), userDetails.getUserPassword()));
		}

		if (!userRegistered || !loginInfoAdded) {
			result = "FAILURE";
		}

		return result;
	}
}
