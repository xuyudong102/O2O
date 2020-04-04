package com.xyd.service.impl;

import com.xyd.dto.ImageHolder;
import com.xyd.dto.ProductExecution;
import com.xyd.entity.Product;
import com.xyd.entity.ProductImg;
import com.xyd.enums.ProductStateEnum;
import com.xyd.exceptions.ProductOperationException;
import com.xyd.mapper.ProductImgMapper;
import com.xyd.mapper.ProductMapper;
import com.xyd.service.ProductService;
import com.xyd.utils.ImageUtil;
import com.xyd.utils.PageCalculator;
import com.xyd.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service.impl
 * @date 2020/3/31
 * @description
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductImgMapper productImgMapper;

    /**
     * 1.先把缩略图 图片插入到本地相册 把图片地址设置给对象
     * 2.插入商品信息到数据库并且返回商品id
     * 3.插入详情图片到本地并且记录图片地址
     * 4.根据商品id 和详情图片地址集合插入商品图片信息到数据库
     *
     * @param product        商品图片
     * @param thumbnail      商品缩略图
     * @param productImgList 商品详情图片
     * @return
     * @throws ProductOperationException
     */
    @Transactional
    @Override
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //可以插入图片 插入缩略图片到本地
            if (thumbnail.getImage() != null) {
                //插入图片到本地
                addThumbnail(product, thumbnail);
                //设置图片其他属性
                try {
                    //默认状态为上架状态
                    product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
                    product.setCreateTime(new Date());
                    product.setLastEditTime(new Date());
                    product.setPriority(1);
                    //将商品对象插入数据库中 并返回商品id存入对象
                    int effectedNum = productMapper.insertProduct(product);
                    if (effectedNum <= 0) {
                        //商品信息向数据库插入失败
                        throw new ProductOperationException("商品信息插入失败");
                    }
                    //商品信息插入成功 将图片向本地插入
                    if (productImgList != null) {
                         List<ProductImg> productImgs=addProductImgList(productImgList, product);
                         //将图片列表插入数据库
                        int effectNum2 = productImgMapper.batchInsertProductImg(productImgs);
                        if(effectNum2<=0){
                            throw new ProductOperationException("详情图插入数据库失败");
                        }
                        return new ProductExecution(ProductStateEnum.SUCCESS);
                    } else {
                        //商品信息详情流不存在
                        return new ProductExecution(ProductStateEnum.EMPTY_ERROR);
                    }
                } catch (Exception e) {
                    throw new ProductOperationException("信息插入失败");
                }
            } else {
                //如果图片为空 返回空值错误信息
                return new ProductExecution(ProductStateEnum.EMPTY_ERROR);
            }
        }
        return null;
    }

    /**
     * 根据id查询商品信息
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(long productId) {
        return productMapper.queryProductById(productId);
    }

    /**
     *修改商品信息
     * 1.若缩略图有值 则处理缩略图
     * 若先存在缩略图 则先删除再添加新图  之后获取缩略图相对路径并赋值给product
     * 2.若商品详情图列表参数有值 对商品详情列表做同样的操作
     * 3.将tb_product_img下面的该商品原乡详情图记录删除
     * 4.更新tb_product 以及tb_product_img 的信息
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            //如果都有值 可以开始修改
            //给商品设置上默认值
            product.setLastEditTime(new Date());
            //先判断缩略图有没有值 没有值的话就不管他
            if(thumbnail!=null){
                //先删除原来本地的缩略图  先获取原有信息
                Product tempProduct = productMapper.queryProductById(product.getProductId());
                //根据图片路径删除本地缩略图信息
                if(tempProduct.getImgAddr()!=null){
                    //如果有 就先删除本地图片
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                //删除本地图片之后 把缩略图添加到本地
                addThumbnail(product,thumbnail);
            }
            //判断有没有商品详情图文件流
            if(productImgList!=null&&productImgList.size()>0){
                //如果商品详情流里面有数据
                //删除本地详情图片
                deleteProductImgList(product.getProductId());
                //添加文件流到本地详情图片
                List<ProductImg> productImgs = addProductImgList(productImgList,product);
                //把详情图片信息添加到数据库中
                int effectNum2 = productImgMapper.batchInsertProductImg(productImgs);
                if(effectNum2<=0){
                    throw new ProductOperationException("详情图插入数据库失败");
                }
            }
            //其他信息跟新到数据库
            try{
                int effectedNum =  productMapper.updateProduct(product);
                if(effectedNum>0){
                    //更新成功
                    return new ProductExecution(ProductStateEnum.SUCCESS,product);
                }else{
                    //更新失败
                    throw new ProductOperationException("更新商品信息失败!");
                }
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败!"+e.toString());
            }
        }
        return new ProductExecution(ProductStateEnum.EMPTY_ERROR);
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        //查询商品列表
        List<Product> productList = productMapper.queryProductList(productCondition,rowIndex,pageSize);
        //查询商品总数
        int count = productMapper.queryProductCount(productCondition);
        //把查询到的包装成dto类
        ProductExecution productExecution = new ProductExecution();
        productExecution.setCount(count);
        productExecution.setProductList(productList);
        return productExecution;
    }


    /**
     * 添加缩略图到本地
     *
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        //根据shopId 获取相对路径
        try {
            String target = PathUtil.getShopImgPath(product.getShop().getShopId());
            String newImgPath = ImageUtil.generateThumbnail(thumbnail.getImage(), target, thumbnail.getImageName());
            product.setImgAddr(newImgPath);
        } catch (Exception e) {
            throw new ProductOperationException(e.getMessage() + "商品缩略图本地添加失败");
        }

    }

    /**
     * 删除本地的商品详情图片
     */
    private void deleteProductImgList(long productId){
        //先去查询productImgList 商品图片的路径
        List<ProductImg>productImgList = productImgMapper.queryProductImgByProductId(productId);
        //先遍历详情图片 删除本地详情图片
        if(productImgList.size()>0){
            //如果有详情图片的话
            for(ProductImg productImg:productImgList){
                //全部删除本地图片
                ImageUtil.deleteFileOrPath(productImg.getImgAddr());
            }
            //删除原来数据库中的图片
            productImgMapper.deleteProductImgByProductId(productId);
        }
    }


    /**
     * 添加商品详情图片到本地
     */
    private List<ProductImg> addProductImgList(List<ImageHolder> productImgList, Product product) {
        //获得相对路径
        String target = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgs = new ArrayList<>();
        int i = 0;
        for (ImageHolder productImg : productImgList) {
            try{
                ProductImg productImg1 = new ProductImg();
                String newImgAddr = ImageUtil.generateNormalImg(productImg.getImage(), target, productImg.getImageName());
                productImg1.setProductId(product.getProductId());
                productImg1.setCreateTime(new Date());
                i++;
                productImg1.setImgDesc("商品号" + product.getProductId() + "的" + i + "号图片");
                productImg1.setImgAddr(newImgAddr);
                productImg1.setPriority(i);
                productImgs.add(productImg1);
            }catch (Exception e){
                //如果产生异常直接抛出
                throw new ProductOperationException("商品详情图片本地插入异常");
            }
        }
        return productImgs;
    }
}

