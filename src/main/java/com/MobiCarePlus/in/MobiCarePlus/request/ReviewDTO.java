package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private String user; // User's name
    private Long shopUserId;
    private Long userId; // ID of the user submitting the review
}
