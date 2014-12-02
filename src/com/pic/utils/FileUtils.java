package com.pic.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Properties;

import org.apache.log4j.Logger;

public class FileUtils {

	private static Logger log = Logger.getLogger(FileUtils.class);

	public static Properties loadPropertiesByCurrentThread(String path) throws FileNotFoundException, IOException {
		InputStream in = null;
		Properties p = new Properties();
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			if (in == null) {
				throw new FileNotFoundException(path + "文件不存在");
			}
			p.load(in);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("加载配置文件后，关闭文件时错误！文件：" + path, e);
				}
			}
		}
		return p;
	}

	public static Properties loadPropertiesByFile(String path) throws IOException {
		Properties p = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		p.load(in);

		return p;
	}

	/**
	 * @Title: getFileSuffix
	 * @Description:(获取路径后缀名 by cy)
	 * @param path
	 * @return String
	 */
	public static String getFileSuffix(final String path) {
		String suffix = null;
		if (path != null) {
			suffix = "";
			if (path.lastIndexOf('.') != -1) {
				suffix = path.substring(path.lastIndexOf('.') + 1);
			}
		}
		return suffix;
	}

	/**
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		try {
			return deleteFile(new File(path));
		} catch (Exception e) {
			log.warn("delete file error this path is" + path, e);
		}
		return false;
	}

	public static boolean deleteFile(File file) {
		if (null == file) {
			return false;
		}
		try {
			boolean flag = true;
			if (!file.exists()) {
				return false;
			}
			if (file.isFile()) {
				return file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File childFile : files) {
					if (!deleteFile(childFile.getAbsolutePath())) {
						flag = false;
						break;
					}
				}
				if (!flag) {
					return flag;
				}
				return file.delete();
			}
		} catch (Exception e) {
			log.warn("delete file error this path is" + file.getPath(), e);
		}
		return false;
	}

	public static boolean deleteFileBatch(String[] path) {
		boolean flag = true;
		try {
			for (String string : path) {
				File file = new File(string);
				if (file.exists() && file.isFile()) {
					if (file.delete()) {
						flag = false;
					}
				}
			}
		} catch (Exception e) {
			log.info("删除文件错误", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * @Title: createFile
	 * @Description: TODO(创建单个文件 by cy)
	 * @param filePath 文件名，包含路径
	 * @return boolean
	 */
	public static boolean createFile(String filePath) {

		File file = new File(filePath);
		if (file.exists()) {
			return false;
		}
		if (filePath.endsWith(File.separator)) {
			return false;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		try {
			if (file.createNewFile()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isExcelType(String path) {
		String suffix = getFileSuffix(path);
		if ("xls".equals(suffix)) {
			return true;
		}
		return false;
	}

	/**
	 * 比较小的文件生成
	 * 
	 * @return
	 * @throws IOException
	 **/
	public static File byte2File(byte[] data, String fileAllPath) throws IOException {
		WritableByteChannel channel = null;
		ReadableByteChannel read = null;
		File file = null;
		try {
			file = new File(fileAllPath);
			int bufferLength = 2048;
			FileOutputStream output = new FileOutputStream(file);
			channel = Channels.newChannel(output);
			read = Channels.newChannel(new ByteArrayInputStream(data));
			ByteBuffer buffer = ByteBuffer.allocate(bufferLength);
			while (read.read(buffer) != -1) {
				buffer.flip();
				channel.write(buffer);
				buffer.clear();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != read) {
				read.close();
			}
			if (null != channel) {
				channel.close();
			}
		}
		return file;
	}

	/**
	 * @Title: write
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param input
	 * @param output
	 * @param bufferLength
	 * @param count
	 * @throws IOException 设定文件 void 返回类型
	 */
	public static void write(InputStream input, OutputStream output, int bufferLength) throws IOException {
		WritableByteChannel channel = null;
		ReadableByteChannel read = null;
		try {
			channel = Channels.newChannel(output);
			read = Channels.newChannel(input);
			ByteBuffer buffer = ByteBuffer.allocate(bufferLength);
			while (read.read(buffer) != -1) {
				buffer.flip();
				channel.write(buffer);
				buffer.clear();
			}
		} finally {
			if (null != read) {
				read.close();
			}
			if (null != channel) {
				channel.close();
			}
		}
	}

	@Deprecated
	public static void copyFile(File srcFile, File desFile) throws IOException {
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(srcFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(desFile));

			byte[] buffer = new byte[1024];
			while ((inputStream.read(buffer)) != -1) {
				outputStream.write(buffer);
			}
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != inputStream) {
				inputStream.close();
			}
			if (null != outputStream) {
				outputStream.close();
			}
		}
	}

	@Deprecated
	public static void copyFile(String srcPath, String desPath) throws IOException {
		File srcFile = new File(srcPath);
		File desFile = new File(desPath);
		copyFile(srcFile, desFile);
	}

	public static boolean downloadImgWebToLocal(String url, String path) throws IOException {
		boolean flag = false;
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new URL(url).openStream();
			output = new FileOutputStream(new File(path));
			int bufferLength = 2048;
			write(input, output, bufferLength);
			flag = true;
		} catch (IOException e) {
			log.info("下载文件错误", e);
			flag = false;
		} finally {
			if (null != input) {
				input.close();
				input = null;
			}
			if (null != output) {
				output.close();
				output = null;
			}
		}
		return flag;
	}

	/**
	 * 这个方法是为了方便写的。性能和正确性没有测试。所以不建议用
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static String readTxtFile(String path) throws IOException {
		StringBuilder builder = new StringBuilder();
		File file = new File(path);
		if (!file.exists()) {
			return "";
		}
		BufferedInputStream buffered = null;
		try {
			buffered = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		byte[] buff = new byte[1024];
		try {
			int bytesRead = 0;
			while ((bytesRead = buffered.read(buff)) != -1) {
				builder.append(new String(buff, 0, bytesRead, "GB2312"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffered.close();
		return builder.toString();

	}

	public static void main(String[] args) throws IOException {
		String path = null;
		System.out.println(deleteFile(path));

	}
}
