package org.example.kinoxpbackend.service;

import org.example.kinoxpbackend.dto.response.ShowingResponse;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.enums.ShowingStatus;
import org.example.kinoxpbackend.repository.ShowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShowingService {
    private final ShowingRepository showingRepository;

    public ShowingService(ShowingRepository showingRepository) {
        this.showingRepository = showingRepository;
    }
    @Transactional(readOnly = true)
    public List<ShowingResponse> getAllShowings(){
        return showingRepository.findAllByOrderByStartsAtAsc().stream().map(this::toReponse).toList();

    }

    private ShowingResponse toReponse(Showing showing) {
        return new ShowingResponse(showing.getShowingId(),
                showing.getStartsAt(), showing.getStatus(), showing.getMovie().getTitle(), showing.getTheater().getName()
        );
    }
    @Transactional(readOnly = true)
    public Showing getReservableShowing(Long showingId) {
        Showing showing = showingRepository.findById(showingId)
                .orElseThrow(() ->
                        new NoSuchElementException("Showing not found"));

        if (showing.getStatus() == ShowingStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cannot reserve a cancelled showing"
            );
        }

        return showing;
    }

}
