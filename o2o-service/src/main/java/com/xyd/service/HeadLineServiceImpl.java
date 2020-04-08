package com.xyd.service;

import com.xyd.entity.HeadLine;
import com.xyd.mapper.HeadLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/4/5
 * @description
 */
@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineMapper headLineMapper;
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineMapper.queryHeadLine(headLineCondition);
    }
}
