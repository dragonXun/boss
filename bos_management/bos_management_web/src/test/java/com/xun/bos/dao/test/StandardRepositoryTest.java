package com.xun.bos.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xun.bos.dao.StandardRepository;
import com.xun.bos.domain.base.Standard;


/**  
 * ClassName:standardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午3:52:42 <br/>       
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    @Autowired
    private StandardRepository standardRepository;
    @Test
    public void testSave(){
        Standard standard = new Standard();
        standard.setName("wangwu");
        standard.setMaxLength(150);
        standardRepository.save(standard);
    }
    @Test
    public void testUpdate(){
        Standard standard = new Standard();
        standard.setId(2L);
        standard.setName("zhangsan");
        standard.setMaxWeight(100);
        standardRepository.save(standard);
    }
    @Test
    public void testDelete(){
        Standard entity = new Standard();
        entity.setId(1L);
        standardRepository.delete(entity);
    }
    @Test
    public void testFind(){
        Standard s = standardRepository.findOne(1L);
        System.out.println(s);
    }
   /* @Test
    public void test01(){
        List<Standard> list = standardRepository.fin("lisi");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void test02(){
        List<Standard> list = standardRepository.findby("%s%");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }*/
}
  
