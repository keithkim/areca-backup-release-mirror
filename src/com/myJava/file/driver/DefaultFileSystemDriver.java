package com.myJava.file.driver;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.myJava.configuration.FrameworkConfiguration;
import com.myJava.file.attributes.Attributes;
import com.myJava.file.attributes.AttributesHelper;
import com.myJava.object.HashHelper;
import com.myJava.object.ToStringHelper;
import com.myJava.system.OSTool;
import com.myJava.util.log.Logger;

/**
 * Driver par d�faut : relaie tous ses appels � la classe "File".
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 5653799526062900358
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class DefaultFileSystemDriver extends AbstractFileSystemDriver {
    
    protected static boolean USE_BUFFER = FrameworkConfiguration.getInstance().useFileSystemBuffer();
    protected static int BUFFER_SIZE = FrameworkConfiguration.getInstance().getFileSystemBufferSize();
    
    public boolean canRead(File file) {
        return file.canRead();
    }
    
    public boolean canWrite(File file) {
        return file.canWrite();
    }
    
    public boolean createNewFile(File file) throws IOException {
        return file.createNewFile();
    }
    
    public boolean delete(File file) {
        if (! file.exists()) {
            return true;
        } else {
            return file.delete();
        }
    }
    
    public boolean exists(File file) {
        return file.exists();
    }

    public boolean supportsLongFileNames() {
        return ! OSTool.isSystemWindows();
    }
    
    public File getAbsoluteFile(File file) {
        return file.getAbsoluteFile();
    }

    public void flush() throws IOException {
        // Does nothing
    }
    
    /**
     * "/home/toto/titi/../tutu" will return "/home/toto/titi/../tutu" 
     * @see File#getAbsolutePath()
     */
    public String getAbsolutePath(File file) {
        return normalize(file.getAbsolutePath());
    }
    
    public File getCanonicalFile(File file) throws IOException {
        return file.getCanonicalFile();
    }
    
    public boolean createSymbolicLink(File symlink, String realPath) throws IOException {
        return AttributesHelper.createSymbolicLink(symlink, realPath);
    }
  
    public void unmount() throws IOException {
        this.flush();
    }
    
    /**
     * "/home/toto/titi/../tutu" will return "/home/toto/tutu" 
     * @see File#getCanonicalPath()
     */
    public String getCanonicalPath(File file) throws IOException {
        return normalize(file.getCanonicalPath());
    }
    
    public String getName(File file) {
        return file.getName();
    }
    
    public String getParent(File file) {
        return normalize(file.getParent());
    }
    
    public File getParentFile(File file) {
        return file.getParentFile();
    }
    
    public String getPath(File file) {
        return normalize(file.getPath());
    }
    
    public boolean isAbsolute(File file) {
        return file.isAbsolute();
    }
    
    public boolean isDirectory(File file) {
        return file.isDirectory();
    }
    
    public boolean isFile(File file) {
        return file.isFile();
    }
    
    public boolean isHidden(File file) {
        return file.isHidden();
    }
    
    public long lastModified(File file) {
        return file.lastModified();
    }
    
    public long length(File file) {
        return file.length();
    }
    
    public String[] list(File file, FilenameFilter filter) {
        try {
            String[] files = file.list(filter);
            normalize(files);
            return files;
        } catch (RuntimeException e) {
            if (file != null) {
                Logger.defaultLogger().error("Error during call : " + file.getAbsolutePath(), e);
            }
            throw e;
        }
    }
    
    public String[] list(File file) {
        try {
            String[] files = file.list();
            normalize(files);
            return files;
        } catch (RuntimeException e) {
            if (file != null) {
                Logger.defaultLogger().error("Error during call : " + file.getAbsolutePath(), e);
            }
            throw e;
        }
    }
    
    public File[] listFiles(File file, FileFilter filter) {
        try {
            return file.listFiles(filter);
        } catch (RuntimeException e) {
            if (file != null) {
                Logger.defaultLogger().error("Error during call : " + file.getAbsolutePath(), e);
            }
            throw e;
        }
    }
    
    public File[] listFiles(File file, FilenameFilter filter) {
        try {
            return file.listFiles(filter);
        } catch (Exception e) {
            if (file != null) {
                Logger.defaultLogger().error("Error during call : " + file.getAbsolutePath(), e);
            }
            return null;
        }
    }
    
    public File[] listFiles(File file) {
        try {
            if (file.isFile()) {
                return new File[0];
            } else {
                return file.listFiles();
            }
        } catch (NullPointerException e) {
            // Seems to be a bug in open source VMs (GNU libgcj) with some characters (german "Umlaut"s for instance)
            if (file != null) {
                Logger.defaultLogger().error("Error during file list (for : " + file.getAbsolutePath() + "). It is probably due to the fact that you use an incompatible Java Runtime Environment. Note that Sun Microsystem's Runtime Environment is highly recommended.", e);
            }
            throw e;
        } catch (RuntimeException e) {
            Logger.defaultLogger().error("Error during call : " + file.getAbsolutePath(), e);
            throw e;
        }
    }
    
    public boolean mkdir(File file) {
        return file.mkdir();
    }
    
    public boolean mkdirs(File file) {
        return file.mkdirs();
    }
    
    public boolean renameTo(File source, File dest) {
        return source.renameTo(dest);
    }
    
    public boolean setLastModified(File file, long time) {
        if (time < 0) {
            //throw new IllegalArgumentException("Negative time for file [" + file.getAbsolutePath() + "] : " + time);
            time = 0;
        }
        return file.setLastModified(time);
    }
    
    public boolean setReadOnly(File file) {
        return file.setReadOnly();
    }

    public InputStream getFileInputStream(File file) throws IOException {
        if (USE_BUFFER) {
            return new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
        } else {
            return new FileInputStream(file);
        }
    }

    public OutputStream getCachedFileOutputStream(File file) throws IOException {
        return getFileOutputStream(file);
    }
    
    public OutputStream getFileOutputStream(File file) throws IOException {
        if (USE_BUFFER) {
            return new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
        } else {
            return new FileOutputStream(file);
        }
    }
    
    public OutputStream getFileOutputStream(File file, boolean append) throws IOException {
        if (USE_BUFFER) {
            return new BufferedOutputStream(new FileOutputStream(file, append), BUFFER_SIZE);
        } else {
            return new FileOutputStream(file, append);
        }
    }
    
    public Attributes getAttributes(File f) throws IOException {
        return AttributesHelper.readFileAttributes(f);
    }

    public void applyAttributes(Attributes p, File f) throws IOException {
        AttributesHelper.applyFileAttributes(f, p);
    }
    
    public int hashCode() {
        return HashHelper.initHash(this);
    }
    
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            return (o instanceof DefaultFileSystemDriver);
        }
    }

    public boolean directFileAccessSupported() {
        return true;
    }
    
    public void deleteOnExit(File f) {
        f.deleteOnExit();
    }
 
    public String toString() {
        StringBuffer sb = ToStringHelper.init(this);
        return ToStringHelper.close(sb);
    }

    public short getAccessEfficiency() {
        return ACCESS_EFFICIENCY_GOOD;
    }

    public boolean isContentSensitive() {
        return false;
    }

    public FileInformations getInformations(File file) {
        return new FileInformations(this, file);
    }
}
