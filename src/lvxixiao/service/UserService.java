package lvxixiao.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lvxixiao.pojo.User;

public interface UserService {

	String checkUser(HttpServletRequest request,User user, HttpServletResponse response);

	String checkToken(String token);

	String updateAccount(HttpServletRequest request,User user, String oldPassword);



}
