package com.xyd.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Area;
import com.xyd.entity.PersonInfo;
import com.xyd.entity.Shop;
import com.xyd.entity.ShopCategory;
import com.xyd.enums.ShopStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.service.AreaService;
import com.xyd.service.ShopCategoryService;
import com.xyd.service.ShopService;
import com.xyd.utils.HttpServletRequestUtil;
import com.xyd.utils.ImageUtil;
import com.xyd.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
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
            PersonInfo owner = new PersonInfo();
            //Session TODO
            owner.setUserId(1);
            shop.setOwner(owner);
            //ShopExecution se = shopService.addShop(shop, ImageUtil.transferCommonsMultipartFileToFile(shopImg));
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if(se.getState()== ShopStateEnum.CHECK.getState()){
                    //成功
                    modelMap.put("success",true);
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

    /*private static void inputSteamToFile(InputStream ins, File file){
        //定义输出流
        FileOutputStream os = null;
        try{
            os=new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8*1024*1024];
            while((bytesRead=ins.read(buffer))!=-1){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("inputStreamToFile关闭io产生异常:"+e.getMessage());
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(ins!=null){
                    ins.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/
}
