package top.yixun.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import top.yixun.enums.OperatorFriendRequestTypeEnum;
import top.yixun.enums.SearchFriendsStatusEnum;
import top.yixun.mapper.FriendsRequestMapper;
import top.yixun.mapper.MyFriendsMapper;
import top.yixun.mapper.UsersMapper;
import top.yixun.mapper.UsersMapperCustom;
import top.yixun.pojo.FriendsRequest;
import top.yixun.pojo.MyFriends;
import top.yixun.pojo.Users;
import top.yixun.pojo.vo.UserVo;
import top.yixun.service.UserService;
import top.yixun.utils.FastDFSClient;
import top.yixun.utils.FileUtils;
import top.yixun.utils.MD5Utils;
import top.yixun.utils.QRCodeUtils;
/**
 * @Description: 用户相关操作接口实现
 * @author: YiYChao
 * @date: 2020年1月26日 下午4:08:37
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private Sid sid;
	@Autowired
	private QRCodeUtils qrCodeUtils;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private MyFriendsMapper myFriendsMapper;
	@Autowired
	private FriendsRequestMapper friendsRequestMapper;
	@Autowired
	private UsersMapperCustom usersMapperCustom;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryNameExist(String username) {
		Users user = new Users();
		user.setUsername(username);
		
		Users rst = usersMapper.selectOne(user);
		
		return rst == null ? false : true;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(Users user) throws Exception {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", user.getUsername());
		criteria.andEqualTo("password", MD5Utils.getMD5Str(user.getPassword()));
		Users rst = usersMapper.selectOneByExample(userExample);
		return rst;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users saveUser(Users user) throws Exception {
		user.setPassword(MD5Utils.getMD5Str(user.getPassword()));	// 对用户密码进行加密
		
		String codePath = "E:\\" + user.getUsername().hashCode() + "qrcode.png";		// 临时文件
		qrCodeUtils.createQRCode(codePath, "yixun_code:" + user.getUsername());	// 生成二维码
		MultipartFile qrcodeImg = FileUtils.fileToMultipart(codePath);			// 转换图片
		String qrcodeUrl = fastDFSClient.uploadQRCode(qrcodeImg);
		
		user.setQrcode(qrcodeUrl);	// 设置用户的二维码信息
		user.setId(sid.nextShort());	// 生成用户的主键
		user.setFaceImage("");
		user.setFaceImageBig("");
		user.setNickname(user.getUsername());
		usersMapper.insert(user);
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users updateUserInfo(Users user) {
		usersMapper.updateByPrimaryKeySelective(user);		// 选择性更新用户信息
		return usersMapper.selectByPrimaryKey(user.getId());	// 查询用户信息并返回
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public int queryFriendsByName(String userId, String friendName) {
		Users friend = queryUserByUsername(friendName);
		if(friend == null){		// 用户不存在
			return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
		}else if(friend.getId().equals(userId)){	// 搜索用户为自己
			return SearchFriendsStatusEnum.NOT_YOURSELF.status;
		}
		// 验证是否已经是好友
		Example example = new Example(MyFriends.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("myUserId", userId);			// 用户id
		criteria.andEqualTo("myFriendUserId", friend.getId());	// 好友id
		MyFriends myFriends = myFriendsMapper.selectOneByExample(example);
		
		if(myFriends != null){	// 已经是好友
			return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
		}else{	// 还不是好友
			return SearchFriendsStatusEnum.SUCCESS.status;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserByUsername(String username) {
		// 设置查询条件
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username", username);
		
		// 执行查询并返回
		return usersMapper.selectOneByExample(example);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public int addFriendRequest(String userId, String friendId) {
		Example example = new Example(FriendsRequest.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sendUserId", userId);
		criteria.andEqualTo("acceptUserId", friendId);
		FriendsRequest rst = friendsRequestMapper.selectOneByExample(example);
		if(rst == null){
			FriendsRequest request = new FriendsRequest();
			request.setId(sid.nextShort());
			request.setSendUserId(userId);
			request.setAcceptUserId(friendId);
			request.setRequestDateTime(new Date());
			// 新增用户请求记录
			return friendsRequestMapper.insert(request);
		}else{
			return 0;
		}
	}

	@Override
	public List<UserVo> queryFriendRequests(String userId) {
		return usersMapperCustom.queryFriendRequests(userId);
	}

	@Override
	public int dellFriendRequest(String userId, String friendId, Integer operType) {
		// 参数异常
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(friendId) || operType == null){
			return 0;
		}else{
			// 删除申请记录
			int rst = deleteFriendsRequest(userId, friendId);
			// 通过好友申请
			if(OperatorFriendRequestTypeEnum.PASS.type == operType){
				// 双向添加好友
				insertFriend(userId, friendId);
				insertFriend(friendId, userId);
			}
			return rst;
		}
	}
	
	/**
	 * @Description: 删除好友的申请记录
	 * @param userId 当前用户（接收者）的主键id
	 * @param friendId 好友（发送者）的主键id
	 * @return 删除的记录数
	 * @date 2020年1月29日 下午10:52:04
	 */
	private int deleteFriendsRequest(String userId, String friendId){
		Example example = new Example(FriendsRequest.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("acceptUserId", userId);	// 当前用户（接收者）的id
		criteria.andEqualTo("sendUserId", friendId);	// 发送这的id
		int rst = friendsRequestMapper.deleteByExample(example);
		return rst;
	}
	
	/**
	 * @Description: 添加好友记录
	 * @param userId 用户id
	 * @param friendId 好友id
	 * @return 添加的记录数
	 * @date 2020年1月29日 下午11:08:05
	 * @throws
	 */
	private int insertFriend(String userId, String friendId){
		MyFriends friend = new MyFriends();
		friend.setId(sid.nextShort());
		friend.setMyUserId(userId);
		friend.setMyFriendUserId(friendId);
		return myFriendsMapper.insert(friend);
	}

	@Override
	public List<UserVo> queryFriendsList(String userId) {
		return usersMapperCustom.queryFriendsList(userId);
	}
}
