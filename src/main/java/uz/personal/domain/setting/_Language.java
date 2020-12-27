package uz.personal.domain.setting;

import lombok.*;
import uz.personal.domain.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "languages")
public class _Language extends Auditable {

    @Column(name = "name")
    protected String name;

    @Column(name = "code")
    protected String code;
}
