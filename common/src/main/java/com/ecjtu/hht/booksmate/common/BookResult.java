package com.ecjtu.hht.booksmate.common;

import java.io.Serializable;

public class BookResult implements  Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        // 响应业务状态
        private Integer status;

        // 响应消息
        private String msg;

        // 响应中的数据
        private Object data;

        //构建其他状态的taotaoresult对象
        public static BookResult build(Integer status, String msg, Object data) {
            return new BookResult(status, msg, data);
        }

        public static BookResult ok(Object data) {
            return new BookResult(data);
        }

        public static BookResult ok() {
            return new BookResult(null);
        }

        public BookResult() {

        }

        public static BookResult build(Integer status, String msg) {
            return new BookResult(status, msg, null);
        }

        public BookResult(Integer status, String msg, Object data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        public BookResult(Object data) {
            this.status = 200;
            this.msg = "OK";
            this.data = data;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
}
