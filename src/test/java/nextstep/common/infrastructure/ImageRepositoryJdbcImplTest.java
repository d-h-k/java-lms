package nextstep.common.infrastructure;

import nextstep.common.domain.Image;
import nextstep.common.domain.ImageRepository;
import nextstep.fixture.TestFixture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ImageRepositoryJdbcImplTest {
    @Autowired ImageRepository imageRepository;

    @DisplayName("엔티티 저장한다")
    @Test
    public void 저장한다() {
        //given
        Image image = TestFixture.BLUE_IMAGE;
        //when
        Image save = imageRepository.save(image);
        Image find = imageRepository.findByImageId(save.getImageId()).orElseThrow(RuntimeException::new);
        //then
        assertThat(save.getImageId()).as("imageId 값이 할당되어야한다").isNotNull();
        assertThat(find.getImageUrl()).as("다른 필드들이 정상적으로 저장되었는지를 검증한다").isEqualTo(image.getImageUrl());
    }

    @DisplayName("id 를 조회조건으로 엔티티를 조회한다")
    @Test
    public void findByID() {
        //given
        //when
        //then
    }
}