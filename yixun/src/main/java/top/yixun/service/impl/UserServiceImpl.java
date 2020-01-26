package top.yixun.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import top.yixun.mapper.UsersMapper;
import top.yixun.pojo.Users;
import top.yixun.service.UserService;
import top.yixun.utils.MD5Utils;
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

	@Override
	public Users saveUser(Users user) throws Exception {
		user.setPassword(MD5Utils.getMD5Str(user.getPassword()));	// 对用户密码进行加密
		user.setQrcode("");	// 设置用户的二维码信息
		user.setId(sid.nextShort());	// 生成用户的主键
		user.setFaceImage("");
		user.setFaceImageBig("");
		user.setNickname(user.getUsername());
		usersMapper.insert(user);
		return user;
	}

}
