package wang.ismy.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.sell.pojo.entity.SellerInfo;

/**
 * @author MY
 * @date 2020/2/4 11:22
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}
