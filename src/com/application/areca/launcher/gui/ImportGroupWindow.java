package com.application.areca.launcher.gui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.application.areca.ResourceManager;
import com.application.areca.launcher.gui.common.AbstractWindow;
import com.application.areca.launcher.gui.common.SavePanel;
import com.myJava.file.FileSystemManager;

/**
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
public class ImportGroupWindow 
extends AbstractWindow {
    private static final ResourceManager RM = ResourceManager.instance();
    
    private Text location;
    private Button saveButton;

    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 20;
        composite.setLayout(layout);
        
        Label lblAdvise = new Label(composite, SWT.NONE);
        lblAdvise.setText(RM.getLabel("importgrp.intro.label"));
        GridData mainData1 = new GridData();
        mainData1.grabExcessHorizontalSpace = true;
        mainData1.horizontalAlignment = SWT.FILL;
        lblAdvise.setLayoutData(mainData1);
        
        Group grpLocation = new Group(composite, SWT.NONE);
        grpLocation.setText(RM.getLabel("importgrp.location.label"));
        GridLayout grpLayout = new GridLayout(2, false);
        grpLocation.setLayout(grpLayout);
        grpLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        location = new Text(grpLocation, SWT.BORDER);
        GridData mainData2 = new GridData();
        mainData2.grabExcessHorizontalSpace = true;
        mainData2.horizontalAlignment = SWT.FILL;
        location.setLayoutData(mainData2);
        monitorControl(location);
        
        Button btnBrowse = new Button(grpLocation, SWT.PUSH);
        btnBrowse.setText(RM.getLabel("common.browseaction.label"));
        btnBrowse.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                File f= new File(location.getText());
                String path;
                 if (FileSystemManager.exists(f)) {
                     if (FileSystemManager.isFile(f)) {
                         path = Application.getInstance().showFileDialog(FileSystemManager.getParent(f), ImportGroupWindow.this);
                     } else {
                         path = Application.getInstance().showFileDialog(FileSystemManager.getAbsolutePath(f), ImportGroupWindow.this);
                     }
                 } else {
                     path = Application.getInstance().showFileDialog(ImportGroupWindow.this);
                 }

                if (path != null) {
                    location.setText(path);
                }
            }
        });
        GridData mainData3 = new GridData();
        mainData3.horizontalAlignment = SWT.FILL;
        btnBrowse.setLayoutData(mainData3);

        SavePanel pnlSave = new SavePanel(RM.getLabel("common.import.label"), this);
        GridData saveData = new GridData();
        saveData.grabExcessHorizontalSpace = true;
        saveData.horizontalAlignment = SWT.FILL;
        pnlSave.buildComposite(composite).setLayoutData(saveData);        
        saveButton = pnlSave.getBtnSave();
        
        composite.pack();
        return composite;
    }
    
    public String getTitle() {
        return RM.getLabel("importgrp.dialog.title");
    }

    protected boolean checkBusinessRules() {
        boolean ok = true;
        
        this.resetErrorState(location);     
        if (this.location.getText() == null || this.location.getText().length() == 0) {
            ok = false;
        } else {
            File f = new File(location.getText());
            ok = 
                FileSystemManager.exists(f) 
                && FileSystemManager.isFile(f) 
                && FileSystemManager.getName(f).toLowerCase().endsWith(".xml");
        }
        
        if (! ok) {
            this.setInError(location);
        }
        
        return ok;
    }

    protected void saveChanges() {               
        application.importGroup(new File(location.getText()));
        this.hasBeenUpdated = false;
        this.close();
    }

    protected void updateState(boolean rulesSatisfied) {
        this.saveButton.setEnabled(rulesSatisfied);
    }
}