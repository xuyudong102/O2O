package com.xyd.service;

import com.xyd.entity.Area;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/20
 * @description
 */

public interface AreaService {
    public List<Area> getAreaList();
}
