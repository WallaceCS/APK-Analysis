package cn.lry.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {
	/**
	 * 解压 zip 文件，注意不能解压 rar 文件，只能解压 zip 文件 解压 rar 文件 会出现 java.io.IOException:
	 * Negative seek offset 异常
	 * 
	 * @param zipfile
	 *            zip 文件，注意要是正宗的 zip 文件，不能是把 rar 的直接改为 zip 这样会出现
	 *            java.io.IOException: Negative seek offset 异常
	 * @param destDir
	 *            解压到目标目录，不存在自动创建
	 * @throws IOException
	 */
	public static void unZip(String zipfile, String destDir) {
		destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		File tempDir = new File(destDir);
		if (!tempDir.exists() && !tempDir.isDirectory()) {
			tempDir.mkdir();
		}
		byte b[] = new byte[1024];
		int length;
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(new File(zipfile));
			Enumeration<?> enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			OutputStream outputStream = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				File loadFile = new File(destDir + zipEntry.getName());
				if (!zipEntry.isDirectory()) {
					if ("AndroidManifest.xml".equals(zipEntry.getName())) {
						outputStream = new FileOutputStream(
								loadFile);
						InputStream inputStream = zipFile
								.getInputStream(zipEntry);
						while ((length = inputStream.read(b)) > 0) {
							outputStream.write(b, 0, length);
						}
					}
				}
			}
			outputStream.close();
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
