package com.ecjtu.hht.booksmate.common.api.mail;


import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.common.entity.mail.MailInitdata;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-04-01
 */
public interface IMailInitdataService extends IService<MailInitdata> {

    /**
     * 保存
     * @param mailInitdata
     */
    void saveMailInitDate(MailInitdata mailInitdata);
    void sendMail(List<MailInitdata> mailInitdatas);

    void updateMailStatus(Integer id, String message, Date date, Integer sendStatus);
}
