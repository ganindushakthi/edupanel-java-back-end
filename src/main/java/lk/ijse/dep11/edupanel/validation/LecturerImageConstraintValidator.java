package lk.ijse.dep11.edupanel.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class LecturerImageConstraintValidator implements ConstraintValidator<LecturerImage, MultipartFile> {
    private long maximumFileSize;
    @Override
    public void initialize(LecturerImage constraintAnnotation) {
        maximumFileSize = constraintAnnotation.maximumFileSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile==null) return true;
        if(multipartFile.getSize() > maximumFileSize) return false;
        if(multipartFile.getContentType() == null) return false;
        if(!multipartFile.getContentType().startsWith("image/")) return false;
        return true;
    }
}
