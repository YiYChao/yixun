package top.yixun.pojo.bo;
/**
 * @Description: 接收前端用户头像信息的BO
 * @author: YiYChao
 * @date: 2020年1月26日 下午4:41:16
 */
public class UserBO {
    
    private String userId;		// 用户Id
    private String faceData;	// base64的图像
    private String nickname;	// 用户昵称
    private String friendname;	// 好友名称
    private String friendId;	// 好友Id
    private Integer operType;	// 处理好友请求的操作，0通过，1忽略
	
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFaceData() {
		return faceData;
	}
	public void setFaceData(String faceData) {
		this.faceData = faceData;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFriendname() {
		return friendname;
	}
	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public Integer getOperType() {
		return operType;
	}
	public void setOperType(Integer operType) {
		this.operType = operType;
	}
	@Override
	public String toString() {
		return "UserBO [userId=" + userId + ", faceData=" + faceData + ", nickname=" + nickname + ", friendname="
				+ friendname + ", friendId=" + friendId + ", operType=" + operType + "]";
	}
}