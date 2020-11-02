package demo.spring.datasource.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "foo")
public class Foo extends SuperEntity {

    private String bar;

    @Column(name = "bar")
    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
