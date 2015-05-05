package cn.lry.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * 检测apk广告
 * @desc 
 * @author liruiyuan / <liruiyuan@gz.iscas.ac.cn>
 * @Create Date 2014年11月18日 上午9:27:20
 * @version v1.0
 * @Modified By
 * @Modified Date
 * @note
 */
public class CheckApkAds {
	/**
	 * 检测apk是否含有广告
	 * @param tarApk apk绝对路径
	 * @return 含有广告返回true，没有返回false
	 * liruiyuan / 2014年11月18日 上午9:28:54
	 */
	public static boolean check(String tarApk){
		boolean flag = false;
		String apkName = tarApk.substring(tarApk.lastIndexOf(File.separator)+1, tarApk.lastIndexOf("."));
		String apkDir =  tarApk.substring(0, tarApk.lastIndexOf(File.separator)+1);
		ReadManifest.read(tarApk,apkDir + apkName);
		Set<String> activities = ReadManifest.getActivities();
		Set<String> permissions = ReadManifest.getPermissions();
		//FIXME 这里需要写成数据库查询操作做广告特征匹配检查
		for (String str : activities) {
			if (str.contains("com.google.ads")) {
				System.out.println("含有google广告");
				flag = true;
			}
			if (str.contains("net.youmi.android.AdBrowser")
					|| str.contains("net.youmi.android.AdService")
					|| str.contains("net.youmi.android.AdReceiver")) {
				System.out.println("含有youmi广告");
				flag = true;
			}
			if (str.contains("com.admob.android.ads")) {
				System.out.println("含有admob广告");
				flag = true;
			}
			if (str.contains("com.kugou.pushads.AdsReceiver")) {
				System.out.println("含有kugou广告");
				flag = true;
			}
			if (str.contains("cn.domob.android.ads")) {
				System.out.println("含有domob广告");
				flag = true;
			}
			if (str.contains("com.kyview.AdviewWebView")) {
				System.out.println("含有kyview广告");
				flag = true;
			}
			if (str.contains("com.kuaiyou.AdViewInstlActivity")) {
				System.out.println("含有kuaiyou广告");
				flag = true;
			}
		}
		System.out.println("使用到的权限：");
		Properties prop = new Properties();   
        InputStream in = Object.class.getResourceAsStream("/permission.properties");
		for(String str : permissions){
			try {
				prop.load(in);
				if(prop.getProperty(str)==null){
					System.out.println(str);
				}else{
					System.out.println(prop.getProperty(str));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//删除掉解压出来的临时文件
		FileUtil.deleteDirAndFile(apkDir + apkName);
		return flag;
	}
}
