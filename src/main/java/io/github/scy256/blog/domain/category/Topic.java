package io.github.scy256.blog.domain.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topic {

    NONE("없음",1),
    DAILY_LIFE("일상",2),
    SPORTS("스포츠",3),
    ART("미술",4),
    TRAVEL("여행",5),
    IT("IT, 컴퓨터",6);

    private final String title;

    private final int id;

}
