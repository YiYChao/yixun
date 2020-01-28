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

	/**
	 * @Description: 更新用户信息
	 * @param user 用户实体
	 * @return	用户实体
	 * @date 2020年1月28日 上午11:30:06
	 */
	Users updateUserInfo(Users user);

	/**
	 * @Description: 根据好友名称（username）查询当前用户的好友
	 * @param userId 当前用户的 主键id
	 * @param friendName 好友的名称（username）
	 * @return 当前的状态
	 * @date 2020年1月28日 下午10:53:00
	 */
	int queryFriendsByName(String userId, String friendName);
	
	/**
	 * @Description: 通过用户名查找用户
	 * @param username 用户名
	 * @return 用户实体对象
	 * @date 2020年1月28日 下午11:03:20
	 */
	Users queryUserByUsername(String username);

	/**
	 * @Description: 添加好友请求
	 * @param userId 当前用户id
	 * @param friendId 好友id
	 * @return int 添加的记录数
	 * @date 2020年1月28日 下午11:37:22
	 */
	int addFriendRequest(String userId, String friendId);
	
}
