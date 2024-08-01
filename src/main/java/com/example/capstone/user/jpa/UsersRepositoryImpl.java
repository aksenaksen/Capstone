package com.example.capstone.user.jpa;

import com.example.capstone.user.vo.RequestModify;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private QUserEntity userEntity = QUserEntity.userEntity;

    @Override
    public Optional<UserEntity> findByEmail(String email) {

        return Optional.ofNullable(jpaQueryFactory.selectFrom(userEntity)
                .where(userEntity.email.eq(email))
                .fetchOne());
    }

    @Override
    public boolean existsByEmail(String email) {

        Integer fetchOne=jpaQueryFactory.selectOne()
                .from(userEntity)
                .where(userEntity.email.eq(email))
                .fetchOne();

        return fetchOne!=null;
    }

    @Override
    public Optional<UserEntity> findByPhoneNum(String phoneNum) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(userEntity)
                    .where(userEntity.phoneNum.eq(phoneNum))
                    .fetchOne());
    }

    @Override
    public boolean existsByPhoneNum(String phoneNum) {

        Integer fetchOne=jpaQueryFactory.selectOne()
                .from(userEntity)
                .where(userEntity.phoneNum.eq(phoneNum))
                .fetchOne();

        return fetchOne!=null;
    }

    @Override
    public Optional<UserEntity> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.refreshToken.eq(refreshToken))
                .fetchOne());
    }

    @Override
    public Long updateUser(Long userId, RequestModify userModify){

        return jpaQueryFactory.update(userEntity)
                        .set(userEntity.phoneNum, ObjectUtils.defaultIfNull(userModify.getPhoneNum(),userEntity.phoneNum.toString()))
                        .set(userEntity.password, ObjectUtils.defaultIfNull(userModify.getPassword(),userEntity.password.toString()))
                        .set(userEntity.username,ObjectUtils.defaultIfNull(userModify.getUsername(),userEntity.phoneNum.toString()))
                        .where(userEntity.id.eq(userId))
                        .execute();
    }
}
