/**
 * 从后台获取到店铺列表和区域区域信息 填写到前台表单中
 * 从前台获取到表单信息 填充到后台
 */
$(function () {
    //获取初始化信息
    var initUrl="/o2o/shopadmin/getshopinitinfo";
    //添加店铺信息的提交路径
    var registerShopUrl="/o2o/shopadmin/registershop";
    //js文件加载时调用初始化方法
    alert(initUrl);
    getShopInitInfo();


    //后台获取到区域信息 和店铺信息并且填充到前台控件中去
    function getShopInitInfo(){
        $.getJSON(initUrl,function (data) {
            if(data.success){
                //返回信息 成功
                var tempHtml="";
                var tempAreaHtml="";
                data.shopCategoryList.map(function(item,index){
                    tempHtml+='<option data-id="'+item.shopCategoryId+'">'+
                        item.shopCategoryName+'</option>';
                })
                data.areaList.map(function(item,index){
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'+
                        item.areaName+'</option>';
                });
                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
            //返回信息失败
        });
    }

    //为提交按添加点击事件
    $("#submit").click(function () {
        //当点击提交按钮的时候
        var shop={};
        shop.shopName=$("#shop-name").val();
        shop.shopAddr=$("#shop-addr").val();
        shop.phone=$("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        shop.shopCategory={
            //被选中的对象的值 被封装在对象中
            shopCategoryId:$("#shop-category").find("option").not(function(){return !this.selected}).data("id")
        };
        shop.area = {
            areaId:$("#area").find("option").not(function(){return !this.selected}).data("id")
        }
        var shopImg = $("#shop-img")[0].files[0];
        var formData = new FormData();
        formData.append("shopImg",shopImg);
        formData.append("shopStr",JSON.stringify(shop));
        $.ajax({
            url:registerShopUrl,
            type:'POST',
            data:formData,
            contentType:false,
            proceesData:false,
            cache:false,
            success:function(data){
                if(data.success){
                    //添加成功
                    $.toast("提交成功!");
                }else{
                    $.toast("提交失败!"+data.errMsg);
                }
            }
        })
    });
})
