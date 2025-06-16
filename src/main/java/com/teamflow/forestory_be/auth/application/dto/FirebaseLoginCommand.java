package com.teamflow.forestory_be.auth.application.dto;

public record FirebaseLoginCommand(
    String email,
    String firebaseUid
) {
}
