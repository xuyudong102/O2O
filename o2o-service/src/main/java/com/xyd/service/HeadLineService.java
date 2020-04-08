package com.xyd.service;

import com.xyd.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/4/5
 * @description
 */
public interface HeadLineService {

    /**
     * 根据查询条件返回头条列表(头条状态值)
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition)throws IOException;
}
