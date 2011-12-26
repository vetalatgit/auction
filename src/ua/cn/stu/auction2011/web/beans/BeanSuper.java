package ua.cn.stu.auction2011.web.beans;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ua.cn.stu.auction2011.config.Messages;

/**
 * This is just a super class for all beans, that has handful methods for adding messages to JSF 
 * 
 *
 */
public class BeanSuper implements Serializable {
	
	/** serial id for serialization */
    private static final long serialVersionUID = 1L;
	
    transient protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * Adding Global FATAL message
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addGlobalFatalMessage(String message, Throwable t) {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// Logging the service error
		if (t != null) {
			log.fatal("Service fatal", t); //$NON-NLS-1$
		} else
			log.fatal("Service fatal :" + message); //$NON-NLS-1$

		
		FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_FATAL, Messages.getString("BeanSuper.ErrorFatal"), message); //$NON-NLS-1$
		facesContext.addMessage(null, facesMessage);
	}
	
	/**
	 * Adding Global ERROR message
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addGlobalErrorMessage(String message, Throwable t) {
		// Logging the service error
		if (t != null)
			log.error("Service error", t); //$NON-NLS-1$
		else
			log.error("Service error :" + message); //$NON-NLS-1$

		addErrorMessage(null, message, t);
	}
	
	/**
	 * Adding ERROR message for specified id
	 * @param id Id to add message for
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addErrorMessage(String id, String message, Throwable t) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;
		
		if (t != null) {
			facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_ERROR, message, t.getLocalizedMessage());
		}else {
			facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, ""); //$NON-NLS-1$
		}
		facesContext.addMessage(id, facesMessage);
	}
	
	/**
	 * Adding Global WARN message
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addGlobalWARNMessage(String message, Throwable t) {
		
		// Logging the service warn
		if (t != null) {
			log.warn("Service warn", t); //$NON-NLS-1$
		}else {
			log.warn("Service warn :" + message); //$NON-NLS-1$
		}
		addWARNMessage(null, message, t);		
	}
	
	/**
	 * Adding WARN message for specified id
	 * @param id Id to add message for
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addWARNMessage(String id, String message, Throwable t) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;

		if (t != null) {
			facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_WARN, message, t.getLocalizedMessage());
			
		}else {
			facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_WARN, message, ""); //$NON-NLS-1$
		}
	
		facesContext.addMessage(id, facesMessage);
	}
	
	/**
	 * Adding Global INFO message
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addGlobalINFOMessage(String message, Throwable t) {
		addINFOMessage(null, message, t);
	}
	
	/**
	 * Adding INFO message for specified id
	 * @param id Id to add message for
	 * @param message Message to add
	 * @param t {@link Throwable} object or null
	 */
	protected void addINFOMessage(String id, String message, Throwable t) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;
		
		if (t != null)
			facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_INFO, message, t.getLocalizedMessage());
		else
			facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_INFO, message, ""); //$NON-NLS-1$
		
		facesContext.addMessage(id, facesMessage);
	}
	
	/**
	 * @param <T> Type of bean to return
	 * @param beanName bean name to look for
	 * @return Bean from the external context session or <code>null</code> if not found
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getBeanFromSession(String beanName) {
		return (T) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName);
	}
	
	/**
	 * Getting parameter from request
	 * @param paramName Name of the parameter
	 * @return Found parameter or <code>null</code>
	 */
	protected String getParameterFromRequest(String paramName) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> param = facesContext.getExternalContext()
				.getRequestParameterMap();

		return param.get(paramName);
	}
	
	/**
	 * Getting parameter from request
	 * @param paramName Name of the parameter
	 * @return Found parameter or <code>null</code>
	 */
	protected Long getParameterFromRequestAsLong(String paramName) {
		String sParamValue = getParameterFromRequest(paramName);
		Long paramLong = null;
		try {
			paramLong = Long.valueOf(sParamValue);
		} catch (NumberFormatException e) {
			if (log.isTraceEnabled()) {
				log.trace("Value not found in request for " + paramName); //$NON-NLS-1$
			}
		}
		return paramLong;
	}
}
