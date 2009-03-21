package com.myJava.file.metadata.posix.jni.wrapper;

import com.myJava.object.ToStringHelper;

/**
 * This class mimics the "stat" structure returned on POSIX systems.
 * 
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 7019623011660215288
 */

 /*
 Copyright 2005-2009, Olivier PETRUCCI.

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
public class GetDataResult extends AbstractMethodResult {
	public long st_ctime;
	public long st_mtime;
	public long st_atime;
	public long st_mode;
	public long st_size;
	
	/**
	 * File device number
	 */
	public long st_dev;
	
	/**
	 * Inode : File serial number
	 */
	public long st_ino;
	
	/**
	 * Nr of hard links
	 */
	public long st_nlink;
	public long st_uid;
	public long st_gid;
	
	/**
	 * Device type if inode is a device
	 */
	public long st_rdev;
	
	/**
	 * Filesystem's block size
	 */
	public long st_blksize;
	
	/**
	 * Actual nr of allocated blocks
	 */
	public long st_blocks;
	public long __unused4;
	public long __unused5;

	public String toString() {
		StringBuffer sb = ToStringHelper.init(this);
		
		ToStringHelper.append("st_ctime", st_ctime, sb);
		ToStringHelper.append("st_mtime", st_mtime, sb);
		ToStringHelper.append("st_atime", st_atime, sb);
		ToStringHelper.append("st_mode", st_mode, sb);
		ToStringHelper.append("st_size", st_size, sb);
		ToStringHelper.append("st_dev", st_dev, sb);
		ToStringHelper.append("st_ino", st_ino, sb);
		ToStringHelper.append("st_nlink", st_nlink, sb);
		ToStringHelper.append("st_uid", st_uid, sb);
		ToStringHelper.append("st_gid", st_gid, sb);
		ToStringHelper.append("st_rdev", st_rdev, sb);
		ToStringHelper.append("st_blksize", st_blksize, sb);
		ToStringHelper.append("st_blocks", st_blocks, sb);
		ToStringHelper.append("__unused4", __unused4, sb);
		ToStringHelper.append("__unused5", __unused5, sb);
		ToStringHelper.append("returnCode", returnCode, sb);
		ToStringHelper.append("errorNumber", errorNumber, sb);
		ToStringHelper.append("transcodedErrorNumber", transcodedErrorNumber, sb);
		
		return ToStringHelper.close(sb);
	}
}