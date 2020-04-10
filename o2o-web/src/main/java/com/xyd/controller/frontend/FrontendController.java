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
    /*首页路由*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    private String index(){
        return "frontend/index";
    }

    /*商品列表页路由*/
    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    private String showShopList(){
        return "frontend/shoplist";
    }

    /*商店详情页路由*/
    @RequestMapping(value = "/shopdetail",method = RequestMethod.GET)
    private String showShopDetail() { return "frontend/shopdetail"; }

    /*商品详情页面路由*/
    @RequestMapping(value = "/productdetail",method = RequestMethod.GET)
    private String showProductDetail(){return "frontend/productdetail";}
}
