/**
 *
 * */
$(function () {
    getlist();
    //初始化的一下方法
    function getlist(e) {
        $.ajax({
            url:"/o2o/shopadmin/getshoplist",
            type:"get",
            dataType:"json",
            success:function (data) {
                if(data.success){
                    //如果成功请求后台 带回来shoplist
                    //渲染shopList
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }

    //显示名字
    function handleUser(data){
        $("#user-name").text(data.name);
    }

    //渲染列表信息
    function handleList(data) {
        var tempHtml = "";
        data.map(function (item,index) {
            tempHtml+='<div class="row row-shop"><div class="col-40" >'+item.shopName+'</div>' +
                '<div class="col-40">'+goEnableStatus(item.enableStatus)+'</div>'+
                '<div class="col-20">'+goShop(item.enableStatus,item.shopId)+'</div></div>';
        })
        $(".shop-wrap").html(tempHtml);
    }

    //根据状态返回相应状态信息
    function goEnableStatus(status){
        if(status==-1){
            return "店铺非法";
        }else if(status==0){
            return "审核中";
        }else{
            return "已通过";
        }
    }

    //拼接店铺管理连接
    function goShop(status,id) {
        if(status==1){
            //如果是可用状态
            return '<a  href="/o2o/shopadmin/shopmanagement?shopId='+id+'">进入</a>';
        }else{
            return '';
        }
    }

})