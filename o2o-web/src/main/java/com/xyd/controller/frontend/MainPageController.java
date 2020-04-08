package com.xyd.controller.frontend;

import com.xyd.entity.HeadLine;
import com.xyd.entity.ShopCategory;
import com.xyd.mapper.HeadLineMapper;
import com.xyd.service.HeadLineService;
import com.xyd.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.frontend
 * @date 2020/4/5
 * @description
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    HeadLineService headLineService;
    @Autowired
    ShopCategoryService shopCategoryService;

    @RequestMapping("/listmainpageinfo")
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<HeadLine> headLineList = new ArrayList<>();
        try{
            //获取一级店铺类别信息 店铺类别查询对象为空
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            //获取状态值为1 的头条列表
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("headLineList",headLineList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }


}
