package com.liyu.cms.data.web.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by liyu on 17/9/27.
 */

@RestController
@RequestMapping(value = "")
public class IndexController {

    @RequestMapping(value = "/heart-beat", method = RequestMethod.GET)
    public String alive(){
        return "alive";
    }
}
