package wang.ismy.sell.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisLockTest {

    @Autowired
    RedisLock redisLock;

    private static int i =500;

    class MyThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String key = System.currentTimeMillis()+"";
            redisLock.lock("lock",key);
            i--;
            redisLock.unlock("lock",key);
        }
    }

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < 500; i++) {
            new MyThread().start();
        }
        Thread.sleep(5000);
        log.info("结果:{}",i);
    }
}