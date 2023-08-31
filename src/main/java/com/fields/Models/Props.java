package com.fields.Models;


import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonBlobType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;

@Entity
@Data
@Table(name="ref_books", schema="public")
@TypeDefs(@TypeDef(name = "json", typeClass = JsonBinaryType.class))
@Getter
@Setter
@ToString
public class Props {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "ref_name")
    private String refName;

    @Type(type = "json")
    @Column(name = "fields", columnDefinition = "json")
    public JsonNode fields;

}



