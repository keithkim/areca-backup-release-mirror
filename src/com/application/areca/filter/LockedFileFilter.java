package com.application.areca.filter;

import java.io.File;

import com.myJava.file.FileSystemManager;
import com.myJava.object.Duplicable;
import com.myJava.object.EqualsHelper;
import com.myJava.object.HashHelper;

/**
 * 
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 5570316944386086207
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
public class LockedFileFilter extends AbstractArchiveFilter {

	public void acceptParameters(String parameters) {
    }
    
    public boolean acceptIteration(File entry) {
        return true;
    }
    
    /**
     * Cette condition ne s'applique que sur les fichiers (pour des raisons d'optimisation).
     * Les repertoires retournent systematiquement "true"
     */
    public boolean acceptStorage(File entry) {
        if (entry == null) {
            return false;
        } else if (FileSystemManager.isDirectory(entry)) {
            return true;
        } else {
            if (
            		(! FileSystemManager.exists(entry)) 			// Cas specifique des liens symboliques qui ne pointent sur rien : on les accepte qd même
            		|| (FileSystemManager.isReadable(entry))
            ) {
                return exclude;
            } else {
                return ! exclude;
            }
        }
    }
    
    public Duplicable duplicate() {
        LockedFileFilter filter = new LockedFileFilter();
        filter.exclude = this.exclude;
        return filter;
    }    
    
    public String getStringParameters() {
        return  null;
    }
    
    public boolean requiresParameters() {
        return false;
    }
    
    public boolean equals(Object obj) {
        if (obj == null || (! (obj instanceof LockedFileFilter)) ) {
            return false;
        } else {
            LockedFileFilter other = (LockedFileFilter)obj;
            return 
            	EqualsHelper.equals(this.exclude, other.exclude)
           	;
        }
    }
    
    public int hashCode() {
        int h = HashHelper.initHash(this);
        h = HashHelper.hash(h, this.exclude);
        return h;
    }
}
