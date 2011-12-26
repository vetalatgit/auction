package ua.cn.stu.auction2011.web.tags;

import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import ua.cn.stu.auction2011.config.Messages;

/**
 * Convertor that will convert calendar value
 */
public class ConvertCalendar implements Converter {
	
	  /**
     * Localized months names in second form. For russian language second form
     * is called Roditelniy Padezh. (In name of constant SF stands for Second
     * Form.)
     */
    private static final String[] monthsNamesSF = new String[] { Messages.getString("ConvertCalendar.Jan"), //$NON-NLS-1$
                    Messages.getString("ConvertCalendar.Feb"), Messages.getString("ConvertCalendar.Mar"), Messages.getString("ConvertCalendar.Apr"), Messages.getString("ConvertCalendar.May"), Messages.getString("ConvertCalendar.Jun"), Messages.getString("ConvertCalendar.Jul"), Messages.getString("ConvertCalendar.Aug"),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
                    Messages.getString("ConvertCalendar.Sep"), Messages.getString("ConvertCalendar.Oct"), Messages.getString("ConvertCalendar.Nov"), Messages.getString("ConvertCalendar.Dec") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    /**
     * Formatting string representation of the day withou year
     *
     * @param day
     *            Day to format (<code>null</code> for current day)
     * @return Formatted string in the way dd mmmm yyyy
     */
    public static String formatDate(Calendar day) {
            if (day == null)
                    day = Calendar.getInstance();
       
            String result = ""; //$NON-NLS-1$
       
            result += String.valueOf(day.get(Calendar.DAY_OF_MONTH));
            result += " "; //$NON-NLS-1$
            result += monthsNamesSF[day.get(Calendar.MONTH)].toLowerCase();
            result += " "; //$NON-NLS-1$
            result += String.valueOf(day.get(Calendar.YEAR));
            return result;
    }  

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		return Calendar.getInstance();
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if (arg2 != null) {
			if (arg2 instanceof Calendar) {
				return formatDate((Calendar) arg2);
			} else {
				return arg2.toString();
			}
		} else {
			return ""; //$NON-NLS-1$
		}
	}
}
