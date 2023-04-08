package ru.practicum.ewm.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDto {
        private Long id;
        private Long requester;
        private Long event;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime created;
        private RequestStatus status;
    }

    public static RequestDto toInnerRequestDto(Request request) {
        return new RequestDto(request.getId(),
                request.getRequester().getId(),
                request.getEvent().getId(),
                request.getCreated(),
                request.getStatus());
    }
}
