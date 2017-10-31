package cn.melina.license;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class licenseVerifyTest {
	public static void main(String[] args){
		System.out.println(System.getProperty("user.dir"));
		BasicConfigurator.configure();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"conf/spring.xml");
		VerifyLicense vl = (VerifyLicense)context.getBean("verifyLicense");
		Map<String,Object> map = new  HashMap<String,Object>();
		map.put("state","00");
		map.put("license","W4VGrLuN3AaZJawYRmKPfJO9EPG0GPjRaVitMoOWQ41izvQgg7cYQmA3ct6pW5QROb4bTS4femNtJqFJbZdwF+sD7IsV48PVE4QR9qnNY81ArC/w/GPOJHXvFAks15P4+5iyRSALkQ4LOun/5V/PnwdE5U8A72ThKKn6IibYFxTDK+FKsWZ0ZWIkDCrwuyAM6F7Lvn0UDw9GDmG4wGiB+5Ql/RRet8KvZ1a2wZAzp2Q9JvkC/rDim+/Eq/PQWqo+s9idolNC3hMfVFmy7rtjv95obndb2XBjzdxB/G4JafF6HBNHMdPu8DBzxr/xIXPCiscIL7SFIw8WWooEYvU2uku5NbxoVPOVOCPv1JdLK+qDZlrAys7+Q7at+JiHnWZUkklipzRfGsKj2kgIPxV0qVVU54sE+PvuhDdITKzgC2dUDstyI7spx+yDGMg7WE9sMDSyrK8gncT2+RN/Z+wGUOeu+U3Z6V0iOcrEI9l1KYuX6xg03ab1DRLZSlF+ntagRFpSxSnos8PDxVTk+SM7LBFMwsV7QW56eonRorKGkCDLD4WQv5Mf+0qRA8yIjgAaweSI6Y92QQeAcfs5sUwIL4glPDrUqwzGGjXpctzdIowP0QWibq/nPk3vJsNGa/NaI11KhCZQBPiJi8rVxgNVFvM6BPhSCqk2bQ8XRLQ5BlPt0CV3e9ITJ/q+iWfDm/KIyCkP/HsM5QgaWqzNxCm/EKYqRyy0iCgfher5kpzBQFIAyXLYS6rw2KeQpWIJA0pHTX5pw3rrWkoGJmnj0JMaDXFc6zSGuUXOu3Ed3U6lvAnY6COi04zqeTdB6Gw+W0Y60Hv3V7j4vD4l2t43Rp+5f2wlHcG2Ffs0aj1PG0nTR56+HOBeX6KA1Q==");
		JSONObject j = new JSONObject(map);
		System.out.println(vl.verify(j.toString()));
	}
}
