package Pet.Society.models.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalControllerException {

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setInstance(URI.create(request.getRequestURL().toString()));
        problem.setProperty("timestamp", OffsetDateTime.now());
        return problem;
    }

    @ExceptionHandler(UserExistsException.class)
    public ProblemDetail HandlerUserExistsException(UserExistsException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.CONFLICT, "User Exists", "The user already exists", request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail HandlerUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "User Not Found", "The user does not exist", request);
    }

    @ExceptionHandler(UnsubscribedUserException.class)
    public ProblemDetail HandlerUnsubscribedUserException(UnsubscribedUserException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.CONFLICT, "User Unsubscribed", "The user is unsubscribed", request);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ProblemDetail HandlerPetNotFoundException(PetNotFoundException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Pet Not Found", "The pet does not exist", request);
    }

    @ExceptionHandler(UserAttributeException.class)
    public ProblemDetail handlerUserAttributeException(UserAttributeException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Invalid User Attributes", "The data entered is not correct: " + ex.getMessage(), request);
    }

    @ExceptionHandler(LoginErrorException.class)
    public ProblemDetail handlerLoginError(LoginErrorException ex, HttpServletRequest request) {
        ProblemDetail problem = createProblemDetail(HttpStatus.UNAUTHORIZED, "Login Error", ex.getMessage(), request);
        problem.setProperty("error", "An error occurred during login");
        return problem;
    }

    @ExceptionHandler(DiagnosesNotFoundException.class)
    public ProblemDetail handlerDiagnosesNotFoundException(DiagnosesNotFoundException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Diagnoses Not Found", "The diagnoses does not exist: " + ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicatedAppointmentException.class)
    public ProblemDetail handlerDuplicatedAppointmentException(DuplicatedAppointmentException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.CONFLICT, "Duplicated Appointment", ex.getMessage(), request);
    }

    @ExceptionHandler(UnavailableAppointmentException.class)
    public ProblemDetail handlerUnavailableAppointmentException(UnavailableAppointmentException ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.CONFLICT, "Unavailable Appointment", ex.getMessage(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleUniqueConstraintViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "That data already exists";
        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("duplicate")) {
            if (ex.getMessage().toLowerCase().contains("email")) {
                message = "The email already exists";
            } else if (ex.getMessage().toLowerCase().contains("dni")) {
                message = "The DNI already exists";
            }
        }
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Data Integrity Violation", message, request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail HandlerException(Exception ex, HttpServletRequest request) {
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "There was an error processing the request. " + ex.getMessage(), request);
    }
}
