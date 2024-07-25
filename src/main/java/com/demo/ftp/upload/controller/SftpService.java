package com.demo.ftp.upload.controller;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.Selectors;
import org.springframework.stereotype.Service;

import com.demo.ftp.upload.model.FileInfo;

@Service
public class SftpService {

    private static final String SOURCE_SERVER = "sftp://your_username@abcd/path/to/source/file.txt";
    private static final String DESTINATION_SERVER = "sftp://your_username@xyz/path/to/destination/file.txt";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public void transferFile(List<FileInfo> fileInfos) {
        FileSystemManager fsManager = null;
        FileObject sourceFile = null;
        FileObject localFile = null;
        FileObject destFile = null;
        
        System.out.println(fileInfos);
        

        try {
            fsManager = VFS.getManager();

            // Step 1: Copy the file from SOURCE_SERVER to local machine
             //sourceFile = fsManager.resolveFile(SOURCE_SERVER, getFileSystemOptions());
            for(FileInfo fileInfo :fileInfos) {
             localFile = fsManager.resolveFile(fileInfo.getUrl());
             System.out.println("localFile-->"+localFile);
             }
           // localFile.copyFrom(sourceFile, Selectors.SELECT_SELF);
             
             System.out.println("File localFile--->"+localFile);

            // Step 2: Copy the file from local machine to DESTINATION_SERVER
           // destFile = fsManager.resolveFile(DESTINATION_SERVER, getFileSystemOptions());
           // destFile.copyFrom(localFile, Selectors.SELECT_SELF);

            System.out.println("File transfer completed.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (sourceFile != null) sourceFile.close();
                if (localFile != null) localFile.close();
                if (destFile != null) destFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private FileSystemOptions getFileSystemOptions() {
        FileSystemOptions opts = new FileSystemOptions();
        StaticUserAuthenticator auth = new StaticUserAuthenticator(null, USERNAME, PASSWORD);
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
        return opts;
    }
}