package com.xyd.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Area;
import com.xyd.entity.PersonInfo;
import com.xyd.entity.Shop;
import com.xyd.entity.ShopCategory;
import com.xyd.enums.ShopStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.mapper.ShopMapper;
import com.xyd.service.AreaService;
import com.xyd.service.ShopCategoryService;
import com.xyd.service.ShopService;
import com.xyd.utils.CodeUtil;
import com.xyd.utils.HttpServletRequestUtil;
import com.xyd.utils.ImageUtil;
import com.xyd.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.shopadmin
 * @date 2020/3/23
 * @description
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private  ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    //获取区域 以及店铺类别的信息将他返回给前台
    //需要选出parentid 不为空的 一级类别不能做为店铺类别
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList= new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            //选出 所有shopcategory 里面的parentid不为空的
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("success",true);
            modelMap.put("areaList",areaList);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            System.out.println("初始化查询店铺类别和区域信息失败");
        }
        return modelMap;
    }


    /**
     * 注册店铺 将店铺信息添加到数据库中
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //如果两个验证码不相同的话 modelMap直接返回验证码不正确
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码不正确!!");
            return modelMap;
        }

        //1.接收并转换相应的参数 包括店铺信息 以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        //把json对象转换成 java对象
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        //获取文件流
        CommonsMultipartFile shopImg = null;
        //把文件流剥离出来
        //需要文件解析器 去解析文件信息
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断是否有上传的文件流
        if(commonsMultipartResolver.isMultipart(request)){
            //有文件流
            //强制转换成文件流请求
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else{
            //没有文件流  没有上传图片
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if(shop!=null&&shopImg!=null) {
            //通过session回话传进来的
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            //Session TODO
            //owner.setUserId(1);
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if(se.getState()== ShopStateEnum.CHECK.getState()){
                    //成功
                    modelMap.put("success",true);
                    //添加成功后 需要把信息填入session
                    List<Shop> shopList = (List) request.getSession().getAttribute("shopList");
                    if(shopList==null||shopList.size()==0){
                        shopList = new ArrayList();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                    return modelMap;
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                    return modelMap;
                }
            }catch (ShopOperationException e) {
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }catch (IOException e) {
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }else{
            //报错返回
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
            //3.返回结果
    }

    /**
     * 修改店铺信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //如果两个验证码不相同的话 modelMap直接返回验证码不正确
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码不正确!!");
            return modelMap;
        }

        //1.接收并转换相应的参数 包括店铺信息 以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        //把json对象转换成 java对象
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        //获取文件流
        CommonsMultipartFile shopImg = null;
        //把文件流剥离出来
        //需要文件解析器 去解析文件信息
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断是否有上传的文件流
        //修改操作可以不上传文件
        if(commonsMultipartResolver.isMultipart(request)){
            //有文件流
            //强制转换成文件流请求
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店铺信息
        if(shop!=null&&shop.getShopId()!=null) {

            ShopExecution se = null;
            try {
                if(shopImg==null){
                    se = shopService.modifyShop(shop, null, null);
                }else {
                    se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                }

                if(se.getState()== ShopStateEnum.SUCCESS.getState()){
                    //成功
                    modelMap.put("success",true);

                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            }catch (ShopOperationException e) {
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }catch (IOException e) {
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else{
            //报错返回
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入要修改的店铺ID");
        }
        //3.返回结果
        return modelMap;
    }



    /**
     *通过id查询店铺信息
     * @return
     */
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        Map modelMap = new HashMap();
        if(shopId>-1){
            try {
                //如果有shopid的话
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else{
            //直接返回失败信息
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

}
