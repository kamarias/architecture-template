package io.github.kamarias.domain;


import org.springframework.util.StringUtils;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:34
 */
public class User {


    private String userName;


    private String passWord;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

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


    public void handler(){
        if (!StringUtils.hasText(userName)){
            System.out.println("用户名不能为空");
            throw new RuntimeException("用户名不能为空");
        }
        if (!StringUtils.hasText(passWord)){
            System.out.println("密码不能为空");
            throw new RuntimeException("密码不能为空");
        }

    }

}
