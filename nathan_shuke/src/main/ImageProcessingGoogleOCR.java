package main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

/**
 * Helper class to process image-encoding before calling Google API, in order to minimize failed API calls and 
 * associated costs.
 * 
 * @author Nathan Zheng
 * 
 */
public class ImageProcessingGoogleOCR {
	
	/**
	 * 1. Ensures encoding is in proper Base64.
	 * 2. Ensure that encoding represents an image. 
	 * 3. Ensure image file is .jpg, .png, or jpeg.
	 * 4. Find the size of the photo to prevent excessive uploads (max. 5 megabytes).
	 * @param inputEncoding
	 *     - Base64-encoded string to be processed to ensure image identity before API call.
	 * @return
	 *     - True if encoded String satisfies all conditions before API-Call processing with GoogleOCR, false otherwise.
	 */
	public static boolean imageProcessing(String inputEncoding) {
		if(!properEncoding(inputEncoding)) {
			return false;
		} else if(!isImage(inputEncoding)) {
			return false;
		} else if(!imageFileCorrect(inputEncoding)) {
			return false;
		} else if(!imageSizeCorrect(inputEncoding)) {
			return false;
		} else {
			return true;
		}
	}
				
	/**
	 * 1. Ensures input String is in proper Base64.
	 * @param inputEncoding
	 *     - String to check for Base64 encoding.
	 * @return
	 *     - True if string is Base64, false otherwise.
	 */
	public static boolean properEncoding(String inputEncoding) {
		if(Pattern.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$", inputEncoding)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 2. Ensure that encoding represents an image. 
	 * @param inputEncoding
	 *     - String to check for identity as an image.
	 * @return
	 *     - True if string represents image, false otherwise.
	 */	   
	public static boolean isImage(String inputEncoding) {
		
		// create a buffered image
		byte[] imageByte = Base64.getDecoder().decode(inputEncoding);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(imageByte);
		try {
			if(ImageIO.read(byteStream) == null) { //returns BufferedImage, which will be false if String is not valid representation of image.
				System.out.println("Encoded string cannot be read as an image.");
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			System.out.println("Error occurred while processing image encoding.");
			return false;	//If Exception, input cannot be read as an image, thus return false
		}	
	}
	
	/**
	 * 3. Ensure image file is .jpg, .png, or jpeg.
	 * @param inputEncoding
	 *     - String to check for proper file extension (.jpg, jpeg, .png).
	 * @return
	 *     - True if file extension of image is .jpg, jpeg, or .png.
	 */
	public static boolean imageFileCorrect(String inputEncoding) {
		
		byte[] decodedBytes = Base64.getDecoder().decode(inputEncoding);
		String hexString = bytesToHex(decodedBytes);
		String prefix = hexString.substring(0,8);
		String prefixTwo = hexString.substring(12,24);
		String prefixPNG = hexString.substring(0,16);
		
		if(prefix.equals("FFD8FFDB")) {		//.jpg, .jpeg
			return true;
		} else if(prefix.equals("FFD8FFE0") && prefixTwo.equals("4A4649460001")) {
			return true;
		} else if(prefix.equals("FFD8FFE1") && prefixTwo.equals("457869660000")) {
			return true;
		} else if(prefixPNG.equals("89504E470D0A1A0A")) {	 //.png				
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 4. Find the size of the photo to prevent excessive uploads (max. 5 megabytes).
	 * @param inputEncoding
	 *     - String representing image whose size we need to check.
	 * @return
	 *     - True if file is under 5 MB., false otherwise.
	 */
	public static boolean imageSizeCorrect(String inputEncoding) {
		
		byte[] decodedBytes = Base64.getDecoder().decode(inputEncoding);
		double numBytes = decodedBytes.length;
		if((numBytes / 1048576) > 5) {
			System.out.println("Image size exceeds 5 megabytes (MB), please reduce size.");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Converts byte array into hexadecimal String.
	 * @param bytes
	 *     - byte array to convert.
	 * @return
	 *     - hexadecimal String representation of byte array.
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for(int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}