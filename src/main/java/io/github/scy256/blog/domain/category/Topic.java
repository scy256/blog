package io.github.scy256.blog.domain.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topic {

    NONE("없음"),
    DAILY_LIFE("일상"),
    SPORTS("스포츠"),
    ART("미술"),
    TRAVEL("여행"),
    IT("IT");

    private final String title;

    public static Topic findByTitle(String title) {
        for(Topic topic : values()) {
            if(topic.getTitle().equals(title))
                return topic;
        }
        throw new IllegalArgumentException("존재하지 않는 관심사입니다");
    }

}
