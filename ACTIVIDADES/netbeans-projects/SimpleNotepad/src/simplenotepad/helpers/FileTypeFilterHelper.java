/*
 * Copyright (C) 2015 StaticKidz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplenotepad.helpers;

import java.io.File;

/**
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class FileTypeFilterHelper extends javax.swing.filechooser.FileFilter {
        
    private final String extension;
    private final String description;

    public FileTypeFilterHelper(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file){
        if (file.isDirectory()) {
            return true;
        } return file.getName().endsWith(extension);
    }

    @Override
    public String getDescription(){
        return description + String.format(" (*%s)", extension);
    }
    
    public static String getExtension(String uri) {
        if (uri == null) return null;
        int dot = uri.lastIndexOf(".");
        int end = uri.lastIndexOf(")");
        if (dot >= 0) {
            return uri.substring(dot, end);
        } else {
            return "";
        }
    }
    
}
