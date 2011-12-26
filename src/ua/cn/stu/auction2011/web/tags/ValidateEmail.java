package ua.cn.stu.auction2011.web.tags;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.EmailValidator;

import ua.cn.stu.auction2011.config.Messages;

/**
 * Validating email for correctness
 * 
 */
public class ValidateEmail implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.validator.Validator#validate(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {

		if ((arg2 == null) ||
			(!EmailValidator.getInstance().isValid(arg2.toString()))) {
			FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.getString("ValidateEmail.ErrorEmailValid"), arg2 + Messages.getString("ValidateEmail.ErrorNotValidAddess")); //$NON-NLS-1$ //$NON-NLS-2$
			throw new ValidatorException(mess);
		}
		
	}
}
