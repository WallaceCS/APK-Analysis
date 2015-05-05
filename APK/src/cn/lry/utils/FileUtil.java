package cn.lry.utils;

import java.io.File;

public class FileUtil {
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param mPath
	 *            被删除目录的文件路径
	 * liruiyuan / 2014年11月18日 上午10:07:49
	 */
	public static void deleteDirAndFile(String mPath) {
		File file = new File(mPath);
		if(file.exists()){
			File[] files = file.listFiles();
			for (File mfile : files) {
				// 删除子文件
				mfile.delete();
			}
			file.delete();
		}
	}
}
