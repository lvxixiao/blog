package lvxixiao.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lvxixiao.mapper.UserMapper;
import lvxixiao.pojo.User;
import lvxixiao.service.UserService;
import lvxixiao.util.Jwt;
import lvxixiao.util.Tools;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Resource
	UserMapper userMapper;
	
	@Resource
	Gson gson;
	
	private static final long TWO_HOUR = 1000 * 60 * 60 * 2L;
	
	@Override
	public String checkUser(HttpServletRequest request,User user, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String,Object>();
		String token;
		user.setPassword(Tools.MD5(user.getPassword()));
		user = userMapper.findUserByuser(user);
		if(user != null) {		
			Map<String, Object> payload = new HashMap<String,Object>();
			Date date = new Date();
			payload.put("username", user.getusername());
			payload.put("iat", date.getTime());
			payload.put("ext", date.getTime() + TWO_HOUR);
			token = Jwt.createToken(payload);
			
			Cookie cookie = new Cookie("token",token);
			cookie.setPath("/lvxixiao/admin");
			response.addCookie(cookie);
			
			jsonMap.put("status", "200");
			logger.info("管理员登,登陆地址:"+Tools.getIpAddr(request));
			return gson.toJson(jsonMap);
		}
		else {
			jsonMap.put("status", "400");
			return gson.toJson(jsonMap);
		}
	}

	@Override
	public String checkToken(String token) {
		if(token != null) {
			Map<String,Object> validToken = Jwt.validToken(token);
			return gson.toJson(validToken);
		} else {
			return "{}";
		}
	}

	@Override
	public String updateAccount(HttpServletRequest request,User user, String newPassword) {
		String oldPassword = Tools.MD5(user.getPassword());
		user.setPassword(oldPassword);
		user = userMapper.findUserByuser(user);
		if(user != null) {
			newPassword = (Tools.MD5(newPassword));
			user.setPassword(newPassword);
			userMapper.updateUser(user);
			logger.info("管理员密码修改,新密码:"+user.getPassword()+",旧密码:"+oldPassword+",修改地址:"+Tools.getIpAddr(request));
			return "{\"status\":\"200\"}";
		} else {
			return "{\"status\":\"400\"}";
		}
	}

}
