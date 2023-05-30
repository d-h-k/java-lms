package nextstep.courses.domain;

import nextstep.courses.exception.ExceededStudentCount;
import nextstep.courses.exception.OutOfRegistrationPeriod;
import nextstep.image.domain.Image;
import nextstep.users.domain.UserCode;
import nextstep.utils.CommunicationTerm;

import javax.validation.constraints.NotNull;
import java.util.Date;

@CommunicationTerm("강의")
public class Session {
    private SessionId sessionId;
    private Image coverImage;
    @CommunicationTerm("기수")
    private Long term;
    @NotNull
    private Long price;
    @NotNull
    private SessionStatus status;
    @NotNull
    private Long maxStudentCount;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

    public Session(SessionId sessionId, Image coverImage, Long term, Long price, SessionStatus status, Long maxStudentCount, Date startDate, Date endDate) {
        this.sessionId = sessionId;
        this.coverImage = coverImage;
        this.term = term;
        this.price = price;
        this.status = status;
        this.maxStudentCount = maxStudentCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Session of(Long price, Long term, Long maxStudentCount) {
        return new Session(
                null,
                null,
                term,
                price,
                SessionStatus.PREPARING,
                maxStudentCount,
                new Date(),
                new Date(System.currentTimeMillis() + 1000000)
        );
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String coverImageUrl() {
        return this.coverImage.getImageUrl();
    }

    public void toFreeSession() {
        this.price = 0L;
    }

    public boolean isFreeSession() {
        return this.price == 0L;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void toPreparingState() {
        this.status = SessionStatus.PREPARING;
    }

    public void toRecruitingState() {
        this.status = SessionStatus.RECRUITING;
    }

    public void toProgressState() {
        this.status = SessionStatus.IN_PROGRESS;
    }
    public void toCloseState() {
        this.status = SessionStatus.CLOSED;
    }

    public void registerCoverImage(Image image) {
        this.coverImage = image;
    }

    public Enroll register(UserCode userCode, long alreadyEnrolledCount) {
        validateState();
        validateStudentCount(alreadyEnrolledCount);
        return new Enroll(null, this.sessionId, userCode); //Enroll.of(this.sessionId.value(), userCode.value());
    }

    private void validateStudentCount(long count) {
        if (maxStudentCount < count + 1) {
            throw new ExceededStudentCount();
        }
    }

    private void validateState() {
        if (!this.status.isRecruitStatus()) {
            throw new OutOfRegistrationPeriod();
        }
    }

    public void adjustStudentCount(Long maxStudentCount) {
        this.maxStudentCount = maxStudentCount;
    }

    public long getTerm() {
        return this.term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public boolean isEnrolledSession(Enroll enroll) {
        return enroll.isEnrolledSession(this.sessionId);
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }
}
