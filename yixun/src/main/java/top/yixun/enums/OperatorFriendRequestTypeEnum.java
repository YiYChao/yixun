package top.yixun.enums;

/**
 * @Description: 忽略或者通过 好友请求的枚举
 * @author: YiYChao
 * @date: 2020年1月28日 下午9:50:08
 */
public enum OperatorFriendRequestTypeEnum {
	
	IGNORE(0, "忽略"),
	PASS(1, "通过");
	
	public final Integer type;
	public final String msg;
	
	OperatorFriendRequestTypeEnum(Integer type, String msg){
		this.type = type;
		this.msg = msg;
	}
	
	public Integer getType() {
		return type;
	}  
	
	public static String getMsgByType(Integer type) {
		for (OperatorFriendRequestTypeEnum operType : OperatorFriendRequestTypeEnum.values()) {
			if (operType.getType() == type) {
				return operType.msg;
			}
		}
		return null;
	}
	
}
