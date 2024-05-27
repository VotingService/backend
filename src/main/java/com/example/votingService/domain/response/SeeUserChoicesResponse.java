package com.example.votingService.domain.response;

import com.example.votingService.dto.ElectionDto;
import com.example.votingService.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeeUserChoicesResponse {
    private ElectionDto election;
    private UserDto candidate;
    private UserDto voter;
    private Integer candidatePosition;
}
