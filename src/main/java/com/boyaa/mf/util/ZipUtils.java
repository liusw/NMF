package com.boyaa.mf.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ZipUtils {
	static Logger logger = Logger.getLogger(ZipUtils.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	/**
	 * 获取压缩或者非压缩的读取对象
	 * @param path
	 * @param compress
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static BufferedReader getReader(String path, boolean compress) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if(compress)
			return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path)), "utf8"));
		else
			return new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf8"));
	}
	
	/**
	 * 获取压缩或者非压缩的写入对象
	 * @param path
	 * @param compress
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static PrintWriter getWriter(String path, boolean compress) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if(compress)
			return new PrintWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(path, true)), "UTF-8"), true);
		else
			return new PrintWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"), true);
	}
	
	public static byte[] gzip(String value) {
		if (value == null || value.length() == 0) return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(value.getBytes());
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return out.toByteArray();
	}

	public static String gunzip(byte[] value) {
		if (value == null) return null;
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		try {
			in = new ByteArrayInputStream(value);
			ginzip = new GZIPInputStream(in);

			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			return out.toString();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	public static final byte[] zip(String value) {
		if (value == null) return null;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(value.getBytes());
            zout.closeEntry();
            return out.toByteArray();
        } catch (IOException e) {
        	logger.error(e);
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                	logger.error(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                	logger.error(e);
                }
            }
        }
        return null;
	}

	public static final String unzip(byte[] value) {
		if (value == null) return null;
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(value);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			return out.toString();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	
	public static void compressGzip(String inputFilePath, String outFilePath) {
		GZIPOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new GZIPOutputStream(new FileOutputStream(outFilePath));
			in = new FileInputStream(inputFilePath);
			
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.finish();
			out.flush();
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		} finally{
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					errorLogger.error(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					errorLogger.error(e);
				}
			}
		}
	}
}
