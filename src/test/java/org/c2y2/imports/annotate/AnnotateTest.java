package org.c2y2.imports.annotate;

import org.c2y2.imports.config.ImportFileConfig;
import org.c2y2.imports.core.AnnotateConfigHandler;
import org.c2y2.imports.entity.User;

public class AnnotateTest {
	public static void main(String[] args) {
		ImportFileConfig importFileConfig = AnnotateConfigHandler.handler(User.class);
		if(importFileConfig!=null){
			System.out.println(importFileConfig.getFileType());
			System.out.println(importFileConfig.getColumnList().size());
		}
	}
}
