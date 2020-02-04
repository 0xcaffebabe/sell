package wang.ismy.sell.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.entity.SellerInfo;
import wang.ismy.sell.utils.KeyUtils;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoRepositoryTest {

    @Autowired
    SellerInfoRepository repository;

    @Test
    @Transactional
    public void test(){
        saveOne();
        assertEquals("蔡徐坤",repository.findByOpenid("715").getUsername());
    }

    private void saveOne(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtils.generateUniqueKey());
        sellerInfo.setUsername("蔡徐坤");
        sellerInfo.setPassword("715");
        sellerInfo.setOpenid("715");
        repository.save(sellerInfo);
    }



}