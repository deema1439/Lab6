package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.message.Message;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
public class Employee {
@NotEmpty(message = "The Id Cant be Empty")
@Size(min=3,
        message = "the length of the Id should be more than 2 characters.")
 private String id;
@NotEmpty(message = "the name Can't be Empty")
@Size(min = 5,
        message = "Write the Full name Should be more than 4  ")
@Pattern(regexp = "^[a-zA-Z\\s]*$")
private String name;
@NotEmpty(message="The email should not be Empty. ")
@Email(message = "it's not a valid Email")
 private String email;

@NotEmpty(message = "the PhoneNumber should not be Empty")
@Pattern(regexp = "^05\\d{8}$",
        message = "PhoneNumber Should start with 05,and the length should be 10 numbers")
 private String phoneNumber;

@NotNull(message = "The Age Should not be Empty")
@Min(value = 26,
        message = "The Age Must be more than 25 ")
@Max(value = 70,
        message = "The Age Must be 70 or less")
 private Integer age;

@NotEmpty(message ="The position Can not be Empty")
@Pattern(regexp = "^(supervisor|coordinator)$",
        flags = Pattern.Flag.CASE_INSENSITIVE)
private String position;

@AssertFalse(message ="onleave must be false initially")
//في اجازه
 private boolean onLeave;
 @NotNull(message = "the hireDate should not be empty ")
 @JsonFormat(pattern = "yyyy-MM-dd")
 @PastOrPresent(message = "the date Should be Today or in The Past. ")
 private LocalDate hireDate;

 @NotNull(message = "The annualLeave Can not be Empty. ")
 @Positive(message = "the annualLeave Should be a Positive Number Only ")
 private Integer annualLeave ;
























}
