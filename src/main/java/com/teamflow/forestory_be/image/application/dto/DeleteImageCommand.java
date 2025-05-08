package com.teamflow.forestory_be.image.application.dto;

public record DeleteImageCommand(
        Long userId,
        String fileName) {

}
