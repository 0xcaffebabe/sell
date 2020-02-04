package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.sell.pojo.entity.SellerInfo;
import wang.ismy.sell.repository.SellerInfoRepository;

/**
 * @author MY
 * @date 2020/2/4 11:25
 */
@Service
@AllArgsConstructor
public class SellerService {

    private SellerInfoRepository repository;

    public SellerInfo findSellerInfoByOpenid(String openid){
        return repository.findByOpenid(openid);
    }
}
