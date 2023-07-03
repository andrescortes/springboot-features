package com.debuggeando_ideas.best_travel.domain.entities.documents;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {

    @Field("granted_authorities")
    private Set<String> grantedAuthorities;
}
