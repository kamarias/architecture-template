package io.github.kamarias.model;

import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:31
 */
public class UserDto implements Serializable {


    private String userName;


    private String passWord;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}
