package lvxixiao.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import net.minidev.json.JSONObject;

public class Jwt {
	/**
     * 秘钥：MessageDigest.getInstance("MD5").digest("lvxixiao")
     */
    private static final byte[] SECRET="58bf638ce71743948f2e801f185fa349".getBytes();
    /**
     * 初始化head部分的数据为
     * {
     * 		"alg":"HS256",
     * 		"type":"JWT"
     * }
     */
    private static final JWSHeader header=new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);
	
    private static final String VALID = "200";
    
    private static final String EXPIRED = "401";
    
    private static final String INVALID = "400";
    
    /**
	 * 生成token，该方法只在用户登录成功后调用
	 * 
	 * @param Map集合，可以存储用户id，token生成时间，token过期时间等自定义字段
	 * @return token字符串,若失败则返回null
	 */
	public static String createToken(Map<String, Object> payload) {
		String tokenString = null;
		//创建一个JWS object
		JWSObject jwsObject = new JWSObject(header,new Payload(new JSONObject(payload)));
		try {
			//将jwsObject进行HMAC签名
			jwsObject.sign(new MACSigner(SECRET));
			tokenString = jwsObject.serialize();
		} catch (JOSEException e) {
			System.err.println("签名失败:"+e.getMessage());
			e.printStackTrace();
		}
		return tokenString;		
	}
	/**
     * 校验token是否合法，返回Map集合,集合中主要包含    state状态码   data鉴权成功后从token中提取的数据
     * 该方法在过滤器中调用，每次请求API时都校验
     * @param token
     * @return  Map<String, Object>
     */
	public static Map<String, Object> validToken(String token) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JWSObject jwsObject = JWSObject.parse(token);
			Payload payload = jwsObject.getPayload();
			JWSVerifier verifiler = new MACVerifier(SECRET);
		
			if(jwsObject.verify(verifiler)) {
				JSONObject jsonOBJ = payload.toJSONObject();
				//token校验成功（此时没有校验是否过期）
				resultMap.put("status", VALID);
				//若payload包含ext字段，则校验是否过期
				if(jsonOBJ.containsKey("ext")) {
					long extTime = Long.valueOf(jsonOBJ.get("ext").toString());
					long curTime = new Date().getTime();
					//过期了
					if(curTime > extTime) {
						resultMap.clear();
						resultMap.put("status", EXPIRED);
					}
				}
				//校验用户名
				if(jsonOBJ.containsKey("username")) {
					String username = jsonOBJ.get("username").toString();
					if(!username.equals("root@lvxixiao")) {
						resultMap.clear();
						resultMap.put("status",INVALID);
					}
				} else {
					resultMap.clear();
					resultMap.put("status",INVALID);
				}
			} else {
				//校验失败
				resultMap.put("status",INVALID);
			}
		} catch (Exception e) {
			// token格式不合法导致的异常
			resultMap.clear();
			resultMap.put("state", INVALID);
		}
		return resultMap;
		
	}
}
