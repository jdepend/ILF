package com.rofine.platform.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdg on 2015/11/30.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void login(ModelMap model, @RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest req, HttpServletResponse res) {

        boolean loginResult;
        if(name.equals("admin") && password.equals("1")) {
            req.getSession().setAttribute("user", name);
            loginResult = true;
        }else{
            loginResult = false;
        }

        res.setContentType("text/plain");
        String callbackFunName =req.getParameter("callbackparam");//得到js函数名称
        try {
             res.getWriter().write(callbackFunName + "({\"result\":" + loginResult + "})"); //返回jsonp数据 
        } catch (IOException e) {
             e.printStackTrace();
        }
    }

    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public Map login(ModelMap model, @RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest req) {

        boolean loginResult;
        if(name.equals("admin") && password.equals("1")) {
            req.getSession().setAttribute("user", name);
            loginResult = true;
        }else{
            loginResult = false;
        }

        Map<String, Object> rtn = new HashMap();
        rtn.put("result", loginResult);
        return rtn;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String loginSubmit(ModelMap model, @ModelAttribute("loginInfo") LoginInfo userInfo, HttpServletRequest req, HttpServletResponse res) {

        boolean loginResult;
        if(userInfo.getName().equals("admin") && userInfo.getPassword().equals("1")) {
            req.getSession().setAttribute("user", userInfo.getName());
            return "redirect:/main";
        }else{
            model.addAttribute("errorMsg", "用户名密码错误");
            return "login/login-form";
        }

    }

    @RequestMapping(value = "into", method = RequestMethod.GET)
    public String loginInto() {
       return "login/login";
    }
}
