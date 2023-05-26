package nextstep.qna.domain;

import nextstep.qna.exception.QuestionDeleteAnswerExistedException;
import nextstep.qna.exception.QuestionDeleteUnauthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Question {
    private final List<Answer> answers = new ArrayList<>();
    private final LocalDateTime createdDate = LocalDateTime.now();
    private QuestionId questionId;
    private String title;
    private String contents;
    private NsUser writer;
    private boolean deleted = false;
    private LocalDateTime updatedDate;

    public Question() {
    }

    public Question(QuestionId questionId, String title, String contents, NsUser writer, boolean deleted, LocalDateTime updatedDate) {
        this.questionId = questionId;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.deleted = deleted;
        this.updatedDate = updatedDate;
    }

    public static Question of(Long questionId, NsUser writer, String title, String contents) {
        return new Question(
                new QuestionId(questionId),
                title,
                contents,
                writer,
                false,
                LocalDateTime.now()
        );
    }

    public Long getId() {
        return this.questionId.getQuestionId();
    }

    public QuestionId getQuestionId() {
        return questionId;
    }

    public void addAnswer(Answer answer) {
        answer.relateToQuestion(this);
        answers.add(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }

    public List<DeleteHistory> delete(NsUser loginUser) {
        validateDelete(loginUser);
        deleteQuestionAndAnswers();
        return makeDeleteHistories();
    }

    private void deleteQuestionAndAnswers() {
        this.doDelete();
        answers.forEach(Answer::doDelete);
    }

    private void doDelete() {
        this.deleted = true;
    }

    private List<DeleteHistory> makeDeleteHistories() {
        List<DeleteHistory> deleteHistories = answers.stream()
                .map(Answer::toDeleteHistory)
                .collect(Collectors.toList());
        deleteHistories.add(this.toDeleteHistory());
        return deleteHistories;
    }

    private void validateDelete(NsUser loginUser) {
        validateQuestionOwner(loginUser);
        validateAnswers(loginUser);
    }

    private void validateAnswers(NsUser loginUser) {
        answers.stream()
                .filter(answer -> !answer.isOwner(loginUser))
                .findAny().ifPresent(answer -> {
                    throw new QuestionDeleteAnswerExistedException();
                });
    }

    private void validateQuestionOwner(NsUser loginUser) {
        if (!writer.matchUser(loginUser)) {
            throw new QuestionDeleteUnauthorizedException();
        }
    }

    public DeleteHistory toDeleteHistory() {
        return DeleteHistory.of(ContentType.QUESTION, this.getId(), this.writer, LocalDateTime.now());
    }
}
