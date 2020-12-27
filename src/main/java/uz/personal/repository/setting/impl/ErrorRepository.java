package uz.personal.repository.setting.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.personal.criteria.GenericCriteria;
import uz.personal.domain.Auditable;
import uz.personal.domain.setting._ErrorMessage;
import uz.personal.enums.ErrorCodes;
import uz.personal.repository.GenericDao;
import uz.personal.repository.setting.IErrorRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class ErrorRepository extends GenericDao<Auditable, GenericCriteria> implements IErrorRepository {

    /**
     * Common logger for use in subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public String getErrorMessage(ErrorCodes errorCode, String params) {
        String lang = userSession.getLanguage() == null ? "ru" : userSession.getLanguage();
        try {
            String error = (String) entityManager.createQuery("SELECT emt.name FROM _ErrorMessage t" +
                    " LEFT JOIN _ErrorMessageTranslation emt on t.id = emt.messageId " +
                    " WHERE t.errorCode = '" + errorCode + "' and emt.language.code = '" + lang + "' ORDER BY t.id DESC ").getSingleResult();
            return String.format(error, (Object[]) params.split("#"));
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(String.format("Error Message with errorCode '%s' not found", errorCode));
        }
    }

    @Override
    public _ErrorMessage getByErrorCode(String errorCode) {
        try {
            return (_ErrorMessage) entityManager.createQuery("SELECT t FROM _ErrorMessage t WHERE t.errorCode = '" + errorCode + "' ORDER BY t.id DESC ").getSingleResult();
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(String.format("Error Message with errorCode '%s' not found", errorCode));
        }
    }
}
