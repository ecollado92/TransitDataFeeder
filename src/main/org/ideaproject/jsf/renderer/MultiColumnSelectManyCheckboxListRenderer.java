/**
 *  Copyright 2010 SingleMind Consulting, Inc. (http://singlemindconsulting.com)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 */
package org.ideaproject.jsf.renderer;

import java.io.IOException;
import java.util.List;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.MenuRenderer;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;

/**
 * Extension of MenuRender which will allow for an amalgamation of page and line directions
 * to provide for a multi-column (and multi-line) layout.
 * 
 * @author dirk
 * @see com.sun.faces.renderkit.html_basic.MenuRenderer
 */
public class MultiColumnSelectManyCheckboxListRenderer extends MenuRenderer {

	/*
	 * (non-Javadoc)
	 * @see com.sun.faces.renderkit.html_basic.MenuRenderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		
		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}

		ResponseWriter writer = context.getResponseWriter();
		assert(writer != null);

		Converter converter = null;
        if(component instanceof ValueHolder) {
            converter = ((ValueHolder)component).getConverter();
        }

		renderBeginText(writer, context, component, getStyleContainerForComponent(component), true);

		List<SelectItem> items = RenderKitUtils.getSelectItems(context, component);

		String parentId = component.getClientId(context);

		if (!items.isEmpty()) {
			StyleContainer childStyleContainer = getChildStyleContainer(component);
            Object currentSelections = getCurrentSelectedValues(component);
            Object[] submittedValues = getSubmittedSelectedValues(component);
            int itemIndex = -1;
            for (SelectItem item : items) {
            	itemIndex++;
                if (item instanceof SelectItemGroup) {
                	renderBeginText(writer, context, component, null, false);
                	String groupId = parentId + NamingContainer.SEPARATOR_CHAR;
                	writer.writeAttribute("id", groupId, "id");
                	writer.writeAttribute("class", "itemGroupDiv", "class");
                	// write out the label for the group.
                	if (item.getLabel() != null) {
                		writer.writeText(item.getLabel(), component, "label");
                	}
                    // render options of this group.
                    SelectItem[] itemsArray =
                          ((SelectItemGroup) item).getSelectItems();
                    for (int i = 0; i < itemsArray.length; ++i) {
                    	renderOption(writer, context, component, converter, itemsArray[i], 
                    			currentSelections, submittedValues, childStyleContainer, groupId, i);
                    }
                    renderEndText(writer, context, component);
                } else {
                	renderOption(writer, context, component, converter, item,
                			currentSelections, submittedValues, childStyleContainer, parentId, itemIndex);
                }
            }
		}
		renderEndText(writer, context, component);
	}

	/**
	 * Renders each individual "option"/checkbox.
	 * 
	 * @param writer
	 * @param context
	 * @param component
	 * @param converter
	 * @param curItem
	 * @param currentSelections
	 * @param submittedValues
	 * @param styleContainer
	 * @param parentId
	 * @param itemNumber
	 * @throws IOException
	 */
	private void renderOption(ResponseWriter writer, FacesContext context, UIComponent component,
								Converter converter, SelectItem curItem, Object currentSelections,
								Object[] submittedValues, StyleContainer styleContainer, 
								String parentId, int itemNumber)
			throws IOException {

		renderBeginText(writer, context, component, styleContainer, false);

        // disable the check box if the attribute is set.
        boolean componentDisabled = Util.componentIsDisabled(component);

        String labelClass;
        if (componentDisabled || curItem.isDisabled()) {
            labelClass = (String) component.
                  getAttributes().get("disabledClass");
        } else {
            labelClass = (String) component.
                  getAttributes().get("enabledClass");
        }

		writer.startElement("input", component);
		writer.writeAttribute("name", component.getClientId(context), "clientId");

		String idString = parentId + NamingContainer.SEPARATOR_CHAR + Integer.toString(itemNumber);
		writer.writeAttribute("id", idString, "id");

        String valueString = getFormattedValue(context, component,
                curItem.getValue(), converter);
        writer.writeAttribute("value", valueString, "value");
        writer.writeAttribute("type", "checkbox", null);

        Object valuesArray;
        Object itemValue;
        if (submittedValues != null) {
            valuesArray = submittedValues;
            itemValue = valueString;
        } else {
            valuesArray = currentSelections;
            itemValue = curItem.getValue();
        }

        RequestStateManager.set(context,
                RequestStateManager.TARGET_COMPONENT_ATTRIBUTE_NAME,
                component);

        if (isSelected(context, component, itemValue, valuesArray, converter)) {
            writer.writeAttribute(getSelectedTextString(), Boolean.TRUE, null);
        }

        // Don't render the disabled attribute twice if the 'parent'
        // component is already marked disabled.
        if (!Util.componentIsDisabled(component)) {
            if (curItem.isDisabled()) {
                writer.writeAttribute("disabled", true, "disabled");
            }
        }

        // Apply HTML 4.x attributes specified on UISelectMany component to all
        // items in the list except styleClass and style which are rendered as
        // attributes of outer most table.
        RenderKitUtils.renderPassThruAttributes(writer,
                                                component,
                                                new String[] { "border", "style" });
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);

