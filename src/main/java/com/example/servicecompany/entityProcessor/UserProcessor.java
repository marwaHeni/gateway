package com.example.servicecompany.entityProcessor;

import com.example.servicecompany.domain.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor implements RepresentationModelProcessor<EntityModel<User>> {


    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        return null;
    }
}