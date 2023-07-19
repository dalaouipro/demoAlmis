package org.example.services;

import com.almis.awe.config.ServiceConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl extends ServiceConfig implements AuditorAware<String> {

    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        return Optional.of(getSession().getUser());
    }


}
