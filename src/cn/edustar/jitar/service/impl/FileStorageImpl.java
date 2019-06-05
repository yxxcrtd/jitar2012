package cn.edustar.jitar.service.impl;

import java.io.File;
import java.io.IOException;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FileStorage;

/**
 * FileStorage 的简单实现
 * 
 *
 */
public class FileStorageImpl implements FileStorage {

	/** 拥有者 */
	private final Object owner;

	private final File root_folder;

	/**
	 * 使用指定的 owner, root_folder 构造一个 FileStorageImpl 的新实例
	 * 
	 * @param owner
	 * @param root_folder
	 */
	public FileStorageImpl(User user, File root_folder) {
		this.owner = user;
		this.root_folder = root_folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FileStorage#getOwner()
	 */
	public Object getOwner() {
		return this.owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FileStorage#isFileExist(java.lang.String)
	 */
	public boolean isFileExist(String relativePath) throws IOException {
		String filePath = this.root_folder.getCanonicalPath() + File.pathSeparator + relativePath;
		File f = new File(filePath);
		return f.exists();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FileStorage#getRootFolder()
	 */
	public File getRootFolder() throws IOException {
		return this.root_folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FileStorage#getFile(java.lang.String)
	 */
	public File getFile(String relativePath) throws IOException {
		if (relativePath == null || relativePath.length() == 0) {
			return new File(root_folder.getCanonicalPath());
		}

		String filePath = this.root_folder.getCanonicalPath() + File.separator + relativePath;
		File f = new File(filePath);
		return f.getCanonicalFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FileStorage#getRelativePath(java.io.File)
	 */
	public String getRelativePath(File file) {
		if (file == null) {
			return null;
		}
		
		String abs_path = file.getAbsolutePath();
		String root_path = this.root_folder.getAbsolutePath();
		if (root_path.endsWith(File.separator) == false) {
			root_path += File.separator;
		}
		if (file.isDirectory() && abs_path.endsWith(File.separator) == false) {
			abs_path += File.separator;
		}

		// 如果所给文件的绝对路径不在 root_path 下面，则返回 null 表示非法 (例如: 试图访问别人的文件)
		if (abs_path.startsWith(root_path) == false) {
			return null;
		}

		String rel_path = abs_path.substring(root_path.length());
		if (rel_path == null || rel_path.length() == 0) {
			rel_path = "/";
		}
		rel_path = rel_path.replace('\\', '/');
		
		if (rel_path.startsWith("/") == false) {
			rel_path = "/" + rel_path;
		}

		if (file.isDirectory() && rel_path.endsWith("/") == false && rel_path.length() > 1) {
			rel_path += "/";
		}

		return rel_path;
	}
	
}
