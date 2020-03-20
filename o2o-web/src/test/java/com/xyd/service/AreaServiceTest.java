package com.xyd.service;

import com.xyd.BaseTest;
import com.xyd.entity.Area;
import com.xyd.mapper.AreaMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/20
 * @description
 */
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
      List<Area> areaList = areaService.getAreaList();
      System.out.println(areaList);
    }
}
