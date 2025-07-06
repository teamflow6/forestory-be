package com.teamflow.forestory_be.auth.application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.teamflow.forestory_be.auth.application.dto.FirebaseLoginCommand;
import com.teamflow.forestory_be.auth.domain.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// TODO: 인프라 레이어로 분리, 테스트 후 이메일 검증 로직 추가
@Service
@RequiredArgsConstructor
public class FirebaseService {

    private final FirebaseApp firebaseApp;

    public FirebaseLoginCommand verify(String idToken) {
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance(firebaseApp).verifyIdToken(idToken);

//            if (!firebaseToken.isEmailVerified()) {
//                throw new EmailNotVerifiedException();
//            }

            return new FirebaseLoginCommand(
                firebaseToken.getEmail(),
                firebaseToken.getUid()
            );
        } catch (FirebaseAuthException ex) {
            throw new InvalidTokenException();
        }
    }
}
