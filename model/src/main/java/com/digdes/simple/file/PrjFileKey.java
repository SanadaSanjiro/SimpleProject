package com.digdes.simple.file;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PrjFileKey implements Serializable {
    @Column(name="prjcode")
    private String prjcode;
    @Column(name="name")
    private String name;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj==null || obj.getClass()!=this.getClass()) {return false;}
        PrjFileKey key = (PrjFileKey) obj;
        if (!(key.getName()).equals(name)) {return false;}
        return key.getPrjcode().equals(prjcode);
    }

    @Override
    public String toString() {
        return "FileKey{" +
                "prjcode='" + prjcode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
