package com.xyd.controller.superadmin;

import com.xyd.entity.Area;
import com.xyd.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.superadmin
 * @date 2020/3/20
 * @description
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {
    @Autowired
    private AreaService areaService;



    /**
     * modelmap 需要返回的值
     * 成功:返回集合 1.rows->arealist集合  2.total->集合大小
     *失败: 返回success->false 和errMsg->报错信息
     * 为什么要使用rows 和total  因为前端框架要使用easyUI
     *
     */
    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<Area> listAreas = new ArrayList<>();
        try{
            listAreas  = areaService.getAreaList();
            modelMap.put("rows",listAreas);
            modelMap.put("total",listAreas.size());
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMap",e.toString());
        }
        return modelMap;
    }
}
