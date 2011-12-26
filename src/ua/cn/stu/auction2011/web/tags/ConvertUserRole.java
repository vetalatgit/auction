package ua.cn.stu.auction2011.web.tags;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.StringUtils;

import ua.cn.stu.auction2011.domain.Role;

/**
 * Convertor that will convert values of {@link UserRole}
 */
public class ConvertUserRole implements Converter {
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		if (StringUtils.isNotBlank(arg2)) {
			return Role.valueOf(arg2);
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if (arg2 != null) {
			if (arg2 instanceof Role)
				return ((Role)arg2).name();
			else
				return arg2.toString();
		} else
			return ""; //$NON-NLS-1$
	}
}
