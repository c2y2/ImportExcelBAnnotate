package org.c2y2.imports.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.c2y2.imports.config.ImportFileConfig;
import org.c2y2.imports.entity.User;

public class GenerateExcelTpTest {
	public static void main(String[] args) {
		ImportFileConfig importFileConfig = AnnotateConfigHandler.handler(User.class);//生产配置
		try {
			System.out.println(importFileConfig.getFileType());
			Workbook workbook = GenerateExcelTp.generateExcelTp(importFileConfig);
			File tpFile = new File("E:/javaExamples/imports/tpFile.xls");//注意生成的excel类型，请跟importFileConfig 中类型保持一致
			OutputStream  os = new FileOutputStream(tpFile);
			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
