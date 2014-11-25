package org.c2y2.imports.test;

import java.io.File;
import java.util.List;

import org.c2y2.imports.config.ImportFileConfig;
import org.c2y2.imports.core.AnnotateConfigHandler;
import org.c2y2.imports.core.ExcelParseToList;
import org.c2y2.imports.entity.User;

public class ExcelTest {
	public static void main(String[] args) {
		ImportFileConfig importFileConfig = AnnotateConfigHandler.handler(User.class);
		importFileConfig.setFile(new File("D:/Jfinal/Annotate/resource/user.xls"));
		ExcelParseToList<User> excelParseToList = new ExcelParseToList<User>();
		try {
			List<User> userLIst = excelParseToList.getListFromExcel(importFileConfig);
			for (User user : userLIst) {
				System.out.println(user.getUsername()+"=="+user.getAge()+"=="+user.getSex());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
