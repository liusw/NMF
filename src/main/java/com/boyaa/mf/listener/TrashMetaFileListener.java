package com.boyaa.mf.listener;

import com.boyaa.base.utils.AppContext;
import com.boyaa.base.utils.Constants;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 废弃的站点
 * 
 * @作者 : huangyineng
 * @日期 : 2016-3-14 下午2:33:44
 */
public class TrashMetaFileListener extends FileAlterationListenerAdaptor {
	private static Logger logger = Logger.getLogger(TrashMetaFileListener.class);
	private List<FileAlterationMonitor> monitors = new ArrayList<FileAlterationMonitor>();

	public void onFileCreate(File file) {
		try {
			logger.info(file.getAbsolutePath() + " change!");
			Constants.initConfig();
		} catch (Exception e) {
			logger.error(file.getAbsolutePath() + ": " + e.getMessage());
		}
	}

	public void onFileChange(File file) {
		try {
			logger.info(file.getAbsolutePath() + " change!");
			Constants.initConfig();
		} catch (Exception e) {
			logger.error(file.getAbsolutePath() + ": " + e.getMessage());
		}
	}

	public void start() throws Exception {
		// the parse file path is get from the global config file
		String path = AppContext.local.get(com.boyaa.mf.constants.Constants.TRASH_META_MONITOR_PATH);
		String[] names = AppContext.local.get(com.boyaa.mf.constants.Constants.TRASH_META_MONITOR_FILES).split(Constants.COMMA_SEPERATE);

		for (String name : names) {
			if (new File(path, name).exists()) {
				FileAlterationObserver observer = new FileAlterationObserver(path, FileFilterUtils.nameFileFilter(name));
				observer.addListener(this);
				FileAlterationMonitor monitor = new FileAlterationMonitor(Constants.MONITOR_INTERVAL, observer);
				monitors.add(monitor);
				monitor.start();
			}
		}
	}

	public void stop() throws Exception {
		for (FileAlterationMonitor monitor : monitors)
			monitor.stop();
	}
}
