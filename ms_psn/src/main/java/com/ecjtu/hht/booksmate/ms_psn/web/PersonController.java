package com.ecjtu.hht.booksmate.ms_psn.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.ms_psn.service.IRecentAccessService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@RestController
public class PersonController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IPersonService personService;
    @Value("${avatorRoot}")
    private String avatorRoot;//头像上传路径
    @Value("${fileServer}")
    private String fileServer;
    @Autowired
    private IRecentAccessService recentAccessService;

    /*    @Autowired
        private IBookinfoService bookinfoService;
        @Autowired
        private IBorrowRegisterService borrowRegisterService;*/
    @Cacheable("psnInfo")
    @GetMapping("/mapper/person/getPersonInfoById/{id}")
    public BookResult getPersonById(@PathVariable("id") String id) {
        Person person = personService.selectById(id);
        return person != null ? BookResult.ok(person) : BookResult.build(400, "不存在的会员id");
    }

    @GetMapping("/mapper/person/all/{personstatus}")
    public ModelAndView getAllPerson(@PathVariable("personstatus") Integer status, ModelAndView modelAndView) {
        //正常
        List<Person> persons = personService.selectList(new EntityWrapper<Person>().eq("status", status));
        modelAndView.addObject("persons", persons);
        if (status == 0) {
            modelAndView.setViewName("admin_personinfolist");
        } else {
            modelAndView.setViewName("admin_blacklist");
        }
        return modelAndView;
    }

    /**
     * 点击跳到人员详情页面
     *
     * @param id
     * @param modelAndView
     * @return
     */
    @GetMapping("/mapper/person/{id}")
    public ModelAndView getPersonById(@PathVariable("id") String id, ModelAndView modelAndView) {
        Person person = personService.selectById(id);
        modelAndView.addObject("person", person);
        modelAndView.setViewName("admin_personinfodetail");
        return modelAndView;
    }

    @GetMapping("/mapper/person/recharge/{id}/{money}")
    public BookResult recharge(@PathVariable("id") Integer psnId, @PathVariable("money") Integer money) {

        Integer result = personService.recharge(psnId, money);

        return result > 0 ? BookResult.ok(result) : BookResult.build(400, "充值失败");
    }

    //显示个人主页
    @GetMapping("/psn/homepage/show")
    public ModelAndView showHomePage(ModelAndView modelAndView, String psnId, HttpServletRequest request) {

        //获取自己主页
        Person sessionPerson = (Person) request.getSession().getAttribute("mapper/person");
        Person person = personService.selectById(sessionPerson.getId());
        modelAndView.setViewName("front/homepage");
        //如果不为空且id不等于自己  代表再看他人页面
        if (StringUtils.isNotEmpty(psnId) && !psnId.equals(person.getId().toString())) {
            person = personService.selectById(psnId);
            //更新另一个人的访客记录
            recentAccessService.saveOrupdateRecent(psnId, sessionPerson.getId());
            //获取相似人员
            List<Person> similarPerson = personService.getSimilarPersonByOther(person, sessionPerson.getId());
            modelAndView.setViewName("front/homepage_other");
            modelAndView.addObject("similarPersons", similarPerson);
        }
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    /**
     * 更新人员信息
     *
     * @param person
     * @return
     */
    @GetMapping("/mapper/person/ajaxUpdatePersonInfo")
    public BookResult ajaxUpdatePersonInfo(Person person) {
        person.setUpdateDate(new Date());
        boolean b = personService.insertOrUpdate(person);
        return b == true ? BookResult.ok() : BookResult.build(400, "更新失败");
    }

    /**
     * 上传头像
     *
     * @param avatar
     * @param psnId
     * @return
     */
    @PostMapping("/mapper/person/uploadAvatar/{psnId}")
    public BookResult ajaxUploadPersonAvatar(HttpServletRequest request, @RequestParam("avatar") MultipartFile avatar, @PathVariable("psnId") Integer psnId) {
        if (avatar.isEmpty()) {
            return BookResult.build(500, "上传失败");
        }
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString().replaceAll("-", "") + avatar.getOriginalFilename();
        String avatarPath = avatorRoot + File.separator + filename;
        Path path = Paths.get(avatarPath);
        //更新个人头像
        Person person = new Person();
        person.setId(psnId);
        person.setAvatar(fileServer + "avator/" + filename);
        person.setUpdateDate(new Date());
        request.getSession().setAttribute("person", person);
        personService.insertOrUpdate(person);
        try {
            avatar.transferTo(path);
            return BookResult.ok(fileServer + "avator/" + filename);
        } catch (IOException e) {
            logger.error("-----头像上传失败————psnId=" + psnId, e);
            return BookResult.build(500, "上传失败");
        }
    }

    /**
     * 获取人员动态关注粉丝借阅DFB数目
     *
     * @param psnId
     * @return
     */
    @GetMapping("/mapper/person/ajaxGetPersonDFBCount")
    public BookResult ajaxGetPersonDFBCount(Integer psnId) {
        Map<String, Integer> countMap;
        try {
            countMap = personService.getGetPersonDFBCount(psnId);
        } catch (Exception e) {
            logger.error("-----人员信息统计数获取失败————psnId=" + psnId, e);
            return BookResult.build(400, "人员信息统计数获取失败");
        }
        return BookResult.ok(countMap);
    }

    /**
     * 获取所有人员信息的统计
     *
     * @param psnIds
     * @return
     */
    @GetMapping("/mapper/person/ajaxGetPersonsDFBCount")
    public BookResult ajaxGetPersonsDFBCount(@RequestParam(value = "pIds[]") Integer[] psnIds) {
        List<Map<String, Integer>> maps;
        try {
            maps = personService.getPersonsDFBCount(psnIds);
        } catch (Exception e) {
            logger.error("-----人员信息统计数获取失败————psnId=" + psnIds, e);
            return BookResult.build(400, "人员信息统计数获取失败");
        }
        return BookResult.ok(maps);
    }

    /*  */

    /**
     * 获取新书推荐
     *
     * @param modelAndView
     * @return
     *//*
    @GetMapping("/psn/ajaxGetRecommendBooks")
    public ModelAndView ajaxGetRecommendBooks(ModelAndView modelAndView) {
        List<Bookinfo> bookinfos = bookinfoService.getRecommendBooks(5);
        modelAndView.addObject("bookinfos", bookinfos);
        modelAndView.setViewName("front/dyn_bookrecommend");
        return modelAndView;
    }

    @GetMapping("/person/ajaxGetBorrowHistory")
    public ModelAndView ajaxGetBorrowHistory(ModelAndView modelAndView, Integer psnId) {
        List<BorrowRegisterVO> allBorrowRegisterHistory = borrowRegisterService.getAllBorrowRegisterHistory(psnId, 6);
        modelAndView.addObject("borrowHistorys", allBorrowRegisterHistory);
        modelAndView.setViewName("front/homepage_borrowhistory");
        return modelAndView;
    }*/
    @PostMapping("/doLogin")
    public ModelAndView login(ModelAndView modelAndView, String username, String password) {
        if ("admin".equals(username) && "123".equals(password)) {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping("/tologin")
    public ModelAndView toLogin(ModelAndView modelAndView, String username, String password) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @GetMapping("/person/list")
    public BookResult getAllPerson(){
        return personService.getAllPerson();
    }
}
