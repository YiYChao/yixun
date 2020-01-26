package top.yixun.service;

import top.yixun.pojo.Users;

/**
 * @Description: 用户相关操作接口定义
 * @author: YiYChao
 * @date: 2020年1月26日 下午4:04:46
 */
public interface UserService {

	/**
	 * @Description: 查询用户名是否存在
	 * @param: username 用户名
	 * @return: boolean 布尔值，true存在，法false不存在
	 * @date 2020年1月26日 下午4:05:18
	 */
	boolean queryNameExist(String username);

	/**
	 * @Description: 查询用户的登录信息
	 * @param： user 用户实体
	 * @return： Users 用户实体
	 * @date 2020年1月26日 下午4:16:57
	 * @throws Exception MD5加密异常
	 */
	Users queryUserForLogin(Users user) throws Exception;

	/**
	 * @Description: 用户注册，保存用户信息
	 * @param user 包含用户名称和密码的用户实体
	 * @return 新增的用户信息
	 * @date 2020年1月26日 下午4:31:12
	 * @throws
	 * @throws Exception MD5加密异常
	 */
	Users saveUser(Users user) throws Exception;

	
}
