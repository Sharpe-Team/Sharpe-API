package com.esgi.message;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rfruitet on 08/03/2017.
 */

@Builder
@Data
public class MessageDto {

    private Long id;
    private Long topic;
    private String username;
    private String message;
}
