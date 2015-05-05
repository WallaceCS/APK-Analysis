package cn.lry.test;

import java.io.File;

import cn.lry.utils.CertificateUtil;
import cn.lry.utils.CheckApkAds;

public class Test {

	public static void main(String[] args) {
		//检查apk广告及使用权限
		CheckApkAds.check("G:"+File.separator+"file"+File.separator+"Adobe.apk");
		System.out.println("完整apk数字证书：");
		CertificateUtil.getKeyHashes("G:"+File.separator+"file"+File.separator+"Adobe.apk");
	}
}
