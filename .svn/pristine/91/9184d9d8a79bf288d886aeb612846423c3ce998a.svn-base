package com.boyaa.mf.listener;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.service.config.HBaseMetaService;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liusw
 * 创建时间：16-3-25.
 */
@Component
public class HbTableFilesListener extends FileAlterationListenerAdaptor {
    private static Logger logger = Logger
            .getLogger(TrashMetaFileListener.class);
    private List<FileAlterationMonitor> monitors = new ArrayList<FileAlterationMonitor>();

    private String monitorParentPath;
    private String monitorChildPath;
    private String monitorFiles;

    @Autowired
    private HBaseMetaService hBaseMetaService;

    public HbTableFilesListener() {
    }

    public HbTableFilesListener(String monitorParentPath,
                                 String monitorChildPath, String monitorFiles) {
        this.monitorParentPath = monitorParentPath;
        this.monitorChildPath = monitorChildPath;
        this.monitorFiles = monitorFiles;
    }

    public void onFileCreate(File file) {
        try {
            logger.info(file.getAbsolutePath() + " create!");
            Constants.initTrashConfig();
            hBaseMetaService.updateConfig(file);
        } catch (Exception e) {
            logger.error(file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    public void onFileChange(File file) {
        try {
            logger.info(file.getAbsolutePath() + " change!");
            Constants.initTrashConfig();
            hBaseMetaService.updateConfig(file);
        } catch (Exception e) {
            logger.error(file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    public void onFileDelete(File file) {
        try {
            logger.info(file.getAbsolutePath() + " delete!");
            Constants.initTrashConfig();
        } catch (Exception e) {
            logger.error(file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    public void start() throws Exception {
        if (!StringUtils.isEmpty(monitorChildPath))
            monitorParentPath = monitorParentPath + "/" + monitorChildPath;
        File dir = new File(monitorParentPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileAlterationObserver observer = new FileAlterationObserver(dir,
                FileFilterUtils.and(new IOFileFilter[] {
                        FileFilterUtils.fileFileFilter(),
                        FileFilterUtils.suffixFileFilter(".xml") }));
        observer.addListener(this);
        FileAlterationMonitor monitor = new FileAlterationMonitor(
                com.boyaa.base.utils.Constants.MONITOR_INTERVAL, observer);
        monitors.add(monitor);
        monitor.start();
    }

    public void stop() throws Exception {
        for (FileAlterationMonitor monitor : monitors)
            monitor.stop();
    }

    public String getMonitorParentPath() {
        return monitorParentPath;
    }

    public void setMonitorParentPath(String monitorParentPath) {
        this.monitorParentPath = monitorParentPath;
    }

    public String getMonitorFiles() {
        return monitorFiles;
    }

    public void setMonitorFiles(String monitorFiles) {
        this.monitorFiles = monitorFiles;
    }

    public String getMonitorChildPath() {
        return monitorChildPath;
    }

    public void setMonitorChildPath(String monitorChildPath) {
        this.monitorChildPath = monitorChildPath;
    }
}
