package com.example.card_management.service.user.card.request;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.repository.user.card.request.UserCardRequestRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCardRequestServiceManager extends UserCardRequestService<UserCardRequest> {
    public UserCardRequestServiceManager(UserCardRequestRepository userCardRequestRepository, CardEnvironment cardEnvironment) {
        super(userCardRequestRepository, cardEnvironment);
    }

    @Override
    public void send(UserCardRequest userCardRequest) {
        userCardRequestRepository.save(userCardRequest);
    }

    @Override
    public UserCardRequest getBySenderEmail(String senderEmail) {
        return userCardRequestRepository.findUserCardRequestBySender_Email(senderEmail).orElseThrow(() -> new CardManagementException("user card request is not found"));
    }


    public List<UserCardRequest> getAll(int pageNumber) {
        return userCardRequestRepository.findAllUserCardRequest(PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()));
    }
}
