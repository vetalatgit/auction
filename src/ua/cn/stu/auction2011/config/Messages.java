package ua.cn.stu.auction2011.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Messages {
		
	private static final String BUNDLE_NAME = "resources"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE;
	
	static {
		if (FacesContext.getCurrentInstance() != null) {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,
					FacesContext.getCurrentInstance().getApplication()
							.getDefaultLocale());
		} else {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		}

	}

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

