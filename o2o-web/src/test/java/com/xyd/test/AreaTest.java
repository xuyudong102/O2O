package com.xyd.test;

import com.xyd.BaseTest;
import com.xyd.entity.Area;
import com.xyd.mapper.AreaMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.test
 * @date 2020/3/19
 * @description
 */
public class AreaTest extends BaseTest {
    @Autowired
    private AreaMapper areaMapper;
    @Test
    public void testQueryArea(){
        List<Area> areaList = areaMapper.queryArea();
        System.out.println(areaList);
    }
}
