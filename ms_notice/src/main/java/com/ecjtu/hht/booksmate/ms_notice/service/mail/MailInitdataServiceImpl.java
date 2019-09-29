package com.ecjtu.hht.booksmate.ms_notice.service.mail;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.mail.MailInitdata;
import com.ecjtu.hht.booksmate.ms_notice.mapper.MailInitdataMapper;
import com.ecjtu.hht.booksmate.common.api.mail.MailService;
import com.ecjtu.hht.booksmate.common.api.mail.IMailInitdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-04-01
 */
@Service
public class MailInitdataServiceImpl extends ServiceImpl<MailInitdataMapper, MailInitdata> implements IMailInitdataService {

    @Autowired
    private MailService mailService;
    @Autowired
    private MailInitdataMapper mailInitdataMapper;

    /**
     * 保存
     *
     * @param mailInitdata
     */
    @Override
    public void saveMailInitDate(MailInitdata mailInitdata) {
        this.insert(mailInitdata);
    }

    /**
     * 发送邮件
     *
     * @param mailInitdatas
     */
    @Override
    public void sendMail(List<MailInitdata> mailInitdatas) {
        mailInitdatas.forEach(mailInitdata -> {
            //调用接口发送邮件
            mailService.sendMail(mailInitdata);
        });
    }

    @Override
    public void updateMailStatus(Integer id, String message, Date date, Integer sendStatus) {
        mailInitdataMapper.updateMailStatus(id, message, date, sendStatus);
    }
}
