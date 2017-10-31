package cn.melina.license;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import license.identify.IUniqueIdentify;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class VerifyLicense implements IVerifyLicense{
	private static Logger logger = Logger.getLogger(VerifyLicense.class);
	
	private IUniqueIdentify uniqueIdentify;
	private String publicalias;
	private String storePwd;
	private String subject;
	private String licPath;
	private String pubPath;
	

	public String getPublicalias() {
		return publicalias;
	}

	public void setPublicalias(String publicalias) {
		this.publicalias = publicalias;
	}

	public String getPubPath() {
		return pubPath;
	}

	public void setPubPath(String pubPath) {
		this.pubPath = pubPath;
	}

	public String getStorePwd() {
		return storePwd;
	}

	public void setStorePwd(String storePwd) {
		this.storePwd = storePwd;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLicPath() {
		return licPath;
	}

	public void setLicPath(String licPath) {
		this.licPath = licPath;
	}

	
	public IUniqueIdentify getUniqueIdentify() {
		return uniqueIdentify;
	}

	public void setUniqueIdentify(IUniqueIdentify uniqueIdentify) {
		this.uniqueIdentify = uniqueIdentify;
	}
	
	public String verify(String json){
		LicenseManager licenseManager = LicenseManagerHolder
				.getLicenseManager(initLicenseParams());
		Map<String,Object> returnMap = new  HashMap<String,Object>();
		// 安装证书
		try {
			JSONObject requestData = new JSONObject(json);
			if(!"00".equals(requestData.getString("state"))){
				returnMap.put("success", "false");
				returnMap.put("reason", "returnCode:" + requestData.getString("state"));
				return new JSONObject(returnMap).toString();
			}else{
				String content = requestData.getString("license");
				BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(licPath));
				bs.write(Base64.decodeBase64(content));
				bs.close();
				licenseManager.install(new File(licPath));
				logger.debug("license install success !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("license install fail !");
			returnMap.put("success", "false");
			returnMap.put("reason", "license install error");
			return new JSONObject(returnMap).toString();
		}

		// 验证证书
		try {
			LicenseContent s = licenseManager.verify();
			if (uniqueIdentify.UniqueCode().equals(s.getExtra().toString())) {
				logger.debug("license verify success !");
				returnMap.put("reason", "license verify success");
				returnMap.put("success", "true");
			} else {
				logger.debug("license verify fail !");
				returnMap.put("success", "false");
				returnMap.put("reason", "license verify fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("license verify fail !");
			returnMap.put("success", "false");
			returnMap.put("reason", "license verify error");
		}
		return new JSONObject(returnMap).toString();
	}
	
	// 返回验证证书需要的参数
	private LicenseParam initLicenseParams() {
		Preferences preference = Preferences
				.userNodeForPackage(VerifyLicense.class);
		CipherParam cipherParam = new DefaultCipherParam(storePwd);

		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
				VerifyLicense.class, pubPath, publicalias, storePwd, null);
		LicenseParam licenseParams = new DefaultLicenseParam(subject,
				preference, privateStoreParam, cipherParam);
		return licenseParams;
	}
}
