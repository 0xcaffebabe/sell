package wang.ismy.sell.utils;

import java.util.UUID;

/**
 * @author MY
 * @date 2020/2/2 18:39
 */
public class KeyUtils {

    /**
     * 生成唯一主键
     * @return
     */
    public static String generateUniqueKey(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
