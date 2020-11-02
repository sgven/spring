package demo.spring.datasource.orm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SuperEntity extends IdEntity {

//    protected String createId;
//    protected Date createDate;
//    protected String modifyId;
//    protected Date modifyDate;
//    protected int isActive;
    protected int isDelete;

    @Column(name = "IS_DELETE")
    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
