package com.lrcall.common.models;

import java.util.HashMap;

import com.lrcall.common.utils.StringTools;

/**
 * 错误代码定义
 * 
 * @author libit
 */
public class ErrorInfo
{
	public static final int SUCCESS = 0;
	public static final int WX_SUCCESS = 1;
	public static final int PARAM_ERROR = -1000;
	public static final int EXIST_ERROR = -1001;
	public static final int NOT_EXIST_ERROR = -1002;
	public static final int HIBERNATE_ERROR = -2000;
	public static final int NETWORK_ERROR = -200;
	public static final int FORBIDDEN_ERROR = -300;
	public static final int UNKNOWN_ERROR = -10000;
	public static final int PASSWORD_ERROR = -20000;
	public static final int UNSUPPORT_ENCODE = -20001;
	public static final int JDOM_ERROR = -20002;
	public static final int IO_ERROR = -20003;
	public static final int AES_EXCEPTION = -20005;
	// 微信错误
	public static final int GET_UUID_ERROR = -50001;
	public static final int GEN_QR_ERROR = -50002;
	public static final int QR_NOT_SCAN_ERROR = -50003;
	public static final int QR_NOT_CONFIRM_ERROR = -50004;
	public static final int WEB_WX_LOGIN_ERROR = -50005;
	public static final int WEB_WX_INIT_ERROR = -50006;
	public static final int WX_SESSION_NULL_ERROR = -50007;
	public static final int WX_SESSION_NOT_NULL_ERROR = -50008;
	public static final int UNKNOWN_TIP_ERROR = -50009;
	// 充值卡
	public static final int CARD_ACTIVED_ERROR = -60001;
	public static final int CARD_INVALIDE_ERROR = -60002;

	public static String getErrorInfo(int errcode)
	{
		String errInfo = ERROR_INFO.get(errcode);
		if (StringTools.isNull(errInfo))
		{
			errInfo = String.format("未知错误代码%d", errcode);
		}
		return errInfo;
	}

