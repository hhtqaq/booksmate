package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.mapper.PersonMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.IDynamicService;
import com.ecjtu.hht.booksmate.ms_psn.service.IFollowersService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private IDynamicService dynamicService;
    @Autowired
    private IFollowersService followersService;
   /* @Autowired
    private IBorrowRegisterService borrowRegisterService;*/

    /**
     * 根据psnid 充值
     *
     * @param psnId
     * @param money
     * @return
     */
    @Transactional
    @Override
    public Integer recharge(Integer psnId, Integer money) {
        if (money < 0) {
            return 0;
        }
        Person person = personMapper.selectById(psnId);
        int account = person.getAccount() == null ? 0 : person.getAccount();
        person.setAccount(account + money);
        Integer update = personMapper.updateById(person);
        if (update > 0) {
            return person.getAccount();
        }
        return 0;
    }

    /**
     * 获取人员动态粉丝数量
     *
     * @param psnId
     * @return
     */
    @Override
    public Map<String, Integer> getGetPersonDFBCount(Integer psnId) throws Exception {
        /*Map<String, Integer> countMap = new HashMap<>();
        //1获取动态数量
        int dynCount = dynamicService.getCountByPsnId(psnId, 1);
        //2获取粉丝数量
        int followeringCount = followersService.getCountByFollowId(psnId, 1);
        //3 获取关注数量
        int followersCount = followersService.getCountByPsnId(psnId, 1);
        //4获取借阅数量
        int borrowCount = borrowRegisterService.getBorrowCountByReaderId(psnId);
        countMap.put("dynCount", dynCount);
        countMap.put("followeringCount", followeringCount);
        countMap.put("followersCount", followersCount);
        countMap.put("borrowCount", borrowCount);
        return countMap;*/
        return null;
    }

    /**
     * 获取所有的人员的统计数
     *
     * @param psnIds
     * @return
     */
    @Override
    public List<Map<String, Integer>> getPersonsDFBCount(Integer[] psnIds) throws Exception {
        List<Map<String, Integer>> countMapList = new ArrayList<>();
        for (int i = 0; i < psnIds.length; i++) {
            Map<String, Integer> countMap = this.getGetPersonDFBCount(psnIds[i]);
            countMap.put("psnId", psnIds[i]);
            countMapList.add(countMap);
        }
        return countMapList;
    }

    /**
     * 获取相似人员 根据当前所看的人员
     *
     * @param person
     * @param id
     * @return
     */
    @Override
    public List<Person> getSimilarPersonByOther(Person person, Integer id) {
        return personMapper.getSimilarPersonByOther(person, id);
    }

    /**
     * 获取最多阅读数排行榜
     *
     * @param size
     * @return
     */
    @Override
    public List<Map<String, Object>> getMaxReadPsns(int size) {
     /*  List<Map<String, Integer>> psnIdMap = borrowRegisterService.getMaxReadPsnIds(size);
        List<Map<String, Object>> maps = new ArrayList<>();
        psnIdMap.forEach(map -> {
                    Map<String, Object> personMap = new HashMap<>();
                    Person person = personMapper.selectById(map.get("readerId"));
                    personMap.put("count", map.get("count"));
                    personMap.put("person", person);
            maps.add(personMap);
                }
        );

        return maps;
    */
        return null;
    }

}
