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
public class ConvertCalendarTime implements Converter {

    private static final String[] monthsNames = new String[] {"01","02","03","04","05","06","07","08","09","10","11","12"};
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
            result += "-"; //$NON-NLS-1$
            result += monthsNames[day.get(Calendar.MONTH)];
            result += "-"; //$NON-NLS-1$
            result += String.valueOf(day.get(Calendar.YEAR));
            result += " "; //$NON-NLS-1$
            result += String.valueOf(day.get(Calendar.HOUR_OF_DAY));            
            result += ":"; //$NON-NLS-1$
            result += String.valueOf(day.get(Calendar.MINUTE));
            
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
