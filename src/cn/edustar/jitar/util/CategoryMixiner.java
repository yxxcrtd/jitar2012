package cn.edustar.jitar.util;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.User;

public class CategoryMixiner {
	// 得到用户文章分类对应的分类 itemType.
	public static String toUserArticleCategoryItemType(User user) {
		return CommonUtil.toUserArticleCategoryItemType(user.getUserId());
	}

	// 得到用户资源分类对应的分类 itemType.
	public static String toUserResourceCategoryItemType(User user) {
		return CommonUtil.toUserResourceCategoryItemType(user.getUserId());
	}

	// 得到用户视频分类对应的分类 itemType.
	public static String toUserVideoCategoryItemType(User user) {
		return CommonUtil.toUserVideoCategoryItemType(user.getUserId());
	}

	// 得到协作组文章分类对应的分类 itemType.
	public static String toGroupArticleCategoryItemType(Group group) {
		return CommonUtil.toGroupArticleCategoryItemType(group.getGroupId());
	}

	// 得到协作组资源分类对应的分类 itemType.
	public static String toGroupResourceCategoryItemType(Group group) {
		return CommonUtil.toGroupResourceCategoryItemType(group.getGroupId());
	}

	// 得到协作组图片分类对应的分类 itemType.
	public static String toGroupPhotoCategoryItemType(Group group) {
		return CommonUtil.toGroupPhotoCategoryItemType(group.getGroupId());
	}

	// 得到协作组视频分类对应的分类 itemType.
	public static String toGroupVideoCategoryItemType(Group group) {
		return CommonUtil.toGroupVideoCategoryItemType(group.getGroupId());
	}
}
