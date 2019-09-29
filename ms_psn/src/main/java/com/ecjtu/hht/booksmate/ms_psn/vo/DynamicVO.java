package com.ecjtu.hht.booksmate.ms_psn.vo;


import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.DynComment;
import com.ecjtu.hht.booksmate.ms_psn.entity.Dynamic;

import java.util.List;

public class DynamicVO {
    private Dynamic dynamic;
    private Person person;
    private List<DynComment> dynComment;

    public List<DynComment> getDynComment() {
        return dynComment;
    }

    public void setDynComment(List<DynComment> dynComment) {
        this.dynComment = dynComment;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
