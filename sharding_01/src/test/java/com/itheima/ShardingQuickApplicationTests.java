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
        orderIds.add(491708667008122880L);
        orderIds.add(491708666341228545L);
        orderMapper.selectListByOrderIds(orderIds);
    }


}
