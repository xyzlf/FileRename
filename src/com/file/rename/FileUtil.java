package com.file.rename;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	private static final String FILE_NAME_DIR = "dir.txt";
	private static final String FILE_NAME_ERROR = "error.txt";
	private static final String FILE_NAME_RESULT = "result.txt";

	/**
	 * �������ļ����
	 */
	public static void renameFileMain() {
		String dirConfigFileName = FILE_NAME_DIR; // �ļ�Ŀ¼�Ի��зָ�
		File dirFile = new File(dirConfigFileName);
		if (!dirFile.exists()) {
			String errorInfo = "�ļ�����" + dirConfigFileName + "��������";
			writeErrorInfo(errorInfo);
			return;
		}
		List<String> fileDirList = readFileDir(dirFile);
		renameFile(fileDirList, "v1.");
	}

	private static void renameFile(List<String> fileDirList, String startTag) {
		if (null == fileDirList || fileDirList.isEmpty()) {
			String errorInfo = "�ļ�Ŀ¼�б�Ϊ��^_^-----------------�����Ҫ���������ļ�Ŀ¼�� �磺d:\\V1.7.1-----------------";
			writeErrorInfo(errorInfo);
			return;
		}

		for (String fileDirName : fileDirList) {
			if (fileDirName == null || fileDirName.length() == 0) {
				String errorInfo = "�ļ�Ŀ¼����Ϊ��";
				writeErrorInfo(errorInfo);
				continue;
			}

			File dirFile = new File(fileDirName);
			if (!dirFile.exists()) {
				String errorInfo = "�ļ�Ŀ¼��" + dirFile + "��������";
				writeErrorInfo(errorInfo);
				continue;
			}

			final File[] files = dirFile.listFiles();
			if (null == files || files.length == 0) {
				String errorInfo = "�ļ�Ŀ¼��" + dirFile + "��������ļ�����Ϊ0";
				writeErrorInfo(errorInfo);
				continue;
			}

			for (int i = 0; i < files.length; i++) {
				File oldFile = files[i];
				if (null == oldFile || oldFile.isDirectory() || !oldFile.exists()) {
					String errorInfo = "�ļ���" + oldFile + "�������ڣ�����ΪĿ¼";
					writeErrorInfo(errorInfo);
					continue;
				}
				final String oldFileName = oldFile.getName();
				String newFileName = null;

				if (oldFileName != null) {
					String suffixName = oldFileName.substring(oldFileName.lastIndexOf("."));
					if (oldFileName.contains(startTag)) {
						int startIndex = oldFileName.indexOf(startTag);
						newFileName = oldFileName.substring(startIndex, oldFileName.length()) + suffixName;
					} else {
						continue;
					}
				}
				File newFile = new File(fileDirName + File.separator + newFileName);
				if (!newFile.exists()) {
					oldFile.renameTo(newFile);
				} else {
					String errorInfo = "��" + newFileName + "���Ѿ�������";
					writeErrorInfo(errorInfo);
				}
			}
			writeResultInfo(fileDirName);
		}
	}

	private static List<String> readFileDir(File dirFile) {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(dirFile));
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = null;
			List<String> fileDirList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				fileDirList.add(line.trim());
			}
			reader.close();

			return fileDirList;
		} catch (FileNotFoundException e) {
			writeErrorInfo(e.getMessage());
		} catch (IOException e) {
			writeErrorInfo(e.getMessage());
		}
		return null;
	}

	private static void writeErrorInfo(String errorInfo) {
		writeInfo(FILE_NAME_ERROR, errorInfo);
	}
	
	private static void writeResultInfo(String dirName) {
		writeInfo(FILE_NAME_RESULT, dirName);
	}
	
	private static void writeInfo(String fileName, String info) {
		if (null == fileName || fileName.length() == 0) {
			return;
		}
		try {
			File errorFile = new File(fileName);
			if (!errorFile.exists()) {
				errorFile.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(errorFile.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timeStr = formatter.format(System.currentTimeMillis());
			
			if (FILE_NAME_ERROR.equals(fileName)) {
				bufferWritter.write("ʱ�䣺" + timeStr + "------>" + info);
				bufferWritter.write("\r\n");
			} else if (FILE_NAME_RESULT.equals(fileName)) {
				bufferWritter.write("==================== rename success ====================\r\n");
				bufferWritter.write("finish time:" + timeStr + "\r\n");
				bufferWritter.write("�ļ�Ŀ¼:" + info + "\r\n\r\n\r\n");
			}
			bufferWritter.close();
		} catch (Exception ignore) {
		}
	}

}
