package main.java.ImageRecognitionAPIs;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * Image Processing for the BaiduOCRAPI to ensure image files submitted adhere to API requirements to prevent
 * failed API calls and reduce associated costs.
 * 
 * @author Nathan Zheng
 * 
 */
public class ImageProcessingBaiduOCR {
	
	private static byte[] imageBytes;	//Byte Array representation of image to process.
	private static String imageBase64;	//Base-64 String that represents encoding of image to process.
	private static String filetype; //.png, .jpg, or .bmp, etc.
	private static int byteSize; //size of image in bytes
	
	/**
	 * Note - Image is received as a MultipartFile Object, sent for API results as Base-64 encoded string.
	 * 
	 * 1. Convert MultipartFile into byte array to get Base-64 encoding.
	 * 2. Ensure that Base-64 encoding does not exceed 4 MB.
	 * 3. Ensure image file is .jpg, .png, or .bmp.
	 * 4. Ensure length and width of image are both at least 15 pixels, and maximum 4096 pixels.
	 * @param inputEncoding
	 *     - Base64-encoded string to be processed to ensure image identity before API call.
	 * @return
	 *     - True if encoded String satisfies all conditions before API-Call processing with GoogleOCR, false otherwise.
	 */
	
	/**
	 * The method to call that conducts all the processing/checks. First converts MultipartFile into Base-64 
	 * representation then runs methods to check.
	 * @param image
	 * 	- the image file to inspect
	 * @return
	 * 	- 0 if format incorrect, 1 if needs compression, 2 if format correct
	 */
	public static int checkFormat(MultipartFile image) {
		
		try {
			imageBytes = image.getBytes();
			imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {
			System.out.println("MultipartFile could not be encoded as Base-64 string.");
			return 0;
		}
		
		//compressible = true (image needs compression but other formatting correct), compression = false (no compression)
		boolean compressible = false;
		
		if(!properType()) {
			return 0;
		} 
		
		if(!properSize()) {
			compressible = true;
		} 
		
		if(!properDimensions()) {
			return 0;
		} else {
			if(compressible) {
				return 1;
			} else {
				return 2;
			}
		}
	}
	
	/**
	 * Compresses MultipartFile image if exceeds 4MB limit, called after other formatting
	 * properties are verified to be correct.
	 * @param image
	 * @return byte[] holding compressed image information.
	 */
	public static byte[] compressImage(MultipartFile image) {  
		
		try {
			imageBytes = image.getBytes();
			imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {
			System.out.println("MultipartFile could not be encoded as Base-64 string.");
			return null;
		}
		
		//Finds a ImageWriter that can process the type of image (filetype) inputted (.jpg, .png, .bmp)
		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName(filetype).next();
		
		//Get the default configuration parameters of the ImageWriter
		ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
		
		//Allow for Compression Parameters to be explicitly stated/modified
		jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		
		//Compression quality expressed as a float (currently compress to 70%)
		jpgWriteParam.setCompressionQuality(0.5f);
		
		//read compressed image data into compressedBytes ByteArray stream
		ByteArrayOutputStream compressedByteStream = new ByteArrayOutputStream();
		
		//create output stream for compressed image bytes
		ImageOutputStream outputStream = null;
		try {
			outputStream = ImageIO.createImageOutputStream(compressedByteStream);
		} catch (IOException e) {
			System.out.println("Output stream for compressed image bytes could not be initialized");
			return null;
		} 
		jpgWriter.setOutput(outputStream);
		
		//convert original image data into bufferedImage to allow for compression
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e) {
			System.out.println("Original image could not be read into a byte array input stream.");
		}
		
		//writes compressed image data into outputStream (ImageOutputStream)
		IIOImage outputImage = new IIOImage(originalImage, null, null);
		try {
			jpgWriter.write(null, outputImage, jpgWriteParam);
		} catch (IOException e) {
			System.out.println("Compressed image data could not written into ImageOutputStream.");
		}
		jpgWriter.dispose();
		
	//	return compressed byte array
		byte[] compressedImageBytes = compressedByteStream.toByteArray();
		
		return compressedImageBytes;
	}
	
	/**
	 * 2. Ensure image file is .jpg, .png, or .bmp
	 * @param inputEncoding
	 *     - String to check for proper file extension (.jpg, .png, or .bmp).
	 * @return
	 *     - True if file extension of image is .jpg, .png, or .bmp
	 */
	public static boolean properType() {
		
		String hexString = bytesToHex(imageBytes);
		String prefixBMP = hexString.substring(0,4);
		String prefixJPG = hexString.substring(0,8);
		String prefixJPGTwo = hexString.substring(12,24);
		String prefixPNG = hexString.substring(0,16);
		
		if(prefixJPG.equals("FFD8FFDB")) {		//.jpg, .jpeg
			filetype = "jpg";
			return true;
		} else if(prefixJPG.equals("FFD8FFE0") && prefixJPGTwo.equals("4A4649460001")) {
			filetype = "jpg";
			return true;
		} else if(prefixJPG.equals("FFD8FFE1") && prefixJPGTwo.equals("457869660000")) {
			filetype = "jpg";
			return true;
		} else if(prefixPNG.equals("89504E470D0A1A0A")) {	 //.png		
			filetype = "png";
			return true;
		} else if(prefixBMP.equals("424D")) {	//.bmp
			filetype = "bmp";
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 3. Find the size of the photo to prevent excessive uploads (max. 4 megabytes).
	 * @return
	 *     - True if file is under 4 MB., false otherwise.
	 */
	public static boolean properSize() {
		
		byteSize = imageBytes.length;
		if((byteSize / 1048576) > 4) {
			System.out.println("File size exceeds 4 megabytes (MB), please reduce size.");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Makes sure that dimensions of the processed Image are within bounds of 15-4096 pixels
	 * for height and width.
	 * @return
	 * 	- whether dimensions are in the proper range.
	 */
	public static boolean properDimensions() {
		
		ByteArrayInputStream byteStream = new ByteArrayInputStream(imageBytes);
		Image image = null;
		int imageWidth;
		int imageHeight;
		
		try {
			image = ImageIO.read(byteStream);
		} catch (IOException e) {
			System.out.println("Image could not be represented as an Image Object.");
			return false;
		}
		
		imageWidth = image.getWidth(null);
		imageHeight = image.getHeight(null);
		
		if(imageWidth >= 15 && imageHeight >= 15) {
			if(imageWidth <= 4096 && imageHeight <= 4096) {
				return true;
			}
		}
		return false;
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