package cn.edustar.jitar.util;


public class BaseBlogPage {

	// 判断是否为数字
	public static boolean isIntegerStrong(Object val) {
		int i = 0;
		// # 转换为数字, 如果发生异常则不是数字.
		try {
			i = (Integer) val;
		} catch (Exception e) {
			return false;
		}
		// # 数字再转回字符串, 和字符串完全比较相同才是数字.
		if (!String.valueOf(i).trim().equals(val)) {
			return false;
		}
		return true;
	}

	// # 强判定一个指定字符串是否是一个合法的名字, 可用于判断 user.loginName, group.groupName.
	// # 合法名字必须由字母、数字、下划线构成, 第一个字符必须是字母，长度不得小于1.
	public static boolean isValidName(String name) {
		if (name == null || name.equals("")) {
			return false;
		}
		// 不可在前后带空白字符.
		if (!name.trim().equals(name)) {
			return false;
		}
		// 强制要求不允许出现单字符.
		if (name.length() == 1) {
			return false;
		}
		int i = 0;
		for (int j = 0; j < name.length(); j++) {
			char c = name.charAt(j);
			i++;
			if (('A' <= c && 'Z' >= c) || ('a' <= c && 'z' >= c)) {
				continue;
			}
			// 首字符必须是字母.
			if (i == 1) {
				return false;
			}
			if ('0' <= c && '9' >= c || '_'==c) {
				continue;
			}
			return false;
		}
		return true;
	}
}