        writer.endElement("input");
        writer.startElement("label", component);
		writer.writeAttribute("for", idString, "for");
		// if enabledClass or disabledClass attributes are specified, apply
		// it on the label.
		if (labelClass != null) {
			writer.writeAttribute("class", labelClass, "labelClass");
		}
		String itemLabel = curItem.getLabel();
		if (itemLabel != null) {
			writer.writeText(" ", component, null);
			if (!curItem.isEscape()) {
				// It seems the ResponseWriter API should
				// have a writeText() with a boolean property
				// to determine if it content written should
				// be escaped or not.
				writer.write(itemLabel);
			}
			else {
				writer.writeText(itemLabel, component, "label");
			}
		}
		writer.endElement("label");

		renderEndText(writer, context, component);
	}

	// ------------------------------------------------- Package Private Methods


	/**
	 * @return the html string to return for an option/checkbox which is selected by default.
	 */
	String getSelectedTextString() {

		return "checked";

	}

	/**
	 * Renders the begin text for the checkbox group.
	 * 
	 * @param writer
	 * @param context
	 * @param component
	 * @param styleContainer
	 * @param parentComponent
	 * @throws IOException
	 */
	private void renderBeginText(ResponseWriter writer, FacesContext context,
			UIComponent component, StyleContainer styleContainer, 
			boolean parentComponent) throws IOException {

        writer.startElement("div", component);

        // render style and styleclass attribute on the outer table instead of 
        // rendering it as pass through attribute on every option in the list.
        if (parentComponent) {
            // render "id" only for outerTable.
            if (shouldWriteIdAttribute(component)) {
                writeIdAttributeIfNecessary(context, writer, component);
            }
            String styleClass = (String) component.getAttributes().get(
                  "styleClass");
            String style = (String) component.getAttributes().get("style");
        }
        if (styleContainer != null && styleContainer.getStyleClass() != null) {
            writer.writeAttribute("class", styleContainer.getStyleClass(), "class");
        }
        if (styleContainer != null && styleContainer.getStyle() != null) {
            writer.writeAttribute("style", styleContainer.getStyle(), "style");
        }
        writer.writeText("\n", component, null);
	}

	/**
	 * Renders the end text for the checkbox group.
	 * 
	 * @param writer
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	private void renderEndText(ResponseWriter writer, FacesContext context, UIComponent component)	throws IOException {
		writer.endElement("div");
        writer.writeText("\n", component, null);
	}

	/**
	 * @param component the child component (checkbox)
	 * @return the CSS style associated with the children of the checkbox group (e.g., the checkboxes themselves)
	 */
	private StyleContainer getChildStyleContainer(UIComponent component) {
		StyleContainer result = new StyleContainer(null, null);
		UIComponent firstChild = this.getChildren(component).next();
		if (firstChild != null) {
			result = getStyleContainerForComponent(firstChild);
		}
		return result;
	}

	/**
	 * @param component the component (checkbox group)
	 * @return the CSS style associated with the checkbox group.
	 */
	private StyleContainer getStyleContainerForComponent(UIComponent component) {
        String styleClass = (String) component.getAttributes().get(
                "styleClass");
        String style = (String) component.getAttributes().get("style");
		return new StyleContainer(styleClass, style);
	}

	/**
	 * Bean for encompassing the CSS style (html "class" and "style") associated with an HTML element.
	 * @author dirk
	 */
	private static class StyleContainer {
		/**
		 * The "class" element.
		 */
		private final String styleClass;
		
		/**
		 * The "style" element.
		 */
		private final String style;

		StyleContainer(String styleClass, String style) {
			this.styleClass = styleClass;
			this.style = style;
		}

		/**
		 * @return the styleClass
		 */
		public String getStyleClass() {
			return styleClass;
		}

		/**
		 * @return the style
		 */
		public String getStyle() {
			return style;
		}
	}
}
