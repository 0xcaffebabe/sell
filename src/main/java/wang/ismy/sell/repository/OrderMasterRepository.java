package wang.ismy.sell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.sell.pojo.entity.OrderMaster;

/**
 * @author MY
 * @date 2020/2/2 16:14
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     * 分页查询某位买家的订单
     * @param openid
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);


}
