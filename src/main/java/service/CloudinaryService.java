package service;

import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class CloudinaryService {

	private static final String BASE64_PREFIX="data:image/jpg;base64,";
	
	private Cloudinary cloudinary;

	public CloudinaryService() {
		//Dotenv dotenv = Dotenv.load();
                String cloudinaryUrl = "cloudinary://383698159469413:XJIrxiU7HyBzJY74pBdd4q1bwF8@dulpvbj0d"; //Mi URL de cloudinaria junto con el API Key y API Secret
		cloudinary = new Cloudinary(cloudinaryUrl);
		cloudinary.config.secure = true;
		//System.out.println(cloudinary.config.cloudName);
	}

	public String upload(String base64Data) throws IOException {
		Map<String, String> options = new HashMap<String, String>();
		options.put("folder", "mediaflix");
		
		Map cloudinaryParams = cloudinary.uploader().upload(BASE64_PREFIX + base64Data, options);
		
		String url = (String) cloudinaryParams.get("url");
                //System.out.println(url);
		return url;
	}
}