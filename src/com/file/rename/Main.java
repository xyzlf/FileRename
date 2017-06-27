package com.file.rename;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("--- start ---");
		long startTime = System.currentTimeMillis();
		FileUtil.renameFileMain();
		System.out.println("--- end --- \n(ºÄÊ±)£º" + (System.currentTimeMillis() - startTime));
	}

}
