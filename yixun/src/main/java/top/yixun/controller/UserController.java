package top.yixun.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.yixun.pojo.Users;
import top.yixun.pojo.vo.UserVo;
import top.yixun.service.UserService;
import top.yixun.utils.YiXunJSONResult;
/**
 * 
 * @Description: 用户相关操作的前端控制器
 * @author: YiYChao
 * @date: 2020年1月26日 下午3:14:23
 */
@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value="/registOrLogin")
	public YiXunJSONResult registOrLogin(@RequestBody Users user) throws Exception {
		// 判断用户名和密码是否为空
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return YiXunJSONResult.errorMsg("用户名或密码不能为空");
		}
		// 判断用户是否 存在，存在则进行登录，否则进行注册
		boolean nameExist = userService.queryNameExist(user.getUsername());
		Users rst = null;
		if(nameExist){
			// 登录
			rst = userService.queryUserForLogin(user);
			if (rst == null) {
				return YiXunJSONResult.errorMsg("用户名或密码不正确！");
			}
		}else{
			// 注册
			rst = userService.saveUser(user);
		}
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(rst, userVo);		// 实体拷贝，去除无关信息
		return YiXunJSONResult.ok(userVo);
	}
}
