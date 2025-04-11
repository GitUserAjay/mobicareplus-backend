package com.MobiCarePlus.in.MobiCarePlus.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
private String message;
private String statusCode;
}
