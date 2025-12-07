package com.duke.user.hateoas;

import com.duke.user.controller.UserController;
import com.duke.user.dto.UserResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {

    @Override
    public EntityModel<UserResponseDto> toModel(UserResponseDto user) {
        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class).createUser(null)).withRel("create"),
                linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update"),
                linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete")
        );
    }
}