package lvxixiao.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lvxixiao.pojo.User;
import lvxixiao.service.UserService;

@Controller
public class UserController {
	
	@Resource
	UserService userService;
	//用户登陆
	@RequestMapping(value="/signin",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String signin(HttpServletRequest request,User user,HttpServletResponse response) {
		
		return userService.checkUser(request,user,response);
	}
	//校验Token
	@RequestMapping(value="/admin/checkToken",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String checkToken(@CookieValue(value="token",required=false) String token) {
		
		return userService.checkToken(token);
	}
	@RequestMapping(value="/admin/updateAccount",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String updateAccount(HttpServletRequest request,User user,@RequestParam String newPassword) {
		return userService.updateAccount(request,user,newPassword);
	}
}
