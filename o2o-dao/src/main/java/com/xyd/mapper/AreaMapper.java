package com.xyd.mapper;

import com.xyd.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.mapper
 * @date 2020/3/19
 * @description
 */
@Repository("areaMapper")
public interface AreaMapper {
    /**
     *
     * @return
     */
    List<Area> queryArea();
}
