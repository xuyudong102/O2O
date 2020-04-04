package com.xyd.service.impl;

import com.xyd.dto.ImageHolder;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Shop;
import com.xyd.enums.ShopStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.mapper.ShopMapper;
import com.xyd.service.ShopService;
import com.xyd.utils.ImageUtil;
import com.xyd.utils.PageCalculator;
import com.xyd.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

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
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        //空值判断
        if (shop == null) {
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
            if (effectedNum <= 0) {
                //添加失败
                throw new ShopOperationException("店铺创建失败 数据库插入失败");
            } else {
                //添加成功
                if (thumbnail.getImage() != null) {
                    //本地存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("本地图片存储失败" + e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopMapper.updateShop(shop);
                    if (effectedNum <= 0) {
                        //更新失败
                        throw new ShopOperationException("数据库图片地址更新失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }


    @Override
    public Shop getByShopId(long shopId) {
        return shopMapper.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        //判断是否需要处理图片
        if (shop == null || shop.getShopId() == null) {
            //直接返回错误信息
            return new ShopExecution(ShopStateEnum.NUll_SHOP);
        } else {
            try {
                //如果不为空 判断是否有文件流 或者 (流文件的文件名)
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopMapper.queryByShopId(shop.getShopId());
                    //如果都有的话
                    if (tempShop.getShopImg() != null) {
                        //以前有图片  先删除图片
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    //传入的是文件流的名字  再向本地添加文件返回图片相对路径
                    addShopImg(shop, thumbnail);
                }
                //文件操作成功后 向数据库添加其他数据
                shop.setLastEditTime(new Date());
                //然后将数据修改到数据库中
                int effectedNum = shopMapper.updateShop(shop);
                if (effectedNum <= 0) {
                    //插入失败
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopMapper.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("图片修改失败" + e.getMessage());
            }
        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //pageIndex 代表第几页
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopMapper.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopMapper.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList!=null){
            //如果店铺列表不为空
            //todo 如果没有找到店铺列表 前台怎么知道? 这里不写se.setState吗?
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }


    public void addShopImg(Shop shop, ImageHolder thumbnail) {
        //根据商店id 获得路径  创建本地图片文件
        String target = PathUtil.getShopImgPath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail.getImage() , target,thumbnail.getImageName());
        shop.setShopImg(shopImgAddr);
    }


}
