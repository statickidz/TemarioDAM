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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class HtmlLineBreakHelper extends DocumentFilter {
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offs, String str, AttributeSet a)
        throws BadLocationException
    {
        super.insertString(fb, offs, str.replaceAll("\n", "\n\r"), a);
    }

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
        throws BadLocationException
    {
        super.replace(fb, offs, length, str.replaceAll("\n", "\n\r"), a);
    }
}
