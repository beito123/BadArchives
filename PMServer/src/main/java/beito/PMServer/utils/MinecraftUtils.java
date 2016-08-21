package beito.PMServer.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
	author: beito123
*/

public class MinecraftUtils {//skin

	public static boolean saveConvertSkinDataToImage(String path, String savePath){//hmm...
		File file = new File(path);
		if(!file.exists()) return false;
		try{
			FileInputStream is = new FileInputStream(file.getPath());
			BufferedImage image = convertSkinDataToImage(is);
			if(image == null) return false;
			ImageIO.write(image, "png", new File(savePath));
		}catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}

	public static boolean convertSkinDataToFaceImage(String path, String savePath){
		File file = new File(path);
		if(!file.exists()) return false;
		try{
			FileInputStream is = new FileInputStream(file.getPath());
			BufferedImage image = convertSkinDataToImage(is);
			if(image == null) return false;
			ImageIO.write(image.getSubimage(8, 8, 8, 8), "png", new File(savePath));
		}catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}

	//ref https://gist.github.com/sekjun9878/762dbcef367dd01e2b8e
	public static BufferedImage convertSkinDataToImage(FileInputStream is) throws IOException{
		if(is.available() != (64 * 32 * 4) && is.available() != (64 * 64 * 4)) return null;
		int height = 64;
		int width = 64;
		BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

		byte[] pixel = new byte[4];
		int r, g, b, a;
		for(int y = 0;y < height;y++){
			for(int x = 0;x < width;x++){
				is.read(pixel);
				r = Integer.parseInt(binhex(pixel).substring(0, 2), 16);
				g = Integer.parseInt(binhex(pixel).substring(2, 4), 16);
				b = Integer.parseInt(binhex(pixel).substring(4, 6), 16);
				a = Integer.parseInt(binhex(pixel).substring(6, 8), 16);
				//debug
				/*System.out.println("skinConvert:  y." + y + " x." + x + " color(rgba)." + binhex(pixel)
						+ " r." + r + " g." + g + " b." + b + " a." + a
							+ " c." + argb(a, r, g, b));*/
				image.setRGB(x, y, argb(a, r, g, b));
			}
		}
		is.close();
		return image;
	}

	//ref http://nodamushi.hatenablog.com/entry/20111012/1318436587
	public static int argb(int a, int r, int g, int b){
		return a << 24 | r << 16 | g << 8 | b;
	}

	//16進数変換
	public static String binhex(byte[] bytes){
		String v = "";
		for(byte b : bytes){
			v += String.format("%02x", b & 0xff);
	    }
		return v;
	}
}
