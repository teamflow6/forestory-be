package com.teamflow.forestory_be.support.common.domain;

/**
 * AggregateRoot 는 해당 애그리게이트의 유일한 진입점으로, 일관성과 트랜잭션 경계를 보장합니다.
 * <p>
 * 하나의 애그리게이트에는 반드시 하나의 루트만 존재해야 하며, 도메인 엔티티는 해당 클래스를 상속받아야 합니다.
 *
 * @author junseoparkk
 */
public abstract class AggregateRoot extends DomainEntity {
}
