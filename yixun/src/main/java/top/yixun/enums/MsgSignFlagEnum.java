package top.yixun.enums;
/**
 * @Description: 消息签收状态 枚举
 * @author: YiYChao
 * @date: 2020年1月31日 下午7:41:34
 */
public enum MsgSignFlagEnum {
	
	unsign(0, "未签收"),
	signed(1, "已签收");	
	
	public final Integer type;
	public final String content;
	
	MsgSignFlagEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}  
}
