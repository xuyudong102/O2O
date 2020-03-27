package com.xyd.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.shopadmin
 * @date 2020/3/23
 * @description
 */
@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

    @RequestMapping("/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @RequestMapping("/shoplist")
    public String shopList(){return "shop/shoplist";}

    @RequestMapping("/shopmanagement")
    public String shopManagement(){return "shop/shopmanagement";}
}
