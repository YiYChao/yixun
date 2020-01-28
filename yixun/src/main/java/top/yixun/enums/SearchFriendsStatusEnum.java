package top.yixun.enums;
/**
 * @Description: 添加好友前置状态 枚举
 * @author: YiYChao
 * @date: 2020年1月28日 下午9:49:44
 */
public enum SearchFriendsStatusEnum {
	
	SUCCESS(0, "OK"),
	USER_NOT_EXIST(1, "无此用户"),	
	NOT_YOURSELF(2, "不能添加自己"),			
	ALREADY_FRIENDS(3, "用户已经是好友");
	
	public final Integer status;
	public final String msg;
	
	SearchFriendsStatusEnum(Integer status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public Integer getStatus() {
		return status;
	}  
	
	public static String getMsgByKey(Integer status) {
		for (SearchFriendsStatusEnum type : SearchFriendsStatusEnum.values()) {
			if (type.getStatus() == status) {
				return type.msg;
			}
		}
		return null;
	}
	
}
