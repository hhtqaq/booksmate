package com.ecjtu.hht.booksmate.ms_notice.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.common.entity.mail.MailInitdata;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-04-01
 */
public interface MailInitdataMapper extends BaseMapper<MailInitdata> {

    /**
     *
     * @param id
     * @param message
     * @param date
     * @param sendStatus
     */
    void updateMailStatus(Integer id, String message, Date date, int sendStatus);
}
