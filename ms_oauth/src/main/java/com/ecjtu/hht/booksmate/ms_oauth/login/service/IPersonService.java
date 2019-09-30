package com.ecjtu.hht.booksmate.ms_oauth.login.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@FeignClient(name = "ms-psn")
public interface IPersonService extends IService<Person> {

    /**
     * 根据psnid 充值
     * @param psnId
     * @param money
     * @return
     */
    Integer recharge(Integer psnId, Integer money);

    /**
     * 获取人员动态粉丝数量
     * @param psnId
     * @return
     */
    Map<String,Integer> getGetPersonDFBCount(Integer psnId) throws Exception;

    /**
     * 获取所有的人员的统计数
     * @param psnIds
     * @return
     */
    List<Map<String, Integer>> getPersonsDFBCount(Integer[] psnIds) throws Exception;

    /**
     * 获取相似人员 根据当前所看的人员
     * @param person
     * @param id
     * @return
     */
    List<Person> getSimilarPersonByOther(Person person, Integer id);

    /**
     * 获取最多阅读数排行榜
     * @param size
     * @return
     */
    List<Map<String, Object>> getMaxReadPsns(int size);

    /**
     * 检验登录密码
     * @param email 邮箱
     * @param password 密码
     * @return
     */
    Person checkPassword(String email, String password);
    @GetMapping("/person/list")
    BookResult getAllPerson();
}
