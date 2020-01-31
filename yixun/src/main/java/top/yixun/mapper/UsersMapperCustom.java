package top.yixun.mapper;

import java.util.List;

import top.yixun.pojo.vo.UserVo;

/**
 * @Description: 自定义用户持久层操作接口定义
 * @author: YiYChao
 * @date: 2020年1月29日 下午9:43:37
 */
public interface UsersMapperCustom{
	/**
	 * @Description: 查询当前用户接收到的好友请求
	 * @param userId 当前用户的主键id
	 * @return 虚拟用户对象列表List<UserVo>
	 * @date 2020年1月29日 下午9:48:33
	 */
	List<UserVo> queryFriendRequests(String userId);
	
	/**
	 * @Description: 查询当前用户的好友列表
	 * @param userId 当前用户的id
	 * @return 虚拟用户对象列表List<UserVo>
	 * @date 2020年1月30日 下午4:48:33
	 */
	List<UserVo> queryFriendsList(String userId);

	/**
	 * @Description: 批量签收消息
	 * @param msgIdList
	 * @date 2020年1月31日 下午8:20:02
	 * @throws
	 */
	void updateMsgSigned(List<String> msgIdList);
}