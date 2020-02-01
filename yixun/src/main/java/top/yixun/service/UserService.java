package top.yixun.service;

import java.util.List;

import top.yixun.netty.ChatMsg;
import top.yixun.pojo.Users;
import top.yixun.pojo.vo.UserVo;

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

	/**
	 * @Description: 查询用户收到的好友请求
	 * @param userId 当前用户的id
	 * @return 请求添加的好友虚拟信息实体列表List<UserVo>
	 * @date 2020年1月29日 下午9:59:28
	 */
	List<UserVo> queryFriendRequests(String userId);

	/**
	 * @Description: 处理好友申请
	 * @param userId 当前用户的id
	 * @param friendId 好友的id
	 * @param operType 操作，0忽略，1同意
	 * @return 操作的记录数
	 * @date 2020年1月29日 下午10:42:17
	 */
	int dellFriendRequest(String userId, String friendId, Integer operType);

	/**
	 * @Description: 查询当前用户的后好友列表
	 * @param userId 当前用户的主键id
	 * @return 当前用户的好友虚拟对象列表
	 * @date 2020年1月30日 下午4:52:02
	 */
	List<UserVo> queryFriendsList(String userId);

	/**
	 * @Description: 保存聊天消息
	 * @param chatMsg 自定义的聊天信息实体
	 * @return 消息的主键id
	 * @date 2020年1月31日 下午8:04:50
	 */
	String saveMsg(ChatMsg chatMsg);

	/**
	 * @Description: 将消息设置为签收状态
	 * @param msgIdList 消息的主键列表
	 * @date 2020年1月31日 下午8:12:50
	 */
	void updateMsgSigned(List<String> msgIdList);

	/**
	 * @Description: 批量获取用户的未读聊天记录
	 * @param userId 用户id
	 * @return 聊天记录实体列表
	 * @date 2020年2月1日 上午10:37:58
	 */
	List<top.yixun.pojo.ChatMsg> queryUnReadMsgList(String userId);
	
}
