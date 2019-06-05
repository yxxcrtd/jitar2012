package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.Category;

/**
 * 分类计算 path, depth 等信息时候的辅助公共函数.
 *
 *
 */
public class CategoryHelper {
	private CategoryHelper() {}
	
	/** 分类 path 中数字使用 36 进制表示. */
	public static final int CATEGORY_PATH_RADIX = 36;
	
	/**
	 * 将指定分类标识数字转换为路径字符串表示, 使用 36 进制进行转换.
	 * @param cid
	 * @return
	 */
	public static final String toPathString(int cid) {
		return Integer.toString(cid, CATEGORY_PATH_RADIX).toUpperCase();
	}
	
	/**
	 * 计算指定分类的分类路径, 该分类路径 = category.parentPath + category.id(36进制) + '/'
	 * @param category - 分类, 如果 == null 则返回根分类路径 '/'
	 * @return
	 */
	public static final String calcCategoryPath(Category category) {
		if (category == null) return "/";
		return category.getCategoryPath();
	}
}
