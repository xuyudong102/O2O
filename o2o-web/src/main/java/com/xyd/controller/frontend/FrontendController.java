package com.xyd.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.frontend
 * @date 2020/4/6
 * @description
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    private String index(){
        return "frontend/index";
    }

    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    private String showShopList(){
        return "frontend/shoplist";
    }
}
