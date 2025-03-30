package start;

import jakarta.persistence.*; // JPA 어노테이션 패키지

@Entity // 이 클래스를 테이블과 매핑한다고 JPA에게 알려준다.
@Table(name="MEMBER") // 엔티티 클래스에 매핑할 테이블 정보를 알려준다. name 속성을 활용했다.
public class Member {

    @Id // 식별자 필드 표시
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME") // 필드를 컬럼에 매핑한다.
    private String username;

    private Integer age; // @Column을 생략하면 필드명(여기선 age)이 컬럼명이 된다.

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
