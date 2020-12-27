package uz.personal.domain.setting;

import lombok.*;
import uz.personal.domain.Auditable;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "error_message_translations")
public class _ErrorMessageTranslation extends Auditable {

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @OneToOne
    @JoinColumn(name = "lang_id", referencedColumnName = "id")
    private _Language language;
}
