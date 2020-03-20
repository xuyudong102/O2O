package com.xyd.service.impl;

import com.xyd.entity.Area;
import com.xyd.mapper.AreaMapper;
import com.xyd.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service.impl
 * @date 2020/3/20
 * @description
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<Area> getAreaList() {
        List<Area>areaList = areaMapper.queryArea();
        return areaList;
    }
}
