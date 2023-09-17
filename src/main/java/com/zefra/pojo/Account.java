package com.zefra.pojo;


public class Account {
    public String mail;//邮箱
    public String account;//账号名
    public String passWord;//密码

    public Account(String mail, String account, String passWord) {
        this.mail = mail;
        this.account = account;
        this.passWord = passWord;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Account{" +
                "mail='" + mail + '\'' +
                ", account='" + account + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
