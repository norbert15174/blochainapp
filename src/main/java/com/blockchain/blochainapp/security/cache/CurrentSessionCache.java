package com.blockchain.blochainapp.security.cache;

import com.blockchain.blochainapp.security.model.SessionModel;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Component
public class CurrentSessionCache {

    private Map<String, SessionModel> sessions = new HashMap<>();

    public void createOrReplaceSession(String accessToken, String refreshToken, String clientId, String subject, String username) {
        var session = SessionModel.builder()
                .shortAccessToken(accessToken.length() > 50 ? accessToken.substring(0, 50) : accessToken)
                .refreshToken(refreshToken)
                .clientId(clientId)
                .username(username)
                .build();
        //noinspection RedundantCollectionOperation
        if (sessions.containsKey(subject)) {
            sessions.remove(subject);
        }
        sessions.put(subject, session);
    }

    public SessionModel checkAndGetSession(String userId) {
        if (!sessions.containsKey(userId)) {
            throw new JwtException("Invalid tokenId");
        }

        var session = sessions.get(userId);
        return session;
    }

}
