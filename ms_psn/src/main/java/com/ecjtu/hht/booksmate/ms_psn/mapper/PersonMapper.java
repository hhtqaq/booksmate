package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.common.entity.person.Person;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface PersonMapper extends BaseMapper<Person> {


    Person doLogin(String email, String shapassword);

    List<Person> getSimilarPersonByOther(Person person, Integer psnId);


    List<Person> getMaxReadPsns(int size);


    Person getPersonByEmail(String email);
}
