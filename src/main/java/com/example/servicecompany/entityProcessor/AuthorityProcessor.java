package com.example.servicecompany.entityProcessor;

import com.example.servicecompany.domain.Authority;
import com.example.servicecompany.domain.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AuthorityProcessor implements RepresentationModelProcessor<EntityModel<Authority>> {


    @Override
    public EntityModel<Authority> process(EntityModel<Authority> model) {
        return null;
    }
}