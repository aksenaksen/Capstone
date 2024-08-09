package com.example.capstone.diary.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDiary is a Querydsl query type for Diary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiary extends EntityPathBase<Diary> {

    private static final long serialVersionUID = 337632206L;

    public static final QDiary diary = new QDiary("diary");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath food = createString("food");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> kcal = createNumber("kcal", Integer.class);

    public final EnumPath<MealType> mealType = createEnum("mealType", MealType.class);

    public QDiary(String variable) {
        super(Diary.class, forVariable(variable));
    }

    public QDiary(Path<? extends Diary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDiary(PathMetadata metadata) {
        super(Diary.class, metadata);
    }

}

