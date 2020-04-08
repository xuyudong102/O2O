package com.xyd.controller.shopadmin;

import com.xyd.dto.ShopExecution;
import com.xyd.entity.Area;
import com.xyd.entity.Shop;
import com.xyd.entity.ShopCategory;
import com.xyd.service.AreaService;
import com.xyd.service.ShopCategoryService;
import com.xyd.service.ShopService;
import com.xyd.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.shopadmin
 * @date 2020/4/8
 * @description
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;


    /**
     * 初始化店铺列表和区域列表信息
     * 首页如果点击 全部类别 就是查询所有商品一级 如果点击单个类别 就是查询该类别下面的子类别
     * 返回商品列表里的ShopCategory列表(二级或者一级) 一级区域信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            //先从前端获取parentId
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            if (parentId != -1) {
                //如果传入parentId那么证明要查询的是二级类别列表
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } else {
                //查询一级类别列表
                shopCategoryList = shopCategoryService.getShopCategoryList(null);

            }
            areaList = areaService.getAreaList();
            modelMap.put("success", true);
            modelMap.put("areaList", areaList);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 获取指定查询条件下的店铺列表
     */
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //非空判断
        if (pageSize > -1 && pageIndex > -1) {
            //当主页点击全部类别 列表也展示所有一级类别  点击一级类别可得到parentId
            //试着获取一级类别id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //当主页点击某个一级类别 列表页展示该一级类别下面的子类别 点击子类别可得到shopCategoryId
            // 试着获取二级类别id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //试着获取区域id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //试着获取模糊查询名字
            String shopName = HttpServletRequestUtil.getString(request,"shopName");
            shopName = new String(shopName.getBytes("ISO-8859-1"),"UTF-8");
            //试着获取组合之后的查询条件
            Shop shopCondition =compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
            ShopExecution se =  shopService.getShopList(shopCondition,pageIndex,pageSize);
            modelMap.put("success",true);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("count",se.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    /**
     * 组合查询条件 并将条件封装到ShopCondition 对象里返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId > -1) {
            //如果parentId 有值的话 那么就是查询父类别的子类别对应的所有商品
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setParent(parent);
            shopCondition.setShopCategory(shopCategory);
        }
        if (shopCategoryId > -1) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId > -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        //前端展示的店铺都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
