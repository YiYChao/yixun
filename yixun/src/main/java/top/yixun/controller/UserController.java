package top.yixun.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import top.yixun.enums.SearchFriendsStatusEnum;
import top.yixun.pojo.Users;
import top.yixun.pojo.bo.UserBO;
import top.yixun.pojo.vo.UserVo;
import top.yixun.service.UserService;
import top.yixun.utils.FastDFSClient;
import top.yixun.utils.FileUtils;
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
	@Autowired
	private FastDFSClient FastDFSClient;
	
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
	
	@PostMapping(value="/uploadFaceBase64")
	public YiXunJSONResult uploadFaceBase64(@RequestBody UserBO userBo) throws Exception {
		// 获取前端传过来的base64的头像字符串
		String base64Data = userBo.getFaceData();
		String tmpPath = "E:\\" + userBo.getUserId() + "userface64.png";
		FileUtils.base64ToFile(tmpPath, base64Data);
		
		MultipartFile multipartFile = FileUtils.fileToMultipart(tmpPath);
		String imgPath = FastDFSClient.uploadBase64(multipartFile);
		
		Users user = new Users();
		user.setId(userBo.getUserId());
		user.setFaceImageBig(imgPath);
		String smallPath = imgPath.substring(0, imgPath.lastIndexOf(".")) + "_80x80.png";	// 拼接小图的地址
		user.setFaceImage(smallPath);
		
		user = userService.updateUserInfo(user);
		
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(user, userVo);		// 实体拷贝，去除无关信息
		
		return YiXunJSONResult.ok(userVo);
	}
	
	@PostMapping(value="/setNickname")
	public YiXunJSONResult setNickname(@RequestBody UserBO userBo) throws Exception {
		System.err.println(userBo);
		Users user = new Users();
		user.setId(userBo.getUserId());
		user.setNickname(userBo.getNickname()); 	// 设置用户昵称
		
		user = userService.updateUserInfo(user);	// 更新用户信息
		
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(user, userVo);		// 实体拷贝，去除无关信息
		
		return YiXunJSONResult.ok(user);
	}
	
	@PostMapping(value="/searchFriend")
	public YiXunJSONResult searchFriend(@RequestBody UserBO userBo) throws Exception {
		// 判断用户名和密码是否为空
		if(StringUtils.isBlank(userBo.getUserId()) || StringUtils.isBlank(userBo.getFriendname())){
			return YiXunJSONResult.errorMsg("搜索空异常！");
		}
		int rst = userService.queryFriendsByName(userBo.getUserId(), userBo.getFriendname());
		if(rst == SearchFriendsStatusEnum.SUCCESS.status){
			Users tmp = userService.queryUserByUsername(userBo.getFriendname());		// 根据用户名称进行查询
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(tmp, userVo);		// 实体拷贝，去除无关信息
			return YiXunJSONResult.ok(userVo);
		}else{
			return YiXunJSONResult.errorMsg(SearchFriendsStatusEnum.getMsgByKey(rst));	// 返回泛型的消息提示
		}
	}
	
	@PostMapping(value="/friendRequest")
	public YiXunJSONResult friendRequest(@RequestBody UserBO userBo) throws Exception {
		// 判断用户名和密码是否为空
		if(StringUtils.isBlank(userBo.getUserId()) || StringUtils.isBlank(userBo.getFriendname())){
			return YiXunJSONResult.errorMsg("搜索空异常！");
		}
		int rst = userService.queryFriendsByName(userBo.getUserId(), userBo.getFriendname());
		if(rst == SearchFriendsStatusEnum.SUCCESS.status){
			userService.addFriendRequest(userBo.getUserId(), userBo.getFriendId());		// 添加好友请求
			return YiXunJSONResult.ok();
		}else{
			return YiXunJSONResult.errorMsg(SearchFriendsStatusEnum.getMsgByKey(rst));	// 返回泛型的消息提示
		}
	}
	
	@PostMapping(value="/queryFriendRequests")
	public YiXunJSONResult queryFriendRequests(@RequestBody UserBO userBo) throws Exception {
		List<UserVo> rst = userService.queryFriendRequests(userBo.getUserId());
		return YiXunJSONResult.ok(rst);
	}
	
	@PostMapping(value="/dellFriendRequest")
	public YiXunJSONResult dellFriendRequest(@RequestBody UserBO userBo) throws Exception {
		int rst = userService.dellFriendRequest(userBo.getUserId(),userBo.getFriendId(), userBo.getOperType());
		if(rst == 1){
			return YiXunJSONResult.ok();
		}else{
			return YiXunJSONResult.errorMsg("操作失败！");
		}
	}
}
