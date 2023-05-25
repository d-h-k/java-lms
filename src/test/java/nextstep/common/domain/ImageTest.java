package nextstep.common.domain;

import nextstep.fixture.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {
    @DisplayName("이미지는 하나의 이미지링크를 갖는다")
    @Test
    public void imageLink() {
        //given
        Image image = TestFixture.RED_IMAGE;
        //when
        //then
        assertThat(image.getImageLink())
                .as("이미지 링크가 존재해야 한다")
                .isNotEmpty();
    }
}