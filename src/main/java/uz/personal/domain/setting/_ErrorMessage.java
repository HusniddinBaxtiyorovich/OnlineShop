package uz.personal.domain.setting;

import lombok.*;
import uz.personal.domain.Auditable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "error_messages")
public class _ErrorMessage extends Auditable {

    @Column(name = "error_code", unique = true)
    private String errorCode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_id")
    @Builder.Default
    private List<_ErrorMessageTranslation> translations = new ArrayList<>();
}
