package wang.ismy.sell.lock;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author MY
 * @date 2020/2/4 16:33
 */
@Component
@Slf4j
@AllArgsConstructor
public class RedisLock {

    private StringRedisTemplate redisTemplate;

    public boolean lock(String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        while (true){
            // 不断获取锁，直至成功
            String currentValue = redisTemplate.opsForValue().get(key);
            // 锁过期
            if (!StringUtils.isEmpty(currentValue)
                    && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                // 上一个锁的时间
                String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
                if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                    return true;
                }

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void unlock(String key, String value) {
        String currentValue = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
            redisTemplate.delete(key);
        }
    }
}
