package start;

import jakarta.persistence.*; // JPA 어노테이션 패키지

@Entity // 이 클래스를 테이블과 매핑한다고 JPA에게 알려준다.
// 엔티티 클래스에 매핑할 테이블 정보를 알려준다.
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"} )})
public class Member {

    @Id // 식별자 필드 표시
    @Column(name = "ID")
    private String id;

    // 4. 회원 이름은 필수, 10자를 초과하면 안 된다.
    @Column(name = "NAME", nullable = false, length = 10) // 필드를 컬럼에 매핑한다.
    private String username;

    private Integer age; // @Column을 생략하면 필드명(여기선 age)이 컬럼명이 된다.

    // 1. 회원은 일반 회원과 관리자로 구분해야 한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 2. 회원 가입일과 수정일이 있어야 한다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 3. 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.
    @Lob
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
