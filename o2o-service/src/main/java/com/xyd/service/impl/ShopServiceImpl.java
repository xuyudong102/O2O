package com.xyd.service.impl;

import com.sun.javafx.scene.shape.PathUtils;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Shop;
import com.xyd.enums.ShopStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.mapper.ShopMapper;
import com.xyd.service.ShopService;
import com.xyd.utils.ImageUtil;
import com.xyd.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service.impl
 * @date 2020/3/22
 * @description
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    @Transactional
    //因为只有runtimeexception 才能支持事务
    public ShopExecution addShop(Shop shop, InputStream shopImgInputSteam, String fileName) {
        //空值判断
        if(shop==null){
            //如果shop为空直接返回
            return new ShopExecution(ShopStateEnum.NUll_SHOP);
        } //对shop_id 也要进行判断
        try {
        //给店铺信息赋初始值
            //0表示未上架
            shop.setEnableStatus(0);
            shop.setCreatTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopMapper.insertShop(shop);
            if(effectedNum<=0){
                //添加失败
                throw new ShopOperationException("店铺创建失败 数据库插入失败");
            }else{
                //添加成功
                if(shopImgInputSteam!=null){
                    //本地存储图片
                    try{
                        addShopImg(shop,shopImgInputSteam,fileName);
                    }catch (Exception e){
                        throw  new ShopOperationException("本地图片存储失败"+e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopMapper.updateShop(shop);
                    if(effectedNum<=0){
                        //更新失败
                        throw new ShopOperationException("数据库图片地址更新失败");
                }
                }
            }
        }catch (Exception e){
            throw  new ShopOperationException("addShop error"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }


    public void addShopImg(Shop shop,InputStream shopImgInputSteam,String fileName){
        //根据商店id 获得路径  创建本地图片文件
        String target = PathUtil.getShopImgPath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputSteam, target,fileName);
        shop.setShopImg(shopImgAddr);
    }


}
