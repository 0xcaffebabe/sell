package wang.ismy.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MY
 * @date 2020/2/2 10:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoggerTest {

    Logger log = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test(){
        log.debug("debug");
        log.info("info {}",System.currentTimeMillis());
        log.error("error");
    }

}
