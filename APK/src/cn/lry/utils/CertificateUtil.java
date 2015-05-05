package cn.lry.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import cn.lry.bean.Base64;

public class CertificateUtil {
	private static String lastError = "";

	public static String getError() {
		return lastError;
	}

	public static String getKeyHashes(String archiveSourcePath) {
		PackageParser parser = new PackageParser();
		Certificate[] certificates = parser
				.collectCertificates(archiveSourcePath);
		if (certificates == null) {
			return null;
		}

		KeyTool keytool = new KeyTool();
		String keyhashes = "";
		for (Certificate cert : certificates) {
			try {
				String MD5 = keytool.getCertFingerPrint("MD5", cert);
				String SHA1 = keytool.getCertFingerPrint("SHA1", cert);
				String SHA256 = keytool.getCertFingerPrint("SHA-256", cert);
				String hash = getKeyHash(cert);

				keyhashes += "MD5: " + MD5 + "\n";
				keyhashes += "SHA1: " + SHA1 + "\n";
				keyhashes += "SHA256: " + SHA256 + "\n";
				keyhashes += "hash: " + hash + "\n\n";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(keyhashes);

		return keyhashes;
	}

	// based on
	// https://developers.facebook.com/docs/getting-started/facebook-sdk-for-android/3.0/#sig
	private static String getKeyHash(Certificate cert) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(cert.getEncoded());
			return Base64.encodeToString(md.digest(), Base64.DEFAULT);
		} catch (CertificateEncodingException e) {
			lastError = e.getMessage();
			System.err.println(lastError);
		} catch (NoSuchAlgorithmException e) {
			lastError = e.getMessage();
			System.err.println(lastError);
		}
		return null;
	}
}
