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

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author GreenElm
 * @web https://github.com/GreenElm/expInlineImage
 */

public class CustomHtmlEditorKitHelper extends HTMLEditorKit {
    private static HTMLEditorKit.HTMLFactory factory = null;    

    @Override
    public ViewFactory getViewFactory() {
        if (factory == null) {
            factory = new HTMLEditorKit.HTMLFactory() {

                @Override
                public View create(Element elem) {
                    AttributeSet attrs = elem.getAttributes();
                    Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
                    Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
                    if (o instanceof HTML.Tag) {
                        HTML.Tag kind = (HTML.Tag) o;
                        if (kind == HTML.Tag.IMG) {
                            // HERE is the call to the special class...
                            return new CustomImageViewHelper(elem);
                        } // End if(kind == IMG)...
                    } // End if(instance of Tag)...
                    return super.create(elem);
                } // End create()...
                
            }; // End new HTMLFactory()...
        } // End if(factory == null)...
        return factory;
    } // End getViewFactory()...
 
} // End RITB64_HTMLEditorKit()...

