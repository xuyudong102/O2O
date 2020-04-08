package com.xyd.dao;

import com.xyd.BaseTest;
import com.xyd.entity.HeadLine;
import com.xyd.mapper.HeadLineMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dao
 * @date 2020/4/5
 * @description
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineMapper headLineMapper;
    @Test
    public void testAQueryHeadLine() {
        List<HeadLine> headLineList = headLineMapper.queryHeadLine(new HeadLine());
        Assert.assertEquals(1,headLineList.size());
    }
}
