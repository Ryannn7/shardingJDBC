package com.itheima;

import com.itheima.domain.Order;
import com.itheima.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingQuickApplicationTests {

    @Autowired
    private OrderMapper orderMapper;
    //--------------水平分表
    //测试新增
    @Test
    public void testInsertOrder(){

        for (int i = 0; i < 10; i++) {
            Order order=new Order();
            order.setPrice(new BigDecimal((i+1)*5));
            order.setStatus("success"+"|"+i);
            order.setUserId(1L);
            orderMapper.inserOrder(order);
        }
    }
    //根据id查询多个订单
    @Test
    public void testSelect(){
        List<Long> orderIds=new ArrayList<>();
        orderIds.add(851494501452087296L);
        orderIds.add(851494500839718913L);
        orderMapper.selectListByOrderIds(orderIds);
    }
    //-------------------水平分库
    @Test
    public void testInsertTable(){

        for (int i = 0; i < 10; i++) {
            Order order=new Order();
            order.setPrice(new BigDecimal((i+1)*5));
            order.setStatus("WAIT_PAY"+"|"+i);
            order.setUserId(1L);
            orderMapper.inserOrder(order);
        }
        for (int i = 0; i < 10; i++) {
            Order order=new Order();
            order.setPrice(new BigDecimal((i+1)*10));
            order.setStatus("WAIT_PAY"+"|"+i);
            order.setUserId(2L);
            orderMapper.inserOrder(order);
        }
    }

    //根据orderId和userId查询多个订单
    @Test
    public void testSelectByUserIdAndOrderId(){
        List<Long> orderIds=new ArrayList<>();
        orderIds.add(851495563378556928L);//在order_db_2库中t_order_1表
        orderIds.add(851494501707939841L);//在order_db_2库中t_order_2表
        Long userId=1L;
        List<Map> maps = orderMapper.selectListByUserIdAndOrderIds(userId, orderIds);
        System.out.println(maps);
    }

}