	public final static HashMap<Integer, String> ERROR_INFO = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 111L;
		{
			put(SUCCESS, "操作成功");
			put(PARAM_ERROR, "参数错误");
			put(EXIST_ERROR, "对象已存在");
			put(NOT_EXIST_ERROR, "对象不存在");
			put(HIBERNATE_ERROR, "数据库错误");
			/* 微信提供的错误信息 */
			put(40001, "获取access_token时AppSecret错误，或者access_token无效");
			put(40002, "不合法的凭证类型");
			put(-1, "系统繁忙，此时请开发者稍候再试");
			put(0, "请求成功");
			put(40001, "获取access_token时AppSecret错误，或者access_token无效");
			put(40002, "不合法的凭证类型");
			put(40003, "不合法的OpenID");
			put(40004, "不合法的媒体文件类型");
			put(40005, "不合法的文件类型");
			put(40006, "不合法的文件大小");
			put(40007, "不合法的媒体文件id");
			put(40008, "不合法的消息类型");
			put(40009, "不合法的图片文件大小");
			put(40010, "不合法的语音文件大小");
			put(40011, "不合法的视频文件大小");
			put(40012, "不合法的缩略图文件大小");
			put(40013, "不合法的APPID");
			put(40014, "不合法的access_token");
			put(40015, "不合法的菜单类型");
			put(40016, "不合法的按钮个数");
			put(40017, "不合法的按钮个数");
			put(40018, "不合法的按钮名字长度");
			put(40019, "不合法的按钮KEY长度");
			put(40020, "不合法的按钮URL长度");
			put(40021, "不合法的菜单版本号");
			put(40022, "不合法的子菜单级数");
			put(40023, "不合法的子菜单按钮个数");
			put(40024, "不合法的子菜单按钮类型");
			put(40025, "不合法的子菜单按钮名字长度");
			put(40026, "不合法的子菜单按钮KEY长度");
			put(40027, "不合法的子菜单按钮URL长度");
			put(40028, "不合法的自定义菜单使用用户");
			put(40029, "不合法的oauth_code");
			put(40030, "不合法的refresh_token");
			put(40031, "不合法的openid列表");
			put(40032, "不合法的openid列表长度");
			put(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符");
			put(40035, "不合法的参数");
			put(40038, "不合法的请求格式");
			put(40039, "不合法的URL长度");
			put(40050, "不合法的分组id");
			put(40051, "分组名字不合法");
			put(41001, "缺少access_token参数");
			put(41002, "缺少appid参数");
			put(41003, "缺少refresh_token参数");
			put(41004, "缺少secret参数");
			put(41005, "缺少多媒体文件数据");
			put(41006, "缺少media_id参数");
			put(41007, "缺少子菜单数据");
			put(41008, "缺少oauth code");
			put(41009, "缺少openid");
			put(42001, "access_token超时");
			put(42002, "refresh_token超时");
			put(42003, "oauth_code超时");
			put(43001, "需要GET请求");
			put(43002, "需要POST请求");
			put(43003, "需要HTTPS请求");
			put(43004, "需要接收者关注");
			put(43005, "需要好友关系");
			put(44001, "多媒体文件为空");
			put(44002, "POST的数据包为空");
			put(44003, "图文消息内容为空");
			put(44004, "文本消息内容为空");
			put(45001, "多媒体文件大小超过限制");
			put(45002, "消息内容超过限制");
			put(45003, "标题字段超过限制");
			put(45004, "描述字段超过限制");
			put(45005, "链接字段超过限制");
			put(45006, "图片链接字段超过限制");
			put(45007, "语音播放时间超过限制");
			put(45008, "图文消息超过限制");
			put(45009, "接口调用超过限制");
			put(45010, "创建菜单个数超过限制");
			put(45015, "回复时间超过限制");
			put(45016, "系统分组，不允许修改");
			put(45017, "分组名字过长");
			put(45018, "分组数量超过上限");
			put(46001, "不存在媒体数据");
			put(46002, "不存在的菜单版本");
			put(46003, "不存在的菜单数据");
			put(46004, "不存在的用户");
			put(47001, "解析JSON/XML内容错误");
			put(48001, "api功能未授权");
			put(50001, "用户未授权该api");
			put(9001001, "POST数据参数不合法");
			put(9001002, "远端服务不可用");
			put(9001003, "Ticket不合法");
			put(9001004, "获取摇周边用户信息失败");
			put(9001005, "获取商户信息失败");
			put(9001006, "获取OpenID失败");
			put(9001007, "上传文件缺失");
			put(9001008, "上传素材的文件类型不合法");
			put(9001009, "上传素材的文件尺寸不合法");
			put(9001010, "上传失败");
			put(9001020, "帐号不合法");
			put(9001022, "设备申请数不合法，必须为大于0的数字");
			put(9001023, "已存在审核中的设备ID申请");
			put(9001024, "一次查询设备ID数量不能超过50");
			put(9001025, "设备ID不合法");
			put(9001026, "页面ID不合法");
			put(9001027, "页面参数不合法");
			put(9001028, "一次删除页面ID数量不能超过10");
			put(9001029, "页面已应用在设备中，请先解除应用关系再删除");
			put(9001030, "一次查询页面ID数量不能超过50");
			put(9001031, "时间区间不合法");
			put(9001032, "保存设备与页面的绑定关系参数错误");
			put(9001033, "门店ID不合法");
			put(9001034, "设备备注信息过长");
			put(9001035, "设备申请参数不合法");
			put(9001036, "查询起始值begin不合法");
			put(9001037, "单个设备绑定页面不能超过30个");
			put(9001038, "设备总数超过了限额");
			put(9001039, "不合法的联系人名字");
			put(9001040, "不合法的联系人电话");
			put(9001041, "不合法的联系人邮箱");
			put(9001042, "不合法的行业id");
			put(9001043, "不合法的资质证明文件url，文件需通过“素材管理”接口上传");
			put(9001044, "缺少资质证明文件");
			put(9001045, "申请理由不能超过500字");
			put(9001046, "公众账号未认证");
			put(9001047, "不合法的设备申请批次id");
			put(9001048, "审核状态为审核中或审核已通过，不能再提交申请请求");
			put(9001049, "获取分组元数据失败");
			put(9001050, "账号下分组数达到上限，最多为100个");
			put(9001051, "分组包含的设备数达到上限，最多为10000个");
			put(9001052, "每次添加到分组的设备数达到上限，每次最多操作1000个设备");
			put(9001053, "每次从分组删除的设备数达到上限，每次最多操作1000个设备");
			put(9001054, "待删除的分组仍存在设备");
			put(9001055, "分组名称过长，上限为100个字符");
			put(9001056, "分组待添加或删除的设备列表中包含有不属于该分组的设备id");
			put(9001057, "分组相关信息操作失败");
			put(9001058, "分组id不存在");
			put(9001059, "模板页面logo_url为空");
			put(9001060, "创建红包活动失败");
			put(9001061, "获得红包活动ID失败");
			put(9001062, "创建模板页面失败");
			put(9001063, "红包提供商户公众号ID和红包发放商户公众号ID不一致");
			put(9001064, "红包权限审核失败");
			put(9001065, "红包权限正在审核");
			put(9001066, "红包权限被取消");
			put(9001067, "没有红包权限");
			put(9001068, "红包活动时间不在红包权限有效时间内");
			put(9001069, "设置红包活动开关失败");
			put(9001070, "获得红包活动信息失败");
			put(9001071, "查询红包ticket失败");
			put(9001072, "红包ticket数量超过限制");
			put(9001073, "sponsor_appid与预下单时的wxappid不一致");
			put(9001074, "获得红包发送ID失败");
			put(9001075, "录入活动的红包总数超过创建活动时预设的total");
			put(9001076, "添加红包发送ID失败");
			put(9001077, "解码红包发送ID失败");
			put(9001078, "获取公众号uin失败");
			put(9001079, "接口调用appid与调用创建活动接口的appid不一致");
			put(9001090, "录入的所有ticket都是无效ticket，可能原因为ticket重复使用，过期或金额不在1-1000元之间");
			put(9001091, "活动已过期");
		}
	};
}
