package jordan.university.gradproject2.usecase.base;

public interface BaseUseCase<REQ, RES> {
    RES invoke (REQ request);
}