/*
 * Copyright 2005-2008 Noelios Consulting.
 * 
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL HEADER in each file and
 * include the License file at http://www.opensource.org/licenses/cddl1.txt If
 * applicable, add the following below this CDDL HEADER, with the fields
 * enclosed by brackets "[]" replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */

package org.restlet.ext.wadl;

import java.util.ArrayList;
import java.util.List;

import org.restlet.util.XmlWriter;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Defines a potential value for a parent parameter description.
 * 
 * @author Jerome Louvel
 */
public class OptionInfo {

    /** Doc elements used to document that element. */
    private List<DocumentationInfo> documentations;

    /** Value of this option element. */
    private String value;

    /**
     * Returns the list of documentation elements.
     * 
     * @return The list of documentation elements.
     */
    public List<DocumentationInfo> getDocumentations() {
        // Lazy initialization with double-check.
        List<DocumentationInfo> d = this.documentations;
        if (d == null) {
            synchronized (this) {
                d = this.documentations;
                if (d == null)
                    this.documentations = d = new ArrayList<DocumentationInfo>();
            }
        }
        return d;
    }

    /**
     * Returns the value of this option element.
     * 
     * @return The value of this option element.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the list of documentation elements.
     * 
     * @param doc
     *                The list of documentation elements.
     */
    public void setDocumentations(List<DocumentationInfo> doc) {
        this.documentations = doc;
    }

    /**
     * Sets the value of this option element.
     * 
     * @param value
     *                The value of this option element.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Writes the current object as an XML element using the given SAX writer.
     * 
     * @param writer
     *                The SAX writer.
     * @throws SAXException
     */
    public void writeElement(XmlWriter writer) throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (getValue() != null && !getValue().equals("")) {
            attributes.addAttribute("", "id", null, "xs:string", getValue());
        }

        if (getDocumentations().isEmpty()) {
            writer.emptyElement("", "option", null, attributes);
        } else {
            writer.startElement("", "option", null, attributes);

            for (DocumentationInfo documentationInfo : getDocumentations()) {
                documentationInfo.writeElement(writer);
            }

            writer.endElement("", "option");
        }
    }

}
