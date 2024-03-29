/**
 *
 * Copyright 2003-2007 Jive Software.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.smackx.xdata.provider;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.provider.ExtensionElementProvider;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import org.jivesoftware.smack.roster.provider.RosterPacketProvider;
import org.jivesoftware.smackx.xdata.FormField;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import org.jivesoftware.smackx.xdatalayout.packet.DataLayout;
import org.jivesoftware.smackx.xdatalayout.provider.DataLayoutProvider;
import org.jivesoftware.smackx.xdatavalidation.packet.ValidateElement;
import org.jivesoftware.smackx.xdatavalidation.provider.DataValidationProvider;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DataFormProvider parses DataForm packets.
 * 
 * @author Gaston Dombiak
 */
public class DataFormProvider extends ExtensionElementProvider<DataForm> {

    @Override
    public DataForm parse(XmlPullParser parser, int initialDepth) throws XmlPullParserException, IOException,
                    SmackException {
        DataForm.Type dataFormType = DataForm.Type.fromString(parser.getAttributeValue("", "type"));
        DataForm dataForm = new DataForm(dataFormType);
        outerloop: while (true) {
            int eventType = parser.next();
            switch (eventType) {
            case XmlPullParser.START_TAG:
                String name = parser.getName();
                String namespace = parser.getNamespace();
                if(name.equals("instructions")){
                	dataForm.addInstruction(parser.nextText());
                }else if(name.equals("title")){
                	dataForm.setTitle(parser.nextText());
                }else if(name.equals("field")){
                	dataForm.addField(parseField(parser));
                }else if(name.equals("item")){
                	dataForm.addItem(parseItem(parser));
                }else if(name.equals("reported")){
                	dataForm.setReportedData(parseReported(parser));
                }else if(name.equals(RosterPacket.ELEMENT)){
                	if (namespace.equals(RosterPacket.NAMESPACE)) {
                        dataForm.addExtensionElement(RosterPacketProvider.INSTANCE.parse(parser));
                    }
                }else if(name.equals(DataLayout.ELEMENT)){
                	if (namespace.equals(DataLayout.NAMESPACE)) {
                        dataForm.addExtensionElement(DataLayoutProvider.parse(parser));
                    }
                }
                /*switch (name) {
                case "instructions":
                    dataForm.addInstruction(parser.nextText());
                    break;
                case "title":
                    dataForm.setTitle(parser.nextText());
                    break;
                case "field":
                    dataForm.addField(parseField(parser));
                    break;
                case "item":
                    dataForm.addItem(parseItem(parser));
                    break;
                case "reported":
                    dataForm.setReportedData(parseReported(parser));
                    break;
                // See XEP-133 Example 32 for a corner case where the data form contains this extension.
                case RosterPacket.ELEMENT:
                    if (namespace.equals(RosterPacket.NAMESPACE)) {
                        dataForm.addExtensionElement(RosterPacketProvider.INSTANCE.parse(parser));
                    }
                    break;
                // See XEP-141 Data Forms Layout
                case DataLayout.ELEMENT:
                    if (namespace.equals(DataLayout.NAMESPACE)) {
                        dataForm.addExtensionElement(DataLayoutProvider.parse(parser));
                    }
                    break;
                }*/
                break;
            case XmlPullParser.END_TAG:
                if (parser.getDepth() == initialDepth) {
                    break outerloop;
                }
                break;
            }
        }
        return dataForm;
    }

    private FormField parseField(XmlPullParser parser) throws XmlPullParserException, IOException {
        final int initialDepth = parser.getDepth();
        final String var = parser.getAttributeValue("", "var");
        final FormField.Type type = FormField.Type.fromString(parser.getAttributeValue("", "type"));

        final FormField formField;
        if (type == FormField.Type.fixed) {
            formField = new FormField();
        } else {
            formField = new FormField(var);
            formField.setType(type);
        }
        formField.setLabel(parser.getAttributeValue("", "label"));

        outerloop: while (true) {
            int eventType = parser.next();
            switch (eventType) {
            case XmlPullParser.START_TAG:
                String name = parser.getName();
                String namespace = parser.getNamespace();
                if(name.equals( "desc")){
                	formField.setDescription(parser.nextText());
                }else if(name.equals("value")){
                	formField.addValue(parser.nextText());
                }else if(name.equals("required")){
                	formField.setRequired(true);
                }else if(name.equals("option")){
                	formField.addOption(parseOption(parser));
                }else if(name.equals(ValidateElement.ELEMENT)){
                	if (namespace.equals(ValidateElement.NAMESPACE)) {
                        formField.setValidateElement(DataValidationProvider.parse(parser));
                    }
                }
                /*switch (name) {
                case "desc":
                    formField.setDescription(parser.nextText());
                    break;
                case "value":
                    formField.addValue(parser.nextText());
                    break;
                case "required":
                    formField.setRequired(true);
                    break;
                case "option":
                    formField.addOption(parseOption(parser));
                    break;
                // See XEP-122 Data Forms Validation
                case ValidateElement.ELEMENT:
                    if (namespace.equals(ValidateElement.NAMESPACE)) {
                        formField.setValidateElement(DataValidationProvider.parse(parser));
                    }
                    break;
                }*/
                break;
            case XmlPullParser.END_TAG:
                if (parser.getDepth() == initialDepth) {
                    break outerloop;
                }
                break;
            }
        }
        return formField;
    }

    private DataForm.Item parseItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        final int initialDepth = parser.getDepth();
        List<FormField> fields = new ArrayList<FormField>();
        outerloop: while (true) {
            int eventType = parser.next();
            switch (eventType) {
            case XmlPullParser.START_TAG:
                String name = parser.getName();
                if(name.equals("field")){
                	fields.add(parseField(parser));
                }
                /*switch (name) {
                case "field":
                    fields.add(parseField(parser));
                    break;
                }*/
                break;
            case XmlPullParser.END_TAG:
                if (parser.getDepth() == initialDepth) {
                    break outerloop;
                }
                break;
            }
        }
        return new DataForm.Item(fields);
    }

    private DataForm.ReportedData parseReported(XmlPullParser parser) throws XmlPullParserException, IOException {
        final int initialDepth = parser.getDepth();
        List<FormField> fields = new ArrayList<FormField>();
        outerloop: while (true) {
            int eventType = parser.next();
            switch (eventType) {
            case XmlPullParser.START_TAG:
                String name = parser.getName();
                if(name.equals("field")){
                	fields.add(parseField(parser));
                }
               /* switch (name) {
                case "field":
                    fields.add(parseField(parser));
                    break;
                }*/
                break;
            case XmlPullParser.END_TAG:
                if (parser.getDepth() == initialDepth) {
                    break outerloop;
                }
                break;
            }
        }
        return new DataForm.ReportedData(fields);
    }

    private FormField.Option parseOption(XmlPullParser parser) throws XmlPullParserException, IOException {
        final int initialDepth = parser.getDepth();
        FormField.Option option = null;
        String label = parser.getAttributeValue("", "label");
        outerloop: while (true) {
            int eventType = parser.next();
            switch (eventType) {
            case XmlPullParser.START_TAG:
                String name = parser.getName();
                if(name.equals("value")){
                	option = new FormField.Option(label, parser.nextText());
                }
                /*switch (name) {
                case "value":
                    option = new FormField.Option(label, parser.nextText());
                    break;
                }*/
                break;
            case XmlPullParser.END_TAG:
                if (parser.getDepth() == initialDepth) {
                    break outerloop;
                }
                break;
            }
        }
        return option;
    }
}
