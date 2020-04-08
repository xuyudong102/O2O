package com.xyd.mapper;

import com.xyd.entity.HeadLine;
import com.xyd.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.mapper
 * @date 2020/4/5
 * @description
 */

@Repository("headLineMapper")
public interface HeadLineMapper {

    /**
     * 根据传入的查询条件查询头条信息(头条名查询头条信息)
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
