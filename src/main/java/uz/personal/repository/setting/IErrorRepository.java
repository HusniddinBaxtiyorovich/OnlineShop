package uz.personal.repository.setting;

import uz.personal.criteria.GenericCriteria;
import uz.personal.domain.Auditable;
import uz.personal.domain.setting._ErrorMessage;
import uz.personal.enums.ErrorCodes;
import uz.personal.repository.IGenericCrudRepository;

public interface IErrorRepository extends IGenericCrudRepository<Auditable, GenericCriteria> {

    String getErrorMessage(ErrorCodes errorCode, String params);

    _ErrorMessage getByErrorCode(String errorCode);
}
